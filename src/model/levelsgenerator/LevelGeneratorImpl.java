package model.levelsgenerator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import model.levelsgenerator.conditions.ConditionGiver;
import model.levelsgenerator.conditions.ConditionGiverImpl;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.levelsgenerator.geometry.GridImpl;
import model.math.BallsUrn;
import model.math.BallsUrnImpl;

/**
 * An implementation of LevelGenerator interface that uses advanced reflection functions (via ClassGraph library) and modular classes.
 */
public final class LevelGeneratorImpl implements LevelGenerator {

    private static final int BALLS_NUMBER = 10;
    private static final int GRID_RANK = 10;
    private static final Coordinate JUMP_RANGE = new Coordinate(3, 3);
    private static final int MAX_ATTEMPTS = 500;
    private static final int REPEAT_ENEMY_PERCENTAGE = 20;
    private static final int MAX_ENTITIES = 6;

    private EntityBlock player;

    private List<LevelGenerationEntity> entityList;
    private Map<EntityBlock, BallsUrn> blockMap;
    private ConditionGiver cg;
    private Grid levelGrid;
    private Integer iteration;
    private ArchitectureBuilder archBuilder;
    private final Random randomIterator;
    private final List<Coordinate> attemptedPoints;

    /**
     * Initialize the level generator, importing the modular component via reflection and preparing it for running.
     */
    public LevelGeneratorImpl() {
        this.importEntities();
        this.importConditionGiver();
        this.initializeBlocksMap();
        this.iteration = 0;
        this.randomIterator = new Random();
        this.attemptedPoints = new ArrayList<>();
    }

    @Override
    public Map<Point, String> getNewLevel() {
        Boolean entityLock = new Boolean(false);
        this.levelGrid.reset();
        this.levelGrid = this.archBuilder.getArchitecture(this.levelGrid);

        if (this.iteration.equals(0)) {
            this.placePlayer();
        }

        Integer entitiesPlaced = 0; 
        BallsUrn.Color ball = BallsUrn.Color.WHITE;
        EntityBlock selectedEntity = this.blockMap.keySet().iterator().next();

        while (entitiesPlaced < LevelGeneratorImpl.MAX_ENTITIES) {
            if (entityLock.equals(Boolean.FALSE)) {
                while (ball.equals(BallsUrn.Color.WHITE)) {
                    for (EntityBlock e : this.blockMap.keySet()) {
                        ball = this.blockMap.get(e).getBall();
                        if (ball.equals(BallsUrn.Color.BLACK)) {
                            selectedEntity = e;
                            break;
                        }
                    }
                }
            }

            Integer attempts = 0;
            List<Coordinate> failedPoints = new ArrayList<>();
            Coordinate point = this.getRandomPoint();

            while (attempts <= LevelGeneratorImpl.MAX_ATTEMPTS 
                   && selectedEntity.verifyPlacingConditions((GridImpl) this.levelGrid, point)) {
                failedPoints.add(point);
                attempts = attempts + 1;
                while (failedPoints.contains(point)) {
                    point = this.getRandomPoint();
                }
            }

            if (attempts.equals(LevelGeneratorImpl.MAX_ATTEMPTS)) {
                entityLock = Boolean.FALSE;
            } else {
                this.levelGrid.place(point, selectedEntity);
                entitiesPlaced = entitiesPlaced + 1;
                entityLock = (this.randomIterator.nextInt(100) < LevelGeneratorImpl.REPEAT_ENEMY_PERCENTAGE) ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        final Map<Point, String> results = new HashMap<>();
        this.levelGrid.getSnapshot().entrySet().stream()
                                               .filter(e -> !e.getValue().equals(this.levelGrid.getVoid()))
                                               .forEach(e -> results.put(e.getKey().getPoint(), e.getValue().getCanonicalName()));
        this.iteration = this.iteration + 1;
        return results;
    }

    private Coordinate getRandomPoint() {
        return new Coordinate(this.randomIterator.nextInt(this.levelGrid.getSize().getPoint().x),
                              this.randomIterator.nextInt(this.levelGrid.getSize().getPoint().y));
    }

    private void placePlayer() {
        Coordinate point = new Coordinate(0, 0);
        this.attemptedPoints.clear();

        while (this.player.verifyPlacingConditions((GridImpl) this.levelGrid, point).equals(Boolean.TRUE)) {
            while (this.attemptedPoints.contains(point)) {
                point = this.getRandomPoint();
            }
        }
        this.attemptedPoints.clear();
        this.levelGrid.place(point, this.player);
    }

    /**
     * Create the entity set scanning the whole build path, searching for classes that extends AbstractEntity and doesn't possess derived classes.
     * The non-definitive class exclusion is essential for granting that only the final version of an entity will be added to the active rotation spawn list.
     * If in future someone want to edit an existing enemy expanding the enemy class but keep the current version of the same enemy too, he has to extend the enemy class in two way:
     * the edited one and a "fake" one that changes nothing for preserving the current state of the entity.
     */
    private void importEntities() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().scan()) {
            final ClassInfoList filtered = scanResult.getAllClasses().filter(
                    classInfo -> (classInfo.extendsSuperclass("model.entities.AbstractEntity") 
                               && classInfo.getSubclasses().isEmpty()));
            this.entityList = new ArrayList<>();
            filtered.stream().forEach(c -> {
                try {
                    this.entityList.add(new LevelGenerationEntity(c));
                } catch (IllegalArgumentException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            });
        }

        //entityList.stream().peek(e -> System.out.println(e.getCanonicalName() + " " + e.getEntityName() + " " + e.getComponentsSet().toString() + "\n"));
        /*if there is no player or at least one architecture entities throw exception*/
        if (this.entityList.stream()
                           .map(e -> e.getEntityName())
                           .noneMatch(en -> en.equals("Player")) 
                           || 
            this.entityList.stream()
                           .map(e -> e.getComponentsSet())
                           .filter(cs -> cs.contains("Architecture"))
                           .count() == 0) {
            throw new IncompatibleEntitiesWorkingSet();
        }
    }

    /**
     * Import the entity that associate the components with the conditions: before using the default class, it search in the whole build path another compatible class.
     * If more than one classes are found, it will be used the first in alphabetical order.
     */
    private void importConditionGiver() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().scan()) {

            final ClassInfoList filtered = scanResult.getAllClasses().filter(
                    classInfo -> (classInfo.implementsInterface("ConditionGiver") 
                               && !classInfo.hasAnnotation("DefaultConditionGiver")));
            if (filtered.isEmpty()) {
                this.cg = new ConditionGiverImpl();
            } else {
                filtered.stream()
                        .sorted((c1, c2) -> c1.getSimpleName().compareTo(c2.getSimpleName()))
                        .findFirst().get().loadClass().cast(this.cg);
            }
        }
    }

    private void initializeBlocksMap() {
        this.blockMap = new HashMap<>();
        final Map<EntityBlock, BallsUrn> architecture = new HashMap<>();

        for (LevelGenerationEntity e : this.entityList) {
            if (e.getComponentsSet().contains("Architecture")) {
                architecture.put(new EntityBlock(e, this.cg), new BallsUrnImpl(LevelGeneratorImpl.BALLS_NUMBER));
            } else {
                this.blockMap.put(new EntityBlock(e, this.cg), new BallsUrnImpl(LevelGeneratorImpl.BALLS_NUMBER));
            }
        }
        this.archBuilder = new ArchitectureBuilderImpl(architecture, 
                                                       new Coordinate(LevelGeneratorImpl.GRID_RANK, LevelGeneratorImpl.GRID_RANK), 
                                                       LevelGeneratorImpl.JUMP_RANGE);
    }
}

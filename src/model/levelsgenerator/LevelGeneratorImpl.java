package model.levelsgenerator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import model.levelsgenerator.conditions.ConditionGiver;
import model.levelsgenerator.conditions.ConditionGiverImpl;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.levelsgenerator.geometry.GridImpl;

import model.math.BallsUrn;
import model.math.BallsUrn.Color;
import model.math.BallsUrnImpl;

/**
 * An implementation of LevelGenerator interface that uses advanced reflection functions (via ClassGraph library) and modular classes.
 */
public final class LevelGeneratorImpl implements LevelGenerator {

    private static final int BALLS_NUMBER = 10;
    private static final Coordinate GRID_DIM = new Coordinate(10, 20);
    private static final Coordinate JUMP_RANGE = new Coordinate(3, 3);
    private static final int MAX_ATTEMPTS = 100;
    private static final int REPEAT_ENEMY_PERCENTAGE = 20;
    private static final int MAX_ENTITIES = 3;

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
        this.levelGrid = new GridImpl(LevelGeneratorImpl.GRID_DIM.getPoint().x, LevelGeneratorImpl.GRID_DIM.getPoint().y);
    }

    @Override
    public Map<Point, String> getNewLevel() {
        //reset the local variables and get architecture.
        Boolean entityLock = Boolean.FALSE;
        this.levelGrid.reset();
        this.levelGrid = this.archBuilder.getArchitecture();
        Integer entitiesPlaced = 0; 
        BallsUrn.Color ball = BallsUrn.Color.WHITE;
        EntityBlock selectedEntity = this.blockMap.keySet().iterator().next();

        //if is the the first iteration, place the player.
        if (this.iteration.equals(0)) {
            this.placePlayer();
        }

        //start the main placing cycle.
        while (entitiesPlaced < LevelGeneratorImpl.MAX_ENTITIES) {
            if (entityLock.equals(Boolean.FALSE)) {                             //if the entity lock is false, choose a new entity via balls sort.
                while (ball.equals(BallsUrn.Color.WHITE)) {
                    for (final EntityBlock e : this.blockMap.keySet()) {
                        ball = this.blockMap.get(e).getBall();
                        if (ball.equals(BallsUrn.Color.BLACK)) {                //continue until a black ball is extracted.
                            selectedEntity = e;
                            break;
                        }
                    }
                }
            }

            //reset iteration variable and try to place the selected entity.
            Integer attempts = 0;
            final List<Coordinate> failedPoints = new ArrayList<>();
            Coordinate point = this.getRandomPoint();

            //while the selected point does not respect the placing condition, choose a new point; 
            //the iteration of this cycle are limited by max attempts.
            while (attempts <= LevelGeneratorImpl.MAX_ATTEMPTS 
                   && selectedEntity.verifyPlacingConditions(this.levelGrid, point).equals(Boolean.FALSE)) {
                failedPoints.add(point);
                attempts = attempts + 1;
                while (failedPoints.contains(point)) {
                    point = this.getRandomPoint();
                }
            }

            //if the program reached this point exiting the cycle at the max-th attempt, the entity cannot be placed, so restore the black ball and deactivate the entity lock.
            if (attempts.equals(LevelGeneratorImpl.MAX_ATTEMPTS)) {
                entityLock = Boolean.FALSE;
                this.blockMap.get(selectedEntity).insertSingleBall(Color.BLACK);
            } else {
                if (selectedEntity.verifyPlacingConditions(this.levelGrid, point).equals(Boolean.TRUE)) {   //else, place it only if surpass another check of the placing conditions.
                    this.levelGrid.place(point, selectedEntity);

                    //roll a dice if the next entity to place will be the same or will be sorted again via balls extraction.
                    entityLock = (this.randomIterator.nextInt(100) < LevelGeneratorImpl.REPEAT_ENEMY_PERCENTAGE) ? Boolean.TRUE : Boolean.FALSE;
                }
            }
            entitiesPlaced = entitiesPlaced + 1;        //increment the entitiesPLaced whenever the entity is placed or not: prevents infinite cycle.
        }

        //print the result in output without the empty grid slots.
        final Map<Point, String> results = new HashMap<>();
        this.levelGrid.getSnapshot().entrySet().stream()
                                               .filter(e -> !e.getValue().equals(this.levelGrid.getVoid()))
                                               .forEach(e -> results.put(e.getKey().getPoint(), e.getValue().getEntityName()));

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

        while (this.player.verifyPlacingConditions(this.levelGrid, point).equals(Boolean.FALSE)) {
            this.attemptedPoints.add(point);
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
                               && classInfo.getSubclasses().isEmpty())
                               && !classInfo.hasAnnotation("model.entities.LevelGenerationIgnore"));
            this.entityList = new ArrayList<>();
            filtered.stream().forEach(c -> {
                try {
                    this.entityList.add(new LevelGenerationEntity(c));
                } catch (IllegalArgumentException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            });
        }

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
                    classInfo -> (classInfo.implementsInterface("model.levelsgenerator.conditions.ConditionGiver") 
                               && !classInfo.hasAnnotation("model.levelsgenerator.conditions.DefaultConditionGiver")));
            if (filtered.isEmpty()) {
                this.cg = new ConditionGiverImpl();
            } else {
                try {
                    this.cg = (ConditionGiver) filtered.stream()
                                                       .sorted((c1, c2) -> c1.getSimpleName().compareTo(c2.getSimpleName()))
                                                       .findFirst().get()
                                                       .loadClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Given a initialized this.entityList, create and catalog the EntityBlocks for grid placing. 
     */
    private void initializeBlocksMap() {
        this.blockMap = new HashMap<>();
        final Map<EntityBlock, BallsUrn> architecture = new HashMap<>();

        /*create blocks for all the entities, filtering the Architecture ones*/
        this.entityList.stream().forEach(e -> {
            final EntityBlock b = new EntityBlock(e, this.cg);
            if (e.getComponentsSet().contains("Architecture")) {
                architecture.put(b, new BallsUrnImpl(LevelGeneratorImpl.BALLS_NUMBER));
            } else {
                this.blockMap.put(b, new BallsUrnImpl(LevelGeneratorImpl.BALLS_NUMBER));
            }
        });

        /*pass all the architecture blocks to the Architecture Builder*/
        this.archBuilder = new ArchitectureBuilderImpl(architecture,
                                                       LevelGeneratorImpl.GRID_DIM.getSafeCopy(), 
                                                       LevelGeneratorImpl.JUMP_RANGE);

        /*remove all the possible player blocks from the spawn table, keeping one for the player placing*/
        final List<EntityBlock> players = this.blockMap.keySet().stream()
                              .filter(b -> b.getEntity().getEntityName().equals("Player"))
                              .collect(Collectors.toList());
        players.stream().peek(p -> this.player = p).forEach(p -> this.blockMap.remove(p));

    }

    @Override
    public int getIteration() {
        return this.iteration;
    }
}

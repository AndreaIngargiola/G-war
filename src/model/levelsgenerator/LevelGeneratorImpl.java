package model.levelsgenerator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import model.entities.AbstractEntity;
import model.entities.Player;
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
public class LevelGeneratorImpl implements LevelGenerator {

    private static final int BALLS_NUMBER = 10;
    private static final int GRID_RANK = 10;
    private static final Coordinate JUMP_RANGE = new Coordinate(3, 3);
    
    private LevelGenerationEntity<Player> player;
    private LevelGenerationEntity<Floor> floor;

    private List<LevelGenerationEntity<? extends AbstractEntity>> entityList;
    private Map<EntityBlock, BallsUrn> blockMap;
    private Map<EntityBlock, BallsUrn> architecture;
    private ConditionGiver cg;
    private Grid levelGrid;
    private Integer iteration;

    /**
     * Initialize the level generator, importing the modular component via reflection and preparing it for running.
     */
    public LevelGeneratorImpl() {
        this.importEntities();
        this.importConditionGiver();
        this.initializeBlocksMap();
        this.iteration = 0;
    }

    @Override
    public Map<Point, ? extends AbstractEntity> getNewLevel() {
        // TODO Auto-generated method stub
        return null;
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
                    classInfo -> (classInfo.extendsSuperclass("AbstractEntity") 
                               && classInfo.getSubclasses().isEmpty()));

            final Class<? extends AbstractEntity> type = AbstractEntity.class;
            this.entityList = new ArrayList<>();
            filtered.stream()
                    .map(c -> c.loadClass(type))
                    .forEach(e -> entityList.add(new LevelGenerationEntity<>(type.cast(e))));
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
        this.architecture = new HashMap<>();

        for (LevelGenerationEntity<?> e : this.entityList) {
            if (e.getComponentsSet().contains("Architecture")) {
                this.architecture.put(new EntityBlock(e, this.cg), new BallsUrnImpl(LevelGeneratorImpl.BALLS_NUMBER));
            } else {
                this.blockMap.put(new EntityBlock(e, this.cg), new BallsUrnImpl(LevelGeneratorImpl.BALLS_NUMBER));
            }
        }
    }

    private void architectureBuilder() {
        for (int i = 0; i < LevelGeneratorImpl.GRID_RANK; i++) {
            this.levelGrid.place(new Coordinate(i, 0), new EntityBlock(this.floor));
        }
        
    }

    private void firstIteration() {
        this.levelGrid = new GridImpl(LevelGeneratorImpl.GRID_RANK);

    }
}

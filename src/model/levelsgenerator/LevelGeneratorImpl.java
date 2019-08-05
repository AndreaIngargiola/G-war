package model.levelsgenerator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import model.entities.AbstractEntity;
import model.levelsgenerator.conditions.ConditionGiver;
import model.levelsgenerator.conditions.ConditionGiverImpl;

/**
 * An implementation of LevelGenerator interface that uses advanced reflection functions (via ClassGraph library) and modular classes.
 */
public class LevelGeneratorImpl implements LevelGenerator {

    private List<LevelGenerationEntity<? extends AbstractEntity>> entityList;
    private List<EntityBlock> blockList;
    private ConditionGiver cg;
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
                new ConditionGiverImpl();
            } else {
                filtered.stream()
                        .sorted((c1, c2) -> c1.getSimpleName().compareTo(c2.getSimpleName()))
                        .findFirst().get().loadClass().cast(this.cg);
            }
        }
    }

    private void initializeBlocksMap() {
        final List<EntityBlock> blockList = new ArrayList<>();
        for (LevelGenerationEntity<?> entity : this.entityList) {
            blockList.add(new EntityBlock(entity, this.cg));
        }

    }
}

package model.levelsgenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * represents an abstraction of an Entity in the level generation.
 */
public class LevelGenerationEntity {
    private final String entityName;
    private final Set<String> componentsSet;

    /**
     * @param entityName is the name of the entity.
     * @param componentsSet is the set of names of the components possessed by the entity.
     */
    public LevelGenerationEntity() {
        this.entityName = "VOID";
        this.componentsSet = new HashSet<>();
    }
    
    /**
     * @param entityName is the name of the entity.
     * @param componentsSet is the set of names of the components possessed by the entity.
     */
    public LevelGenerationEntity(final String entityName, final Set<String> componentsSet) {
        this.entityName = entityName;
        this.componentsSet = componentsSet;
    }

    /**
     * a getter for the entity name.
     * @return the entity name.
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * a getter for the set of components name of this entity.
     * @return the set of components names.
     */
    public Set<String> getComponentsSet() {
        return componentsSet;
    }
}

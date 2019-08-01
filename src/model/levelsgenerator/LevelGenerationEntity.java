package model.levelsgenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import model.entities.AbstractEntity;
import enumerators.Faction;

/**
 * 
 * represents an abstraction of an Entity in the level generation. 
 * This is needed because different entities are casted as different classes that extends AbstractEntity.
 * @param <X> is a class that extends an AbstractEntity.
 */
public class LevelGenerationEntity<X extends AbstractEntity> {
    private final String entityName;
    private final Set<String> componentsSet;
    private final Faction type;

    /**
     * a void constructor.
     */
    public LevelGenerationEntity() {
        this.entityName = "VOID";
        this.componentsSet = new HashSet<>();
        this.type = Faction.NEUTRAL_IMMORTAL;
    }

    /**
     * A constructor that import an entity class and convert it in a Level Generation Entity.
     * @param e is the entity class to convert.
     */
    public LevelGenerationEntity(final X e) {
        this.entityName = e.getClass().getSimpleName();
        this.type = e.getType();
        this.componentsSet = e.getComponents().getInterfaces().stream()
                                                              .map(i -> i.getSimpleName())
                                                              .collect(Collectors.toSet());
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

    /**
     * a getter for the entity type.
     * @return the Enum Type field.
     */
    public Faction getType() {
        return type;
    }
}

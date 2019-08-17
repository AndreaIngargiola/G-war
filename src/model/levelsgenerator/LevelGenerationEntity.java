package model.levelsgenerator;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import enumerators.Faction;
import io.github.classgraph.ClassInfo;

/**
 * Represents an abstraction of an Entity in the level generation. 
 * This is needed because different entities are casted as different classes that extends AbstractEntity.
 */
public class LevelGenerationEntity {
    private final String entityName;
    private final String fullName;
    private final Set<String> componentsSet;
    private final Faction type;
    private static final String COMPONENTS_SEPARATOR = "-"; 

    /**
     * A void constructor.
     */
    public LevelGenerationEntity() {
        this.entityName = "VOID";
        this.componentsSet = new HashSet<>();
        this.type = Faction.NEUTRAL_IMMORTAL;
        this.fullName = "null";
    }

    /**
     * A constructor that import an entity class and converts it in a Level Generation Entity.
     * @param e is the entity class to convert.
     * @throws IllegalAccessException if the fields are not accessible.
     * @throws IllegalArgumentException if the ClassInfo "e" doesn't extends abstract entity or doesn't possess the required fields for the creation of the LevelGenerationEntity.
     */
    public LevelGenerationEntity(final ClassInfo e) throws IllegalArgumentException, IllegalAccessException {
        if (!e.extendsSuperclass("model.entities.AbstractEntity")) {
            throw new IllegalArgumentException();
        } else {
            this.entityName = e.getClass().getSimpleName();
            this.fullName = e.getClass().getCanonicalName();

            final Field type = e.getFieldInfo("TYPE").loadClassAndGetField();
            type.setAccessible(true);
            this.type = (Faction) type.get(null);

            this.componentsSet = new HashSet<>();
            final Field components = e.getFieldInfo("COMPONENTS_LEGACY").loadClassAndGetField();
            components.setAccessible(true);

            final String allComponentsInterfaces = (String) components.get(null);
            String[] componentsArray = allComponentsInterfaces.split(LevelGenerationEntity.COMPONENTS_SEPARATOR);
            for (int i = 0; i < componentsArray.length; i++) {
                this.componentsSet.add(componentsArray[i]);
            }
        }
    }

    /**
     * A getter for the entity name.
     * @return the entity name.
     */
    public String getEntityName() {
        return this.entityName;
    }

    /**
     * A getter for the set of components name of this entity.
     * @return the set of components names.
     */
    public Set<String> getComponentsSet() {
        return this.componentsSet;
    }

    /**
     * A getter for the entity type.
     * @return the Enum Type field.
     */
    public Faction getType() {
        return this.type;
    }

    /**
     * A getter for the entity full name.
     * @return the entity canonical name.
     */
    public String getCanonicalName() {
        return this.fullName;
    }
}

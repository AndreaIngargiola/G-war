package model.levelsgenerator;
/**
 * Is a factory for complex block placing conditions that uses the logic of the entities and the abstraction granted from the gird/block system to 
 * check if the block can be placed in that point respecting some basic rules, granting the fairness of the level in the game play,
 * (avoiding impossible levels or entities that spawns just to die immediately).
 */
public interface ConditionFactory {

    /**
     * Check if all the coordinates with the lowest y of the block are supported by some architectural entities.
     * @return true if all the lowest points of the block are on an entity that has "Architecture" as a component, false otherwise.
     */
    Condition mustBeOnGround();

    /**
     * Check if in a given radius there are rivals to that entity. The radius is a fixed number of blocks calculated all around the block.
     * @return true if there aren't rivals of the entity in the vital space radius, false otherwise.
     */
    Condition notTooNearRival();
}

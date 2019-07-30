package enumerators;

/**
 * An enumerator with the possible factions of an entity.
 *
 */
public enum Faction {

    /**
     * A mortal entity that cannot hurt or be hurt by the player.
     */
    NEUTRAL_MORTAL,

    /**
     * A mortal entity that can hurt or be hurt by the player.
     */
    PSYCO_MORTAL,

    /**
     * An immortal entity (environmental entity) that cannot hurt or be hurt by the player.
     */
    NEUTRAL_IMMORTAL,

    /**
     * An immortal entity (environmental entity) that can hurt the player.
     */
    PSYCO_IMMORTAL;
}

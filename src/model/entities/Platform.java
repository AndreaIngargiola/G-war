package model.entities;

import enumerators.Faction;
import model.components.ArchitectureImpl;

/**
 * Models a Platform.
 */
public final class Platform extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_IMMORTAL;

    /**
     * 
     */
    public Platform() {
        super(TYPE);
        add(new ArchitectureImpl());
    }

    @Override
    public String toString() {
        return "Platform";
    }
}

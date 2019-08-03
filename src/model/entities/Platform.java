package model.entities;

import enumerators.Faction;
import model.components.ArchitectureImpl;
import model.components.EntityBody;

/**
 * Models a Platform.
 */
public final class Platform extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_IMMORTAL;

    /**
     * 
     */
    public Platform(final EntityBody body) {
        super(TYPE, body);
        add(new ArchitectureImpl());
    }

    @Override
    public String toString() {
        return "Platform";
    }
}

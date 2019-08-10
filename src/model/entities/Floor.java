package model.entities;

import enumerators.Faction;
import model.components.ArchitectureImpl;
import model.components.EntityBody;

/**
 * Models a Lake.
 * If the player fall into a lake he dies.
 */
public final class Lake extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_IMMORTAL;

    /**
     * 
     */
    public Lake(final EntityBody body) {
        super(TYPE, body);
        add(new ArchitectureImpl());
    }

    @Override
    public String toString() {
        return "Lake";
    }
}

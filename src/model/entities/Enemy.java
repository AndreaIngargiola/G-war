package model.entities;

import enumerators.Faction;
import model.components.EntityBody;

/**
 * Models a generic enemy in the game.
 */
public class Enemy extends AbstractEntity {

    /**
     * 
     * @param type
     *         the faction the enemy belongs to
     * @param body
     *         an {@link EntityBody} object
     */
    public Enemy(final Faction type, final EntityBody body) {
        super(type, body);
    }
}

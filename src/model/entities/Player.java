package model.entities;

import model.components.LifeImpl;

/**
 * Models the player.
 */

public final class Player extends AbstractEntity {

    /**
     * @param health
     *            Health level to begin with.
     */
    public Player(final int health) {
        super();
        add(new LifeImpl(health));
    }

    @Override
    public String toString() {
        return "Player";
    }
}

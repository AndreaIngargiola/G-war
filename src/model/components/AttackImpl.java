package model.components;

/**
 * Implementation class for the interface {@link Attack} .
 */

public class AttackImpl extends AbstractEntityComponent implements Attack {

    private final int damage;

    /**
     * 
     * @param damage
     *             the number of health points he takes away from the target
     */
    public AttackImpl(final  int damage) {
        super();
        this.damage = damage;
    }

    @Override
    public final int getDamage() {
        return this.damage;
    }

    @Override
    public final String toString() {
        return "Attack";
    }
}

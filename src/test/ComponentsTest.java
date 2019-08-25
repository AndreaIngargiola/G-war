package test;


import static org.junit.Assert.assertFalse;

import org.jbox2d.common.Vec2;
import org.junit.Before;
import org.junit.Test;

import model.components.Life;
import model.components.TimerGrill;
import model.entities.Player;
import model.physics.BodyBuilderImpl;

/**
 * Test if the components of an entities works fine.
 *
 */
public class ComponentsTest {

    private Player player;

    /**
     * Creates a new player model.
     */
    @Before
    public void setUp() {
        player = new Player(new BodyBuilderImpl(), new Vec2(0, 0));
    }

    /**
     * Test that throw an exception because TimerGrill is not a component of the player entity. 
     */
    @Test(expected = IllegalArgumentException.class)
    public void test1() {
        player.get(TimerGrill.class);
    }

    /**
     * Test that finds the Life components of the player, remove 10 health points from it and assert that 
     * the player is dead, since the player full life is 10 points.
     */
    @Test
    public void test2() {
        player.get(Life.class).damage(10);

        assertFalse("Dead", player.isAlive());
    }
}

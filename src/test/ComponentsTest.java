package test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jbox2d.common.Vec2;
import org.junit.Before;
import org.junit.Test;

import model.components.Life;
import model.components.Points;
import model.components.TimerGrill;
import model.entities.Grill;
import model.entities.Player;
import model.physics.BodyBuilderImpl;

/**
 * Test if the components of an entities works fine.
 *
 */
public class ComponentsTest {

    private static final int HEALTH_POINTS = 10;
    private static final int POINTS = 5;
    private Player player;
    private Grill grill;

    /**
     * Creates a new player model.
     */
    @Before
    public void setUp() {
        player = new Player(new BodyBuilderImpl(), new Vec2(0, 0));
        grill = new Grill(new BodyBuilderImpl(), new Vec2(0, 0));
    }


    /**
     * Test that finds the Life components of the player, remove 10 health points from it and assert that 
     * the player is dead, since the player full life is 10 points.
     * It also add 5 points to the player and assert that the Points Component works right.
     */
    @Test
    public void test2() {
        player.get(Life.class).damage(HEALTH_POINTS);
        player.get(Points.class).addPoints(POINTS);

        assertFalse("error if the player is alive", player.isAlive());
        assertEquals("error if the points Component doesn't work right", POINTS, player.get(Points.class).getCurrent());
    }

    /**
     * Test that finds the TimerGrill components of the grill and assert that 
     * the grill is not active. It then change its state and assert that it became active.
     */
    @Test
    public void test3() {
        assertFalse("OFF", grill.get(TimerGrill.class).isDangerous());
        grill.get(TimerGrill.class).changeState();

        assertTrue("ON", grill.get(TimerGrill.class).isDangerous());

    }
}

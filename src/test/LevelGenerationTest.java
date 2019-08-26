package test;


import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.junit.Before;
import org.junit.Test;

import enumerators.Faction;
import model.levelsgenerator.LevelGenerationEntity;
import model.levelsgenerator.geometry.*;



/**
 * Test if the components of an entities works fine.
 *
 */
public class LevelGenerationTest {

    /**
     * Creates a new player model.
     */
    @Before
    public void setUp() {
        final Grid level = new GridImpl(3, 3);
        final Set<String> components = new HashSet<>();
        components.add("Architecture");
        final LevelGenerationEntity platform = new LevelGenerationEntity("Platform", "Platform", components, Faction.NEUTRAL_IMMORTAL);
        components.clear();
        components.add("Movement");
    }

    /**
     * Test that throw an exception because TimerGrill is not a component of the player entity. 
     */
    @Test(expected = IllegalArgumentException.class)
    public void test1() {
        
    }

    /**
     * Test that finds the Life components of the player, remove 10 health points from it and assert that 
     * the player is dead, since the player full life is 10 points.
     */
    @Test
    public void test2() {
        

        assertFalse("Dead", player.isAlive());
    }
}

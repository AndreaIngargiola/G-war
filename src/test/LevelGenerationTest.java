package test;


import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import enumerators.Faction;
import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.LevelGenerationEntity;
import model.levelsgenerator.conditions.ConditionGiver;
import model.levelsgenerator.conditions.ConditionGiverImpl;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.levelsgenerator.geometry.GridImpl;



/**
 * Test if the components of an entities works fine.
 *
 */
public class LevelGenerationTest {
    private Grid level;
    private final Set<String> components = new HashSet<>();
    private LevelGenerationEntity platform;
    private LevelGenerationEntity pawn;
    private EntityBlock platformBlock;
    private EntityBlock pawnBlock;

    /**
     * Creates a new player model.
     */
    @Before
    public void setUp() {
        ConditionGiver cg = new ConditionGiverImpl();
        this.level = new GridImpl(3, 3);
        components.add("Architecture");
        this.platform = new LevelGenerationEntity("Platform", "Platform", components, Faction.NEUTRAL_IMMORTAL);
        components.clear();
        components.add("Movement");
        this.pawn = new LevelGenerationEntity("Pawn", "Pawn", components, Faction.NEUTRAL_MORTAL);

        this.platformBlock = new EntityBlock(this.platform, cg);
        this.pawnBlock = new EntityBlock(this.pawn, cg);
    }

    /**
     * Test that throw an exception because TimerGrill is not a component of the player entity. 
     */
    @Test
    public void test1() {
        this.level.reset();
        this.level.place(new Coordinate(0, 0), this.platformBlock);
        this.level.place(new Coordinate(1, 0), this.platformBlock);

        assertFalse("bla", this.pawnBlock.verifyPlacingConditions(this.level.getCopy(), new Coordinate(2, 1)));
    } 
}

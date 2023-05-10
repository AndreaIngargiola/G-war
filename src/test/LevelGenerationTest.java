package test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import enumerators.Faction;
import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.LevelGenerationEntity;
import model.levelsgenerator.conditions.ConditionGiver;
import model.levelsgenerator.conditions.ConditionGiverImpl;
import model.levelsgenerator.geometry.Block;
import model.levelsgenerator.geometry.BlockImpl;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.levelsgenerator.geometry.GridImpl;



/**
 * Test if, given some level generation entities, the blocks are created properly and the placing conditions are respected in a 3x3 level.
 */
public class LevelGenerationTest {
    private Grid level;
    private EntityBlock platformBlock;
    private EntityBlock pawnBlock;
    private EntityBlock hostileBlock;
    private final ConditionGiver cg = new ConditionGiverImpl();

    /**
     * Initialize the blocks and the test level.
     */
    @Before
    public void setUp() {
        this.level = new GridImpl(3, 3);

        final Set<String> components1 = new HashSet<>(); 
        components1.add("Architecture");
        final LevelGenerationEntity platform = new LevelGenerationEntity("Platform", "Platform", components1, Faction.NEUTRAL_IMMORTAL);
        this.platformBlock = new EntityBlock(platform, this.cg);

        final Set<String> components2 = new HashSet<>(); 
        components2.add("Movement");
        components2.add("Life");
        final LevelGenerationEntity pawn = new LevelGenerationEntity("Pawn", "Pawn", components2, Faction.NEUTRAL_MORTAL);
        this.pawnBlock = new EntityBlock(pawn, this.cg);

        final Set<String> components3 = new HashSet<>();
        components3.add("Life");
        final LevelGenerationEntity hostile = new LevelGenerationEntity("Hostile", "Hostile", components3, Faction.PSYCO_MORTAL);
        this.hostileBlock = new EntityBlock(hostile, this.cg);
    }

    /**
     * Test assert false because i'm trying to place a character mid-air. 
     */
    @Test
    public void testMidAirCondition() {
        this.level.reset();
        this.level.place(new Coordinate(0, 0), this.platformBlock);
        this.level.place(new Coordinate(1, 0), this.platformBlock);

        assertFalse("An error evaluating 'mustBeOnGround' condition has occuurred", 
                    this.pawnBlock.verifyPlacingConditions(this.level.getCopy(), new Coordinate(2, 1)));
    }

    /**
     * Test assert false because i'm trying to place a character too near to a rival. 
     */
    @Test
    public void testNotRivalsNearbyCondition() {
        this.level.reset();
        this.level.place(new Coordinate(0, 0), this.platformBlock);
        this.level.place(new Coordinate(1, 0), this.platformBlock);
        this.level.place(new Coordinate(1, 1), this.pawnBlock);

        assertFalse("An error evaluating 'notTooNearRival' condition has occurred",
                    this.hostileBlock.verifyPlacingConditions(this.level.getCopy(), new Coordinate(0, 1)));
    }

    /**
     * Test assert false because i'm trying to place a character on a platform that has already an entity (of the same faction) on it.
     */
    @Test
    public void testOneForPlatformCondition() {
        this.level.reset();
        this.level.place(new Coordinate(0, 0), this.platformBlock);
        this.level.place(new Coordinate(1, 0), this.platformBlock);
        this.level.place(new Coordinate(2, 0), this.platformBlock);
        this.level.place(new Coordinate(0, 1), this.pawnBlock);

        assertFalse("An error evaluating 'leaveMeAlone' condition has occurred",
                    this.pawnBlock.verifyPlacingConditions(this.level.getCopy(), new Coordinate(2, 1)));
    }

    /**
     * Test assert true because i'm respecting all the conditions. 
     */
    @Test
    public void testCorrectMiniLevel() {
        this.level.reset();
        this.level.place(new Coordinate(0, 0), this.platformBlock);
        this.level.place(new Coordinate(2, 0), this.platformBlock);
        assertTrue("An error evaluating all the conditions has occurred",
                   this.pawnBlock.verifyPlacingConditions(this.level.getCopy(), new Coordinate(0, 1))
                && this.hostileBlock.verifyPlacingConditions(this.level.getCopy(), new Coordinate(2, 1)));
    }

    /**
     * Demonstrate that the overlap of a 2x1 block placed in the far right corner of the grid 
     * is a 1x1 block because the out of bounds tiles are managed. 
     */
    @Test
    public void testOutOfBoundOverlap() {
        this.level.reset();
        final Block b = new BlockImpl();
        b.addPoint(new Coordinate(1, 0));
        final Coordinate insertionPoint = new Coordinate(2, 2);

        assertTrue("An error in test writing has occurred or the classes has been changed",
                   this.level.getOverlap(insertionPoint, b).size() == 1
                && this.level.getOverlap(insertionPoint, b).contains(insertionPoint));
    }

}

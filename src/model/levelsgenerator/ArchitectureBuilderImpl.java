package model.levelsgenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import enumerators.Faction;
import model.levelsgenerator.geometry.BlockImpl;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.levelsgenerator.geometry.GridImpl;
import model.math.BallsUrn;

/**
 * An implementation for the ArchitectureBuilder interface that uses Coordinate, GridImpl, EntityBlock and BallsUrnImpl for generate a walkable random level
 * given at least one neutral and one psycho architecture entity.
 * This class is implemented keeping in mind that will be together with a class that will imports the entities and initialize 
 * the blocks and them conditions.
 */
public final class ArchitectureBuilderImpl implements ArchitectureBuilder {

    private static final Coordinate DEFAULT_STARTING_POINT = new Coordinate(0, 1);
    private static final int CONTIGUOUS_ELEMENTS_PERCENTAGE = 50;
    private final Map<EntityBlock, BallsUrn> neutralEnv;
    private final Map<EntityBlock, BallsUrn> hostileEnv;
    private final GridImpl level;
    private final GridImpl trace;
    private final BlockImpl tolerance;
    private LevelGenerationEntity floor;
    private final Random randomIterator;


   /**
    * The constructor for the architecture builder.
    * @param architecture is an already initialized map of entities with the components "Architecture" in them.
    * @param gridDimension is the level dimension.
    * @param jumpingDistance is a Coordinate where the x is the maximum horizontal jumping distance, and the y the maximum vertical jumping height.
    */
    public ArchitectureBuilderImpl(final Map<EntityBlock, BallsUrn> architecture, final Coordinate gridDimension, final Coordinate jumpingDistance) {
        this.neutralEnv = new HashMap<>();
        this.hostileEnv = new HashMap<>();
        for (final EntityBlock e : architecture.keySet()) {
            if (e.getEntity().getType().equals(Faction.PSYCO_IMMORTAL) || e.getEntity().getType().equals(Faction.PSYCO_MORTAL)) {
                this.hostileEnv.put(e, architecture.get(e));
            } else {
                this.neutralEnv.put(e, architecture.get(e));
            }
        }

        this.level = new GridImpl(gridDimension.getPoint().x, gridDimension.getPoint().y);
        this.trace = new GridImpl(gridDimension.getPoint().x, jumpingDistance.getPoint().x);
        this.randomIterator = new Random();

        /*Create the triangular block that represents the coordinates that can be reached with a jump*/
        this.tolerance = new BlockImpl();
        for (int i = 0; i < jumpingDistance.getPoint().x; i++) {
            for (int j = 0; j < jumpingDistance.getPoint().y; j++) {
                this.tolerance.addPoint(new Coordinate(i, j));
            }
        }

        /*remove all the possible floor blocks from the spawn table, keeping one for the player placing*/
        final List<EntityBlock> floors = this.hostileEnv.keySet().stream()
                              .filter(b -> b.getEntity().getEntityName().equals("Floor"))
                              .collect(Collectors.toList());
        floors.stream().peek(f -> this.floor = f.getEntity()).forEach(f -> this.hostileEnv.remove(f));
        this.placeFloor();
    }

    private void placeFloor() {
        for (int i = 0; i < this.level.getSize().getPoint().x; i++) {
            if (i < this.trace.getSize().getPoint().x) {
                trace.setElement(new Coordinate(i, 0), this.floor);
            }
            level.setElement(new Coordinate(i, 0), this.floor);
        }
    }
    /**
     * Get the next random point where place an architectural element.
     * @param actualPoint
     * @return
     */
    private Coordinate getNextPoint(final Coordinate actualPoint) {
        final List<Coordinate> possibilities = this.level.getOverlap(actualPoint, this.tolerance);
        return possibilities.get(this.randomIterator.nextInt(possibilities.size()));
    }

    private Coordinate findFirstPoint() {
        final Optional<Coordinate> nearestPlatform = this.trace.getSnapshot().entrySet().stream()
                                                                                        .filter(tiles -> tiles.getValue().equals(this.floor))
                                                                                        .filter(es -> es.getValue().getType().equals(Faction.NEUTRAL_IMMORTAL) 
                                                                                                   || es.getValue().getType().equals(Faction.NEUTRAL_MORTAL))
                                                                                        .map(e -> e.getKey())
                                                                                        .max((c1, c2) -> c1.getPoint().x - c2.getPoint().x);
        if (nearestPlatform.equals(Optional.empty())) {
            return ArchitectureBuilderImpl.DEFAULT_STARTING_POINT;
        } else {
            return new Coordinate(-(nearestPlatform.get().getPoint().x - this.trace.getSize().getPoint().x), 
                                  nearestPlatform.get().getPoint().y); 
        }
    }

    private EntityBlock chooseEntity(final Map<EntityBlock, BallsUrn> blockList) {
        BallsUrn.Color ball = BallsUrn.Color.WHITE;
        while (ball.equals(BallsUrn.Color.WHITE)) {
            for (final EntityBlock b : blockList.keySet()) {
                ball = blockList.get(b).getBall();
                if (ball.equals(BallsUrn.Color.BLACK)) {
                    return b;
                }
            }
        }
        return null;
    }

    private void updateTrace() {
        final int minimumInterestingX = this.level.getSize().getPoint().x - this.trace.getSize().getPoint().x;
        this.level.getSnapshot().keySet().stream()
                                         .filter(c -> c.getPoint().x > minimumInterestingX)
                                         .forEach(c -> this.trace.setElement(c.sub(new Coordinate(minimumInterestingX, 0)), this.level.getElement(c)));
    }

    @Override
    public Grid getArchitecture(final Grid grid) {
        /*Initialize local variables*/
        this.updateTrace();
        this.level.reset();
        this.placeFloor();
        Coordinate point = this.findFirstPoint();
        EntityBlock selectedBlock = this.hostileEnv.keySet().iterator().next();
        Boolean entityLock = Boolean.FALSE;

        /*Get the first point based on the last platform in the trace*/
        while (this.level.isInMatrixBounds(point).equals(Boolean.FALSE)) {
            point = this.findFirstPoint();
            point = this.getNextPoint(point);
        }

        /*While the function didn't fill the whole horizontal length of the matrix, keep placing architecture*/
        while (point.getPoint().x < this.level.getSize().getPoint().x) {
            if (entityLock.equals(Boolean.FALSE)) {                            //if entityLock is true, keep the previous entity, else choose a new entity to place.
                if (this.hostileEnv.containsKey(selectedBlock)) {              //if the previous entity placed is an obstacle, choose a neutral one, else choose a random entity.
                    selectedBlock = this.chooseEntity(this.neutralEnv);
                } else {
                    final Integer flipACoin = this.randomIterator.nextInt(2);
                    if (flipACoin.equals(1)) {
                        selectedBlock = this.chooseEntity(this.neutralEnv);
                    } else {
                        selectedBlock = this.chooseEntity(this.hostileEnv);
                    }
                }
            }

            if (selectedBlock.verifyPlacingConditions(this.level, point)) {   //if the block can be placed place it and choose if activate or deactivate the entityLock.
                this.level.place(point, selectedBlock);

                if (this.randomIterator.nextInt(100) < ArchitectureBuilderImpl.CONTIGUOUS_ELEMENTS_PERCENTAGE) {
                    point = point.sum(new Coordinate(1, 0));    //if the entityLock is activated, try to place the same entity in the adjacent point (in the next iteration of the cycle)
                    entityLock = Boolean.TRUE;
                } else {
                    point = this.getNextPoint(point);           //if the entityLock is deactivated, choose a new point from the reachable points.
                    entityLock = Boolean.FALSE;
                } 
            } else {                                                          //if the block cannot be placed, deactivate the entityLock and restart the cycle.
                entityLock = Boolean.FALSE;
            }
        }
        return this.level;
    }

}

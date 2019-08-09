package model.levelsgenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import enumerators.Faction;
import model.levelsgenerator.geometry.BlockImpl;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.levelsgenerator.geometry.GridImpl;
import model.math.BallsUrnImpl;

public class ArchitectureBuilderImpl implements ArchitectureBuilder {

    private final Map<EntityBlock, BallsUrnImpl> neutralEnv;
    private final Map<EntityBlock, BallsUrnImpl> hostileEnv;
    private final GridImpl level;
    private final GridImpl trace;
    private final BlockImpl tolerance;
    private final Random randomIterator;


   /**
    * The constructor for the architecture builder.
    * @param architecture is an already initialized map of entities with the components "Architecture" in them.
    * @param gridDimension is the level dimension.
    * @param jumpingDistance is a Coordinate where the x is the maximum horizontal jumping distance, and the y the maximum vertical jumping height.
    */
    public ArchitectureBuilderImpl(final Map<EntityBlock, BallsUrnImpl> architecture, final Coordinate gridDimension, final Coordinate jumpingDistance) {
        this.neutralEnv = new HashMap<>();
        this.hostileEnv = new HashMap<>();
        for (EntityBlock e : architecture.keySet()) {
            if (e.getEntity().getType().equals(Faction.PSYCO_IMMORTAL) || e.getEntity().getType().equals(Faction.PSYCO_MORTAL)) {
                this.hostileEnv.put(e, architecture.get(e));
            } else {
                this.neutralEnv.put(e, architecture.get(e));
            }
        }

        this.level = new GridImpl(gridDimension.getPoint().x, gridDimension.getPoint().y);
        this.trace = new GridImpl(jumpingDistance.getPoint().x, gridDimension.getPoint().y);
        this.randomIterator = new Random();

        /*Create the triangular block that represents the coordinates that can be reached with a jump*/
        this.tolerance = new BlockImpl();
        for (int i = 0; i < jumpingDistance.getPoint().x; i++) {
            for (int j = 0; j < jumpingDistance.getPoint().y; j++) {
                this.tolerance.addPoint(new Coordinate(i, j));
            }
        }
    }

    private Coordinate getNextPoint(Coordinate actualPoint) {
        List<Coordinate> possibilities = this.level.getOverlap(actualPoint, this.tolerance);
        return possibilities.get(this.randomIterator.nextInt(possibilities.size()));
    }

    private void findFirstPoint() {
        final Optional<Coordinate> nearestPlatform = this.trace.getSnapshot().entrySet().stream()
                                                                                        .filter(es -> es.getValue().getType().equals(Faction.NEUTRAL_IMMORTAL) 
                                                                                                   || es.getValue().getType().equals(Faction.NEUTRAL_MORTAL))
                                                                                        .map(e -> e.getKey())
                                                                                        .max((c1, c2) -> c1.getPoint().x - c2.getPoint().x);
        if (nearestPlatform.equals(Optional.empty())) {

        } else {
            new Coordinate(-(nearestPlatform.get().getPoint().x-this.trace.))) 
        }
    }

    @Override
    public Grid getArchitecture(final Grid grid) {
        // TODO Auto-generated method stub
        return null;
    }

}

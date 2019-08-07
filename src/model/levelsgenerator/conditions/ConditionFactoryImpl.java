package model.levelsgenerator.conditions;

import java.util.ArrayList;
import java.util.List;

import enumerators.Faction;
import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.geometry.BlockInsertion;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.GridImpl;
import model.math.Function;
import model.utility.Pair;

/**
 * An implementation of the ConditionFactory interface that uses GridImpl (or a derived class), Coordinate, Pair and LevelGenerationEntity.
 */
public class ConditionFactoryImpl implements ConditionFactory {

    private static final int VITAL_SPACE = 1;
    @Override
    public final Condition mustBeOnGround() {
        final Condition mustBeOnGround = new ConditionImpl();

        mustBeOnGround.addCondition(new Function<BlockInsertion<? extends GridImpl, ? extends EntityBlock, ? extends Coordinate>, Boolean>() {

            public Boolean apply(final BlockInsertion<? extends GridImpl, ? extends EntityBlock, ? extends Coordinate> i) {

                final GridImpl context = i.getContext();
                final EntityBlock block = i.getBlock();
                final Coordinate enteringPoint = i.getInsertionPoint();

                final Integer yMin = context.getOverlap(enteringPoint, block).stream()
                                                                             .map(c -> c.getPoint().y)
                                                                             .min((y1, y2) -> Integer.compare(y1, y2))
                                                                             .get();
                return (context.getOverlap(enteringPoint, block).stream()
                                                                .filter(p -> p.getPoint().y == yMin)
                                                                .map(p -> context.getElement(p.sub(new Coordinate(1, 1))))
                                                                .allMatch(e -> e.getComponentsSet().contains("Architecture")));
            }
        });
        return mustBeOnGround;
    }

    @Override
    public final Condition notTooNearRival() {
        final Condition notTooNearRival = new ConditionImpl();

        notTooNearRival.addCondition(new Function<BlockInsertion<? extends GridImpl, ? extends EntityBlock, ? extends Coordinate>, Boolean>() {

            public Boolean apply(final BlockInsertion<? extends GridImpl, ? extends EntityBlock, ? extends Coordinate> i) {

                final GridImpl context = i.getContext();
                final EntityBlock block = i.getBlock();
                final Coordinate enteringPoint = i.getInsertionPoint();

                final List<Coordinate> vitalSpace = context.getOverlap(enteringPoint, block);

                for (final Coordinate c : context.getOverlap(enteringPoint, block)) {
                    for (int j = 1; j <= ConditionFactoryImpl.VITAL_SPACE; j++) {

                        final List<Coordinate> partial = new ArrayList<>();
                        partial.add(new Coordinate(0, j));
                        partial.add(new Coordinate(j, 0));
                        partial.add(new Coordinate(j, j));

                        partial.stream()
                               .peek(p -> vitalSpace.add(c.sum(p)))
                               .peek(p -> vitalSpace.add(c.sub(p)));
                    }
                }
                /*if the block is an ally to the player, check if there are enemies around, 
                 * else check if there are non-enemies (excluding architecture and empty blocks)*/
                if (block.getEntity().getType().equals(Faction.NEUTRAL_MORTAL)) {
                    return (vitalSpace.stream()
                                      .map(p -> context.getElement(p).getType())
                                      .noneMatch(e -> e.equals(Faction.PSYCO_MORTAL) || e.equals(Faction.PSYCO_IMMORTAL)));
                } else {
                   return (vitalSpace.stream()
                                     .filter(c -> !context.getElement(c).getComponentsSet().contains("Architecture") 
                                                  || context.getElement(c).equals(context.getVoid()))
                                     .map(p -> context.getElement(p).getType())
                                     .anyMatch(e -> e.equals(Faction.NEUTRAL_MORTAL) || e.equals(Faction.NEUTRAL_IMMORTAL)));
                }
            }
        });
        return notTooNearRival;
    }
}

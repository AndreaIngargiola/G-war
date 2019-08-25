package model.levelsgenerator.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import enumerators.Faction;
import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.LevelGenerationEntity;
import model.levelsgenerator.geometry.BlockInsertion;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.math.Function;

/**
 * An implementation of the ConditionFactory interface that uses Grid, Coordinate and LevelGenerationEntity.
 */
public class ConditionFactoryImpl implements ConditionFactory {

    private static final int VITAL_SPACE = 1;

    @Override
    public final Condition mustBeOnGround() {
        final Condition mustBeOnGround = new ConditionImpl();
        mustBeOnGround.addCondition(new Function<BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate>, Boolean>() {

            public Boolean apply(final BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate> i) {

                final Grid context = i.getContext().getCopy();
                final EntityBlock block = (EntityBlock) i.getBlock().getCopy();
                final Coordinate enteringPoint = i.getInsertionPoint().getSafeCopy();

                //find the lowest tile of the block.
                final Integer yMin = context.getOverlap(enteringPoint, block).stream()
                                                                             .map(c -> c.getPoint().y)
                                                                             .min((y1, y2) -> Integer.compare(y1, y2))
                                                                             .get();

                //find all the tiles of the block at that height.
                final List<Coordinate> bottomSide = context.getOverlap(enteringPoint, block).stream()
                                                                                            .filter(p -> p.getPoint().y == yMin)
                                                                                            .collect(Collectors.toList());

                //find all the matrix coordinates in which the bottom side lays. These coordinates will be memorized in support.
                final List<LevelGenerationEntity> support = new ArrayList<>();
                try {
                    bottomSide.stream().forEach(c -> support.add(context.getElement(c.sub(new Coordinate(0, 1)))));
                } catch (IllegalArgumentException exeption) {
                    return false;
                }

                //return if all the supports block are architectural elements and not obstacles of some kind.
                return (support.stream().allMatch(e -> (e.getComponentsSet().contains("Architecture") 
                                                   && (e.getType().equals(Faction.NEUTRAL_IMMORTAL) 
                                                       || e.getType().equals(Faction.NEUTRAL_MORTAL)))));
            }
        });
        return mustBeOnGround;
    }

    @Override
    public final Condition notTooNearRival() {
        final Condition notTooNearRival = new ConditionImpl();

        notTooNearRival.addCondition(new Function<BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate>, Boolean>() {
            public Boolean apply(final BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate> i) {

                final Grid context = i.getContext().getCopy();
                final EntityBlock block = (EntityBlock) i.getBlock().getCopy();
                final Coordinate enteringPoint = i.getInsertionPoint().getSafeCopy();

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

    @Override
    public final Condition leaveMeAlone() {
        final Condition leaveMeAlone = new ConditionImpl();
        leaveMeAlone.addCondition(new Function<BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate>, Boolean>() {

            public Boolean apply(final BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate> i) {

                final Grid context = i.getContext().getCopy();
                final Coordinate enteringPoint = i.getInsertionPoint().getSafeCopy();

                //project the entering point of the entity in the first architectural element.
                int yRay = enteringPoint.getPoint().y;
                int xRay = enteringPoint.getPoint().x;
                while (yRay >= 0 && !context.getElement(new Coordinate(xRay, yRay)).getComponentsSet().contains("Architecture")) {
                    yRay = yRay - 1;
                }

                //seek all the points of the platform detected by the yRay.
                final List<Coordinate> platform = new ArrayList<>();

                while (xRay < context.getSize().getPoint().x
                       && context.getElement(new Coordinate(xRay, yRay)).getComponentsSet().contains("Architecture")) {

                    platform.add(new Coordinate(xRay, yRay));
                    xRay = xRay + 1;
                }

                xRay = enteringPoint.getPoint().x;
                while (xRay >= 0 && context.getElement(new Coordinate(xRay, yRay)).getComponentsSet().contains("Architecture")) {

                     platform.add(new Coordinate(xRay, yRay));
                     xRay = xRay - 1;
                }

                //return true if all the tiles above the platform are free.
                return (platform.stream()
                                .filter(c -> context.isInMatrixBounds(c.sum(new Coordinate(0, 1))))
                                .map(c -> c.sum(new Coordinate(0, 1)))
                                .allMatch(c -> context.getElement(c).equals(context.getVoid())));
            }
        });
        return leaveMeAlone;
    }
}

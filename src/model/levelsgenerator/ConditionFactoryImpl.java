package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;

import enumerators.Faction;
import model.math.Function;
import model.utility.Pair;

public class ConditionFactoryImpl implements ConditionFactory {

    private static final int VITAL_SPACE = 1;
    @Override
    public final Condition mustBeOnGround() {
        Condition mustBeOnGround = new ConditionImpl();

        mustBeOnGround.addCondition(new Function<Pair<EntityBlock, Pair<Coordinate, ? extends GridImpl>>, Boolean>() {

            public Boolean apply(final Pair<EntityBlock, Pair<Coordinate, ? extends GridImpl>> i) {

                final GridImpl context = i.getY().getY();
                final EntityBlock block = i.getX();
                final Coordinate enteringPoint = i.getY().getX();

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
        Condition notTooNearRival = new ConditionImpl();

        notTooNearRival.addCondition(new Function<Pair<EntityBlock, Pair<Coordinate, ? extends GridImpl>>, Boolean>() {

            public Boolean apply(final Pair<EntityBlock, Pair<Coordinate, ? extends GridImpl>> i) {

                final GridImpl context = i.getY().getY();
                final EntityBlock block = i.getX();
                final Coordinate enteringPoint = i.getY().getX();

                List<Coordinate> vitalSpace = context.getOverlap(enteringPoint, block);

                for (Coordinate c : context.getOverlap(enteringPoint, block)) {
                    for (int j = 1; j <= ConditionFactoryImpl.VITAL_SPACE; j++) {

                        List<Coordinate> partial = new ArrayList<>();
                        partial.add(new Coordinate(0, j));
                        partial.add(new Coordinate(j, 0));
                        partial.add(new Coordinate(j, j));

                        partial.stream()
                               .peek(p -> vitalSpace.add(c.sum(p)))
                               .peek(p -> vitalSpace.add(c.sub(p)));
                    }
                }
                /*if the block is an ally to the player, check if there are enemies around, else check if there are non-enemies (excluding architecture)*/
                if (block.getEntity().getType().equals(Faction.NEUTRAL_MORTAL)) {
                    return (vitalSpace.stream()
                                      .map(p -> context.getElement(p).getType())
                                      .noneMatch(e -> e.equals(Faction.PSYCO_MORTAL) || e.equals(Faction.PSYCO_IMMORTAL)));
                }
                else {
                   return (vitalSpace.stream()
                                     .filter(c -> !context.getElement(c).getComponentsSet().contains("Architecture"))
                                     .map(p -> context.getElement(p).getType())
                                     .anyMatch(e -> e.equals(Faction.NEUTRAL_MORTAL) || e.equals(Faction.NEUTRAL_IMMORTAL)));
                }
            }
        });
        return notTooNearRival;
    }
}

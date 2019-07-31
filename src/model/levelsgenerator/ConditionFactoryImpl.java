package model.levelsgenerator;

import java.util.List;

import model.components.Feet;
import model.entities.Entity;
import model.math.Function;
import model.utility.Pair;

public class ConditionFactoryImpl implements ConditionFactory {

    @Override
    public final Condition getMustBeOnGround() {
        Condition prov = new ConditionImpl();
        prov.addCondition(new Function<Pair<EntityBlock, ? extends GridImpl>, Boolean>(){

            public Boolean apply(Pair<EntityBlock, ? extends GridImpl> i) {

                GridImpl context = i.getY();
                EntityBlock block = i.getX();
                return (block.getRelativeCoordinates().stream()
                                                  .filter(p -> p.equals(block.getYmin()))
                                                  .allMatch());
            }
   });
        prov.addCondition(b -> b.stream);
    }
}

package model.levelsgenerator;
import model.math.Function;

public class ConditionGiverImpl implements ConditionGiver {

    @Override
    public Condition getConditions(String componentInterfaceName) {
        new Function<String, Condition>() {

            @Override
            public Condition apply(final String componentInterfaceName) {
                final ConditionFactoryImpl conditionFactory = new ConditionFactoryImpl();
                final Condition totalConditions = new ConditionImpl();
                totalConditions.addCondition(newFunction);
                switch(componentInterfaceName) {
                case ("Feet") :
                     conditionFactory.mustBeOnGround();
                }
            }

        };
        return null;
    }

}

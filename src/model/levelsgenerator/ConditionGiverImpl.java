package model.levelsgenerator;
import model.math.Function;

public class ConditionGiverImpl implements ConditionGiver {

    @Override
    public Condition getConditions(String componentInterfaceName) {
        new Function<String, Condition>() {

            @Override
            public Condition apply(String i) {
                final ConditionFactoryImpl conditionFactory = new ConditionFactoryImpl();
                
                return null;
            }
            
        }
        return null;
    }

}

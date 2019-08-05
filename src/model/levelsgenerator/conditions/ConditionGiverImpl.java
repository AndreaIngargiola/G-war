package model.levelsgenerator.conditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DefaultConditionGiver
public class ConditionGiverImpl implements ConditionGiver {

    @Override
    public Optional<List<Condition>> getConditions(final String componentInterfaceName) {

        final ConditionFactoryImpl conditionFactory = new ConditionFactoryImpl();
        List<Condition> conditions = new ArrayList<>();

        switch (componentInterfaceName) {

        case ("Feet") :
             conditions.add(conditionFactory.mustBeOnGround());
        break;

        case ("Life") :
            conditions.add(conditionFactory.mustBeOnGround());
        default:

        }
        return (conditions.isEmpty()) ? Optional.empty() : Optional.of(conditions);
    }

}

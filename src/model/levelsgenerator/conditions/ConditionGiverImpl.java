package model.levelsgenerator.conditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is the default condition giver that prevents the walking entities from spawning mid-air and 
 * entities that can get damage from spawn too near to entities with opposed faction. 
 */
@DefaultConditionGiver
public final class ConditionGiverImpl implements ConditionGiver {

    @Override
    public Optional<List<Condition>> getConditions(final String componentInterfaceName) {

        final ConditionFactoryImpl conditionFactory = new ConditionFactoryImpl();
        final List<Condition> conditions = new ArrayList<>();

        switch (componentInterfaceName) {

        case ("Movement") :
             conditions.add(conditionFactory.mustBeOnGround());
        break;

        case ("Life") :
            conditions.add(conditionFactory.notTooNearRival());
        default:

        }
        return (conditions.isEmpty()) ? Optional.empty() : Optional.of(conditions);
    }

}

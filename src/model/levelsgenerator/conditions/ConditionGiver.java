package model.levelsgenerator.conditions;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents the contract between the interface implemented by an entity component and its placing condition in the level generation.
 * Can be implemented with custom implementation for a custom association between a particular component with some placing conditions.
 */
public interface ConditionGiver {
    /**
     * Given a component interface name, get all the condition associated with it.
     * @param componentInterfaceName is the name of the logic OOP contract of the component: given that abstraction is considered appropriate associate 
     * the placing condition with it.
     * @return Optionl.empty if there aren't condition associated with that component, an optional list of condition otherwise.
     */
    Optional<List<Condition>> getConditions(final String componentInterfaceName);
}

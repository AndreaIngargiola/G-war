package model.levelsgenerator;

import java.util.List;
import java.util.Optional;

public interface ConditionGiver {
    Optional<List<Condition>> getConditions(final String componentInterfaceName);
}

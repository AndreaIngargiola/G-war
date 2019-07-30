package model.levelsgenerator;
import model.components.Feet;

public interface ConditionFactory {

    Condition<Feet> getConditionFeet();
}

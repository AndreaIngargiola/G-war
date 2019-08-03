package model.levelsgenerator;

public interface ConditionFactory {

    Condition mustBeOnGround();

    Condition notTooNearRival();
}

package view;

import enumerators.EntityState;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * Models an interface for an Entity view.
 */
public interface EntityView {

    /**
     *  Used to set the entity view position.
     * 
     * @param position
     *            the new position to set for the entity view
     */
    void setPosition(Point2D position);

    /**
     * Used to set the entity view dimension.
     * 
     * @param dimension
     *            the dimension to set for the entity view
     */
    void setDimension(Dimension2D dimension);

    /**
     * @return the current entity view position.
     */
    Point2D getPosition();

    /**
     * Used to remove the entity view from the world view.
     */
    void remove();

    /**
     * 
     * @param state
     *            the new entity state
     */
    void changeState(EntityState state);

}

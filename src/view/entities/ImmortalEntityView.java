package view.entities;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;

/**
 * Models a basic view for an ImmortalEntity.
 * 
 */
public class ImmortalEntityView extends AbstractEntityView {

    /**
     * 
     * @param group
     *            the {@link Group} instance in which the entity view is added
     * @param dimension
     *            the dimension of the entity view
     */
    public ImmortalEntityView(final Group group, final Dimension2D dimension) {
        super(group, dimension);
    }

}

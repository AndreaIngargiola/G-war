package view.entities;

import enumerators.EntityState;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;

/**
 * Models the view of a coward entity.
 *
 */
public final class CowardView extends AbstractMortalEntityView {

    private static final int WIDTH = 10, HEIGHT = 10;

    /**
     * @param group
     *            the {@link Group} instance in which the entity view is added.
     */
    public CowardView(final Group group) {
        super(group, new Dimension2D(WIDTH, HEIGHT));
        getAnimations().put(EntityState.WALKING, justAnImage(new Image("img/enemy.png")));
        startAnimation(EntityState.WALKING);
    }

}

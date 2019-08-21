package viewGame;

import enumerators.EntityState;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;

/**
 * Models the view of a grill entity.
 */
public class GrillView extends ImmortalEntityView {

    private static final int WIDTH = 10, HEIGHT = 10;

    /**
     * 
     * @param group
     *           the {@link Group} instance in which the entity view is added
     */
    public GrillView(final Group group) {
        super(group, new Dimension2D(WIDTH, HEIGHT));
        getAnimations().put(EntityState.OFF, justAnImage(new Image("img/platform.jpg")));
        getAnimations().put(EntityState.ON, justAnImage(new Image("img/grill.png")));
        startAnimation(EntityState.OFF);
    }
}

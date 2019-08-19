package view;

import java.util.HashMap;
import java.util.Map;

import enumerators.EntityState;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Base class for {@link EntityView}.
 */
public abstract class AbstractEntityView implements EntityView {

    private final ImageView view = new ImageView();
    private final Group parentView;
    private final Map<EntityState, Runnable> animations = new HashMap<>();

    /**
     * 
     * @param group
     *            the {@link Group} instance in which the entity view is added
     * @param dimension
     *            the dimension of the entity view
     */
    public AbstractEntityView(final Group group, final Dimension2D dimension) {
        parentView = group;
        setDimension(dimension);

        group.getChildren().add(view);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setPosition(final Point2D pos) {
        view.setTranslateX(pos.getX() - view.getBoundsInLocal().getWidth() / 2);
        view.setTranslateY(pos.getY() - view.getBoundsInLocal().getHeight() / 2);
    }

    @Override
    public final Point2D getPosition() {
        return new Point2D(view.getTranslateX(), view.getTranslateY());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setDimension(final Dimension2D dimension) {
        view.setFitWidth(dimension.getWidth());
        view.setFitHeight(dimension.getHeight());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void remove() {
        parentView.getChildren().remove(view);
    }


    @Override
    public final void changeState(final EntityState state) {
        if (animations.containsKey(state)) {
            animations.get(state).run();
        }
    }

    /**
     * 
     * @return the related {@link ImageView} instance.
     */
    protected ImageView getView() {
        return view;
    }

    /**
     * 
     * @return the belonging {@link Group} instance.
     */
    protected Group getParentView() {
        return parentView;
    }

    /**
     * 
     * @return animations
     */
    protected Map<EntityState, Runnable> getAnimations() {
        return animations;
    }

    /**
     * Used to make a static sprite animation.
     * 
     * @param image
     *            The sprite {@link Image} instance.
     * @return A {@link Runnable} animation instance.
     */
    protected Runnable justAnImage(final Image image) {
        return () -> {
            setImage(image);
        };
    }

    /**
     * Used to set an image for the entity view.
     * 
     * @param image
     *            The {@link Image} instance to set for the entity view.
     */
    protected void setImage(final Image image) {
        getView().setImage(image);
    }

    /**
     * Used to start an animation from the mapped animations.
     * 
     * @param state
     *            The {@link EntityState} instance associated to the specific animation that has to start.
     */
    protected void startAnimation(final EntityState state) {
        getAnimations().get(state).run();
    }

}

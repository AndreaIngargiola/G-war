package view;

import java.io.File;

import enumerators.EntityState;
import enumerators.HorizontalDirection;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;

/**
 * Models the view of the player entity.
 *
 */
public final class PlayerView extends AbstractMortalEntityView {

    private static final int ADDICTIONAL_PUNCH_1 = 5;
    private static final int ADDICTIONAL_PUNCH_2 = 10;
    private static final int ADDICTIONAL_PUNCH_3 = 15;
    private static final int WIDTH = 10, HEIGHT = 10;
    private final StatisticsView statistics;
    private final Media jumpSound = new Media(new File(new File("res/music/highUp.wav").getAbsolutePath()).toURI().toString());
    private Line punch = new Line();

    /**
     * @param group
     *            The {@link Group} instance in which the player view is added.
     * @param statistics
     *            The {@link StatisticsView} instance associated with the player.
     */
    public PlayerView(final Group group, final StatisticsView statistics) {
        super(group, new Dimension2D(WIDTH, HEIGHT));
        this.statistics = statistics;
        getAnimations().put(EntityState.WALKING, justAnImage(new Image("img/player.png")));
        getAnimations().put(EntityState.ANGRY, justAnImage(new Image("img/playerAngry.png")));
        startAnimation(EntityState.WALKING);
    }

    @Override
    public void setPosition(final Point2D pos) {
        super.setPosition(pos);

        getView().getParent()
                .setTranslateX(-getView().getTranslateX() * scaling().getMxx() + getView().getScene().getWidth() / 2);
    }

    @Override
    public void punch() {
        this.changeState(EntityState.ANGRY);
        if (this.getDirection().equals(HorizontalDirection.RIGHT)) {
            this.punch.setStartX(this.getPosition().getX() + ADDICTIONAL_PUNCH_2);
            this.punch.setStartY(this.getPosition().getY() + ADDICTIONAL_PUNCH_1);
            this.punch.setEndX(this.getPosition().getX() + ADDICTIONAL_PUNCH_3);
            this.punch.setEndY(this.getPosition().getY() + ADDICTIONAL_PUNCH_1);
        } else {
            this.punch.setStartX(this.getPosition().getX());
            this.punch.setStartY(this.getPosition().getY() + ADDICTIONAL_PUNCH_1);
            this.punch.setEndX(this.getPosition().getX() - ADDICTIONAL_PUNCH_1);
            this.punch.setEndY(this.getPosition().getY() + ADDICTIONAL_PUNCH_1);
        }
        this.getParentView().getChildren().add(punch);
    }

    @Override
    public void stopPunch() {
        this.changeState(EntityState.WALKING);
        this.getParentView().getChildren().remove(punch);
    }

    @Override
    public void angryAnimation() {
        changeState(EntityState.ANGRY);
    }

    @Override
    public void endAngryAnimation() {
        changeState(EntityState.WALKING);
    }

    @Override
    public void changeLife(final int life) {
        statistics.setCurrentHealth(life);
    }

    @Override
    public void changePoints(final int points) {
        statistics.setPoints(points);
    }

    @Override
    public void jumpSound()  {
        this.setMediaPlayer(new MediaPlayer(this.jumpSound));
        this.getMediaPlayer().play();
    }

    private Scale scaling() {
        return getParentView().getTransforms().stream().filter(t -> t instanceof Scale).map(t -> (Scale) t)
                .findFirst().orElseGet(() -> new Scale(1, 1));
    }

}

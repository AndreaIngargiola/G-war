package view.entities;

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

    private static final double ADDICTIONAL_PUNCH_1 = 0.5;
    private static final double ADDICTIONAL_PUNCH_2 = 4.5;
    private static final double ADDICTIONAL_PUNCH_3 = 5.5;
    private static final double ADDICTIONAL_PUNCH_4 = 9.5; 
    private static final double ADDICTIONAL_PUNCH_5 = 14.5;
    private static final double WIDTH = 9, HEIGHT = 9;
    private final StatisticsView statistics;
    private final Media jumpSound = new Media(getClass().getResource("/music/highUp.wav").toExternalForm());
    private final Line punchView = new Line();

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
        this.updatePunch();
        this.getParentView().getChildren().add(this.punchView);
    }

    @Override
    public void stopPunch() {
        if (this.getParentView().getChildren().contains(this.punchView)) {
            this.changeState(EntityState.WALKING);
            this.getParentView().getChildren().remove(this.punchView);
        }
    }

    @Override
    public void updatePunch() {
        if (this.getDirection().equals(HorizontalDirection.RIGHT)) {
            this.punchView.setStartX(this.getPosition().getX() + PlayerView.ADDICTIONAL_PUNCH_4);
            this.punchView.setStartY(this.getPosition().getY() + PlayerView.ADDICTIONAL_PUNCH_2);
            this.punchView.setEndX(this.getPosition().getX() + PlayerView.ADDICTIONAL_PUNCH_5);
            this.punchView.setEndY(this.getPosition().getY() + PlayerView.ADDICTIONAL_PUNCH_2);
        } else {
            this.punchView.setStartX(this.getPosition().getX() - PlayerView.ADDICTIONAL_PUNCH_1);
            this.punchView.setStartY(this.getPosition().getY() + PlayerView.ADDICTIONAL_PUNCH_2);
            this.punchView.setEndX(this.getPosition().getX() - PlayerView.ADDICTIONAL_PUNCH_3);
            this.punchView.setEndY(this.getPosition().getY() + PlayerView.ADDICTIONAL_PUNCH_2);
        }
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
    public void makeJumpSound()  {
        this.setMediaPlayer(new MediaPlayer(this.jumpSound));
        this.getMediaPlayer().play();
    }

    private Scale scaling() {
        return getParentView().getTransforms().stream().filter(t -> t instanceof Scale).map(t -> (Scale) t)
                .findFirst().orElseGet(() -> new Scale(1, 1));
    }

}

package view.entities;

import java.util.Optional;

import enumerators.HorizontalDirection;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Implementation of {@link MortalEntityView}.
 */
public abstract class AbstractMortalEntityView extends AbstractEntityView implements MortalEntityView {

    private final Media collisionSound = new Media(getClass().getResource("/music/collision.wav").toExternalForm());
    private Optional<MediaPlayer> mediaPlayer = Optional.empty();
    private HorizontalDirection direction = HorizontalDirection.RIGHT;
    /**
     * 
     * @param group
     *            the {@link Group} instance in which the entity view is added
     * @param dimension
     *            the dimension of the entity view
     */
    public AbstractMortalEntityView(final Group group, final Dimension2D dimension) {
        super(group, dimension);
    }

    @Override
    public final void changeDirection(final HorizontalDirection direction) {
        this.direction = direction;
        if (direction.equals(HorizontalDirection.LEFT)) {
            getView().setScaleX(-1);
        } else {
            getView().setScaleX(1);
        }
    }

    @Override
    public final void makeCollisionSound() {
        this.setMediaPlayer(new MediaPlayer(this.collisionSound));
        this.getMediaPlayer().play();
    }

    @Override
    public void punch() {
    }

    @Override
    public void stopPunch() {
    }

    @Override
    public void angryAnimation() {
    }

    @Override
    public void endAngryAnimation() {
    }

    @Override
    public void changeLife(final int life) {
    }

    @Override
    public void changePoints(final int points) {
    }

    @Override
    public void makeJumpSound()  {
    }

    @Override
    public void updatePunch() {
    }

    /**
     * 
     * @return the face direction
     */
    protected final HorizontalDirection getDirection() {
        return this.direction;
    }

    /**
     * 
     * @return mediaPlayer
     */
    protected final MediaPlayer getMediaPlayer() {
        return this.mediaPlayer.get();
    }

    /**
     * 
     * @param sound
     *         the {@link MediaPlayer}
     */
    protected final void setMediaPlayer(final MediaPlayer sound) {
        if (!this.mediaPlayer.equals(Optional.empty())) {
            this.mediaPlayer.get().stop();
        }
        this.mediaPlayer = Optional.of(sound);
    }
}

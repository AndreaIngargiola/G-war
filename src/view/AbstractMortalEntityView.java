package view;

import java.io.File;

import enumerators.HorizontalDirection;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Implementation of {@link MortalEntityView}.
 */
public abstract class AbstractMortalEntityView extends AbstractEntityView implements MortalEntityView {

    private final Media collisionSound = new Media(new File(new File("src/music/collision.wav").getAbsolutePath()).toURI().toString());
    private MediaPlayer mediaPlayer;

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
        if (direction.equals(HorizontalDirection.LEFT)) {
            getView().setScaleX(-1);
        } else {
            getView().setScaleX(1);
        }
    }

    @Override
    public final void collisionSound() {
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
    public void jumpSound()  {
    }

    /**
     * 
     * @return mediaPlayer
     */
    protected final MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    /**
     * 
     * @param sound
     *         the {@link MediaPlayer}
     */
    protected final void setMediaPlayer(final MediaPlayer sound) {
        this.mediaPlayer = sound;
    }
}

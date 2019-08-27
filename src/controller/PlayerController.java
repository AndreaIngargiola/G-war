package controller;

import org.jbox2d.common.Vec2;

import com.google.common.eventbus.Subscribe;

import javafx.geometry.Point2D;
import model.components.Movement;
import model.components.Punch;
import model.entities.Entity;
import model.events.CollisionEvent;
import model.events.Death;
import model.events.EndCollision;
import model.events.JumpEvent;
import model.events.LifeChange;
import model.events.PointsChangeEvent;
import view.entities.PlayerKeyboardInput;
import view.entities.PlayerView;

/**
 * A controller for the player entity.
 *
 */
public final class PlayerController extends MortalEntityController implements PlayerInputListener {

    private final  PlayerKeyboardInput keyboard;
    private Boolean isPunching = Boolean.FALSE;
    /**
     * 
     * @param player
     *            the model of the player entity
     * @param playerView
     *            the view of the player entity
     * @param keyboard
     *            the class that manages the keyboard input
     */
    public PlayerController(final Entity player, final PlayerView playerView, final PlayerKeyboardInput keyboard) {
        super(player, playerView);
        this.keyboard = keyboard;
        this.keyboard.setListener(this);
   }

    @Override
    public void update(final double dt) {
        if (!this.getEntityModel().isAlive()) {
            this.getEntityModel().destroy();
        } else {
            super.update(dt);
            this.getEntityView().updatePunch();
        }
    }

    @Override
    public void deathListener(final Death event) {
        this.keyboard.clearListener();
        super.deathListener(event);
    }

    @Override
    public void move(final Point2D movement) {
        if (!this.isPunching) {
            final Vec2 mov = new Vec2((float) movement.getX(), (float) movement.getY());
            this.getEntityModel().get(Movement.class).move(mov);
        }
    }

    @Override
    public void punch() {
        if (!this.isPunching) {
           this.getEntityModel().get(Punch.class).punch();
           getEntityView().punch();
           this.isPunching = Boolean.TRUE;
        } else {
            getEntityView().stopPunch();
        }
    }

    @Override
    public void stopPunch() {
        getEntityView().stopPunch();
        this.isPunching = Boolean.FALSE;
    }

    @Override
    public void stop() {
        this.getEntityModel().get(Movement.class).stop();
    }

    @Override
    public void collisionListener(final CollisionEvent event) {
        super.collisionListener(event);
        getEntityView().angryAnimation();
    }

    /**
     * 
     * @param event
     *           a {@link LifeChange} event
     */
    @Subscribe
    public void lifeChangeListener(final LifeChange event) {
        getEntityView().changeLife(event.getLife());
    }

    /**
     * 
     * @param event
     *           a {@link PointsChangeEvent}
     */
    @Subscribe
    public void pointsChangeListener(final PointsChangeEvent event) {
        getEntityView().changePoints(event.getPoints());
    }

    /**
     * 
     * @param event
     *            a {@link EndCollision} event
     */
    @Subscribe
    public void endCollisionListener(final EndCollision event) {
        getEntityView().endAngryAnimation();
    }

    /**
     * 
     * @param event
     *           a {@link JumpEvent}
     */
    @Subscribe
    public void jumpListener(final JumpEvent event) {
        this.getEntityView().makeJumpSound();
    }

}

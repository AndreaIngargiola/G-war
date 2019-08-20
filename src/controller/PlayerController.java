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
import view.PlayerView;

/**
 * A controller for the player entity.
 *
 */
public final class PlayerController extends MortalEntityController implements PlayerInputListener {

    private Vec2 movement;

    /**
     * 
     * @param player
     *            the model of the player entity
     * @param playerView
     *            the view of the player entity
     */
    public PlayerController(final Entity player, final PlayerView playerView) {
        super(player, playerView);
    }

    @Override
    public void deathListener(final Death event) {
        //this.getEntityModel().get(Points.class).getCurrent();
        super.deathListener(event);
        //chiama gameover
    }

    @Override
    public void move(final Point2D movement) {
        this.movement = new Vec2((float) movement.getX(), (float) movement.getY());
        this.getEntityModel().get(Movement.class).move(this.movement);
    }

    @Override
    public void punch() {
        System.out.println("PUNCH");
        this.getEntityModel().get(Punch.class).punch();
        getEntityView().punch();
    }

    @Override
    public void stopPunch() {
        this.getEntityModel().get(Punch.class).punch();
        getEntityView().stopPunch();
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
        this.getEntityView().jumpSound();
    }

}

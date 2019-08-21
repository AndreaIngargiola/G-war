package model.physics;

import model.components.CollisionHandler;
import model.entities.Entity;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.contacts.Contact;

import enumerators.CollisionSide;
import enumerators.Faction;
import model.events.EndCollision;

/**
 * Implements a {@link ContactListener}.
 *
 */
public final class MyContactListener implements ContactListener {

    @Override
    public void beginContact(final Contact contact) {

        final Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        final Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        if (entityA.getType().equals(Faction.NEUTRAL_MORTAL)) {
            entityA.get(CollisionHandler.class).collision(entityA, entityB, checkCollisionSide(contact, entityA, entityB));
        } else if (entityB.getType().equals(Faction.NEUTRAL_MORTAL)) {
            entityB.get(CollisionHandler.class).collision(entityB, entityA, checkCollisionSide(contact, entityB, entityA));
        }
    }

    @Override
    public void endContact(final Contact contact) {

        final Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        final Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        if (entityA.getType().equals(Faction.NEUTRAL_MORTAL)) {
            entityA.post(new EndCollision(entityA));
        } else if (entityB.getType().equals(Faction.NEUTRAL_MORTAL)) {
            entityB.post(new EndCollision(entityB));
        }
    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse arg1) {
    }

    @Override
    public void preSolve(final Contact contact, final Manifold manfold) {
    }

    /**
     * 
     * @param contact
     *         the contact
     * @param entity1
     *         the first entity
     * @param entity2
     *         the second entity
     * @return the collision side of the first entity
     */
    private CollisionSide checkCollisionSide(final Contact contact, final Entity entity1, final Entity entity2) {
        final WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        final boolean collidingBottom = isCollidingBottom(worldManifold.points[0].y, worldManifold.points[1].y, entity1, entity2);
        if (collidingBottom) {
            return CollisionSide.BOTTOM;
        } else {
            return CollisionSide.OTHERS;
        }
    }

    /**
     * 
     * @param collisionY1
     *              the y coordinate of the first point of the worldManifold
     * @param collisionY2
     *              the y coordinate of the second point of the worldManifold
     * @param entity1
     *            the first entity
     * @param entity2
     *            the second entity
     * @return true if the collision side of the first entity is the bottom 
     */
    private boolean isCollidingBottom(final float collisionY1, final float collisionY2, final Entity entity1, final Entity entity2) {
        return ((collisionY1 == collisionY2) && (entity1.getCenter().y < entity2.getCenter().y));
    } 

}

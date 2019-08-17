package model.physics;

import model.entities.AbstractEntity;
import model.entities.Entity;

import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.contacts.Contact;

import enumerators.CollisionSide;
import enumerators.Faction;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import model.events.CollisionEvent;
import model.levelsgenerator.LevelGenerationEntity;

public class MyContactListener implements ContactListener {



	@Override
	public void beginContact(final Contact contact) {
    	final Object objA = contact.getFixtureA().getBody().getUserData();
    	final Object objB = contact.getFixtureB().getBody().getUserData();
    	
    // 	if (entityList.contains(objA.getClass())) {
    		final Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
    		final Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
    		
    		System.out.println("collision");
            if(entityA.getType().equals(Faction.NEUTRAL_MORTAL)) {
        	    entityA.post(new CollisionEvent(entityA, entityB, checkCollisionSide(contact, entityA)));
            } else if(entityB.getType().equals(Faction.NEUTRAL_MORTAL)) {
                entityB.post(new CollisionEvent(entityB, entityA, checkCollisionSide(contact, entityB)));	
            }
  //  	}
	}

	@Override
	public void endContact(final Contact contact) {
		
	}

	@Override
    public final void postSolve(final Contact contact, final ContactImpulse arg1) {
		
    }

    @Override
    public final void preSolve(final Contact contact, final Manifold manfold) {
    	
    }



    private CollisionSide checkCollisionSide(final Contact contact, final Entity entity) {
        final WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        final boolean collidingBottom = isCollidingBottom(worldManifold.points[0].y, entity);
        if (collidingBottom) {
        	System.out.println("bottom");
            return CollisionSide.BOTTOM;
        } else {
        	System.out.println("other");
            return CollisionSide.OTHERS;
        }
    }

    private boolean isCollidingBottom(final float collisionY, final Entity entity) {
        return (collisionY <= entity.getBottomSide() && collisionY >= entity.getCenter().y);
    } 

}

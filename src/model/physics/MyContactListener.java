package model.physics;

import model.entities.Entity;
import java.util.HashMap;
import java.util.Map;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import enumerators.CollisionSide;
import controller.entities.Entity;
import enumerators.Faction;
import enumerators.Faction;
import model.GameModel;
import model.CollisionHandler.CollisionSide;
import model.components.CollisionImpl;
import model.entities.Entity;
import model.entities.PlayerModel;
import model.events.CollisionEvent;

public class MyContactListener implements ContactListener {

//    NEL GAMEMODEL HO BISOGNO DI UNA MAPPA <BODY, ENTITY> CON LA FUNZIONE
	
//	 @Override
//	    public Entity getEntityFromBody(final Body body) {
//	        if (!entitiesMap.isEmpty()) {
//	            if (entitiesMap.containsKey(body)) {
//	                return entitiesMap.get(body);
//	            }
//	            throw new IllegalArgumentException("This body doesn't exist inside the entity collection!");
//	        } else {
//	            throw new IllegalArgumentException("Entity collection is empty");
//	        }
//	    }


    private final GameModel gameModel;
    
	@Override
	public void beginContact(Contact contact) {

	}

	@Override
	public void endContact(Contact Ccontact) {

	}

	@Override
    public final void postSolve(final Contact contact, final ContactImpulse arg1) {
    }

    @Override
    public final void preSolve(final Contact contact, final Manifold manfold) {
        final Entity entityA = getEntity(contact.getFixtureA().getBody());
        final Entity entityB = getEntity(contact.getFixtureB().getBody());
        
        if(entityA.getType().equals(Faction.NEUTRAL_MORTAL)) {
        	entityA.post(new CollisionEvent(entityA, entityB, checkCollisionSide(contact, entityA)));
        } else if(entityB.getType().equals(Faction.NEUTRAL_MORTAL)) {
        	entityB.post(new CollisionEvent(entityB, entityA, checkCollisionSide(contact, entityB)));	
        }
    }



    private CollisionSide checkCollisionSide(final Contact contact, final Entity entity) {
        final WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        final boolean collidingBottom = isCollidingBottom(worldManifold.points[0].y, entity);
        if (collidingBottom) {
            return CollisionSide.BOTTOM;
        } else {
            return CollisionSide.OTHERS;
        }
    }

    private boolean isCollidingBottom(final float collisionY, final Entity entity) {
        return (collisionY <= entity.getBottomSide() && collisionY >= entity.getCenter().y);
    }

    private Entity getEntity(final Body body) {
        return gameModel.getEntityFromBody(body).getModel();
   }

    

}

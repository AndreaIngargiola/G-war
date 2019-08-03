package model.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.entities.Enemy;
import model.entities.Entity;
import model.events.Damage;
import model.events.PointsEvent;
import model.events.PunchEvent;

public class PunchImpl extends AbstractEntityComponent implements Punch {

	private final List<Entity> enemies = new ArrayList<>(); 
    private final float RANGE = 1;
    private final float rangeMin = (this.getEntity().getBody().getPosition().x) +  (this.getEntity().getBody().getDimension().x/2);
    private final float rangeMax = rangeMin + RANGE;
    final int playerAttack = this.getEntity().get(Attack.class).getDamage();
    final int MOLTIPLIER_FACTOR = 20;
    
	@Override
	public void setEnemy(final List<Entity> entities) {
		this.enemies = entities.stream()
                .filter(e -> e instanceof Enemy)
                .collect(Collectors.toList());
	}

	@Override
	public void findTarget() {
		for(final Entity e : enemies) {
			if(e.getBody().getPosition().y == (this.getEntity().getBody().getPosition().y)) {
				if(e.getBody().getPosition().x <= (rangeMax + (e.getBody().getDimension().x)/2) && e.getBody().getPosition().y >= rangeMin) {
					e.post(new Damage(e, playerAttack));
	                post(new PointsEvent(getEntity(), (e.get(Attack.class).getDamage())*MOLTIPLIER_FACTOR));
				}
			}	
		}
		
	}

	@Override
	public void punchListener(PunchEvent punch) {
		setEnemy(getEntities());			//mi restituisce tutte le entità
		findTarget();
		
	}

}

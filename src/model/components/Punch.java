package model.components;

import java.util.List;

import com.google.common.eventbus.Subscribe;

import model.entities.Entity;
import model.events.Damage;
import model.events.PunchEvent;

/**
 * Models.
 */
public interface Punch extends EntityComponent {

    void setEnemy(List<Entity> entities);
    
    void findTarget();
    
    @Subscribe
    void punchListener(PunchEvent punch);
    
    
}

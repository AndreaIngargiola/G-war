package model.components;

import com.google.common.eventbus.Subscribe;

import model.events.PunchEvent;

/**
 * Models a punch.
 */
public interface Punch extends EntityComponent {

    /**
     * 
     * @param punch 
     *           an event
     */
    @Subscribe
    void punchListener(PunchEvent punch);
}

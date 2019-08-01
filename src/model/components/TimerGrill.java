package model.components;

import com.google.common.eventbus.Subscribe;

import model.events.TimerEvent;

/**
 * Models a timer for {@link Grill}.
 *
 */
public interface TimerGrill extends EntityComponent {

    /**
     * 
     * @return isDangerous
     *                   true if the grill can hurt the player
     */
    boolean getIsDangerous();

    /**
     * 
     * @param timerEvent
     *                 an event
     */
    @Subscribe
    void timerListener(TimerEvent timerEvent);
}

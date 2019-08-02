package model.components;

import java.util.Timer;
import java.util.TimerTask;

import model.events.JumpEvent;

/**
 * Implementation class for the interface {@link TimerJump} .
 */
public class TimerJumpImpl extends AbstractEntityComponent implements TimerJump {

	private final int TIME = 3000;

	/**
	 * Every TIME time it calls the Jump event.
	 */
	public TimerJumpImpl() {
		super();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                post(new JumpEvent(getEntity()));
            }
	    };
	    timer.schedule(task, TIME, TIME);
    }

    @Override
    public final String toString() {
        return "TimerJump";
    }


}

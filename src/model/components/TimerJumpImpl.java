package model.components;

import model.events.JumpEvent;

/**
 * Implementation class for the interface {@link TimerJump} .
 */
public class TimerJumpImpl extends AbstractEntityComponent implements TimerJump {

	private int TIME = 3000;

	/**
	 * Every TIME time it calls the Jump event.
	 */
	public TimerJumpImpl() {
		super();

		while (true) {
		    try {
		    Thread.sleep(TIME);
		    } catch (InterruptedException e) {
		    e.printStackTrace();
		    }
		   this.getOwner().get().post(new JumpEvent(this.getOwner().get()));
		}
	}


    @Override
    public final String toString() {
        return "TimerJump";
    }


}

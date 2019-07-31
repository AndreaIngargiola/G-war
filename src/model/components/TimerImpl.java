package model.components;

/**
 * Implementation class for the interface {@link Timer} .
 */
public class TimerImpl extends AbstractEntityComponent implements Timer {

	private int TIME = 3000;
	private boolean isDangerous = false;

	/**
	 * Every TIME time chance the boolean isDangerour from true to false or viceversa.
	 */
	public TimerImpl() {
		super();

		while (true) {
		    try {
		    Thread.sleep(TIME);
		    } catch (InterruptedException e) {
		    e.printStackTrace();
		    }
		    System.out.println("hh");
		    if (this.isDangerous) {
			    this.isDangerous = false;
		    } else {
			    this.isDangerous = true;
		    }
		}
	}

	@Override
	public final boolean getIsDangerous() {
		return this.isDangerous;
	}

    @Override
    public final String toString() {
        return "Timer";
    }
}

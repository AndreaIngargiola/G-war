package model.components;

/**
 * Implementation class for the interface {@link TimerGrill} .
 */
public class TimerGrillImpl extends AbstractEntityComponent implements TimerGrill {

	private int TIME = 3000;
	private boolean isDangerous = false;

	/**
	 * Every TIME time chance the boolean isDangerour from true to false or vice versa.
	 */
	public TimerGrillImpl() {
		super();

		while (true) {
		    try {
		    Thread.sleep(TIME);
		    } catch (InterruptedException e) {
		    e.printStackTrace();
		    }
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
        return "TimerGrill";
    }
}

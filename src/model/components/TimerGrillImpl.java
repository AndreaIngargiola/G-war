package model.components;

import model.events.TimerEvent;

/**
 * Implementation class for the interface {@link TimerGrill} .
 */
public class TimerGrillImpl extends AbstractEntityComponent implements TimerGrill {

    private boolean isDangerous = false;

    /**
     * Every TIME time chance the boolean isDangerour from true to false or vice versa.
     */
    public TimerGrillImpl() {
        super();
    }

    @Override
    public final void timerListener(final TimerEvent event) {
        if (this.isDangerous) {
            this.isDangerous = false;
        } else {
            this.isDangerous = true;
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

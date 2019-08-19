package model.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import model.components.TimerGrill;
import model.entities.Entity;
import model.events.EntityEventSubscriber;


/**
 * A TimerTask that can receive an Death event.
 *
 */
public class TimerTaskGrill  extends  TimerTask implements EntityEventSubscriber {

    private final List<Entity> grills = new ArrayList<>();

    @Override
    public final void run() {

        if (!grills.isEmpty()) {
            grills.forEach(g -> g.get(TimerGrill.class).changeState());
        }
    }

    /**
     * 
     * @param grill
     *             a {@link Grill} entity
     */
    public final void add(final Entity grill) {
        grills.add(grill);
    }

    /**
     * 
     * @param grill
     *             a {@link Grill} entity
     */
    public final void remove(final Entity grill) {
        grills.remove(grill);
    }

}

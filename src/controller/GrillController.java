package controller;

import com.google.common.eventbus.Subscribe;

import model.entities.Entity;
import model.events.ChangeStateEvent;
import view.entities.GrillView;
/**
 * A controller for the grill entity.
 *
 */
public final  class GrillController extends ImmortalEntityController {

    /**
     * 
     * @param grill
     *             the model of the grill entity
     * @param grillView
     *             the view of grill entity
     */
    public GrillController(final Entity grill, final GrillView grillView) {
        super(grill, grillView);
    }

    /**
     * 
     * @param event
     *           a {@link ChangeStateEvent} 
     */
    @Subscribe
    public void changeStateListener(final ChangeStateEvent event) {
        getEntityView().changeState(event.getState());
    }
}

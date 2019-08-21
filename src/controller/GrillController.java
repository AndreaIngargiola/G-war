package controller;

import com.google.common.eventbus.Subscribe;

import model.engine.GameModelImpl;
//import test.Main;
import model.entities.Entity;
import model.events.ChangeStateEvent;
import model.events.Death;
import viewGame.GrillView;
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

    @Override
    public void deathListener(final Death event) {
        GameModelImpl.getTimer().remove(this.getEntityModel());
        super.deathListener(event);
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

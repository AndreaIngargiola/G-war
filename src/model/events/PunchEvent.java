package model.events;

import model.entities.Entity;

public class PunchEvent extends AbstractEntityEvent{

	public PunchEvent(Entity source) {
		super(source);
	}

}

package model.entities;

import enumerators.Faction;
import model.components.EntityBody;

public class Enemy extends AbstractEntity {

	public Enemy(Faction type, final EntityBody body) {
		super(type, body);
	}

}

package com.greenteam.spacefighters.entity.entityliving;

import com.greenteam.spacefighters.entity.Entity;

public class EntityLiving extends Entity {
	private int health;

	public EntityLiving() {
		super();
	}

	@Override
	public void update() {
	    if (health > 0) {
			super.update();
	    }
	    else {
	        this.remove();
	    }
	public void update(int ms) {
		super.update(ms);
		if (health <= 0) {
			if (stage != null) {
				stage.remove(this);
			}
		}
	}
	
}

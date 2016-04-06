package com.greenteam.spacefighters.entity.entityliving;

import com.greenteam.spacefighters.entity.Entity;

public class EntityLiving extends Entity {
	private int health;
	
	@Override
	public void update() {
	    if (health > 0) {
			super.update();
	    }
	    else {
	        this.remove();
	    }
	}
	
}

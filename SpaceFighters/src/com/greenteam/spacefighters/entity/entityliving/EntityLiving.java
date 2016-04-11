package com.greenteam.spacefighters.entity.entityliving;

import com.greenteam.spacefighters.entity.Entity;

public class EntityLiving extends Entity {
	private int health;

	public EntityLiving(int health) {
		super();
		this.health = health;
	}

	@Override
	public void update(int ms) {
		super.update(ms);
		if (health <= 0) {
			stage.remove(this);
		}
	}
	
}

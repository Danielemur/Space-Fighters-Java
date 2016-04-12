package com.greenteam.spacefighters.entity.entityliving;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.stage.Stage;

public class EntityLiving extends Entity {
	private int health;

	public EntityLiving(Stage s, int health) {
		super(s);
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

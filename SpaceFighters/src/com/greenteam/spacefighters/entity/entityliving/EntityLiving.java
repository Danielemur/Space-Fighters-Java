package com.greenteam.spacefighters.entity.entityliving;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.stage.Stage;

public class EntityLiving extends Entity {
	private int health;

	public EntityLiving(Stage s, int health) {
		super(s);
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	protected void setHealth(int health) {
		this.health = health;
	}
	
	public int getDamage() {
		return 5; //is only a default, override if you want something different
	}

	@Override
	public void update(int ms) {
		super.update(ms);
		if (health <= 0) {
			stage.remove(this);
		}
	}
	
}

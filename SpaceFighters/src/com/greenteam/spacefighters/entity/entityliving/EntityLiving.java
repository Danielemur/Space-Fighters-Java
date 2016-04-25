package com.greenteam.spacefighters.entity.entityliving;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.stage.Stage;

public abstract class EntityLiving extends Entity {
	private int health;
	private boolean dead;

	public EntityLiving(Stage s, int health) {
		super(s);
		this.health = health;
		dead = false;
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
	
	public boolean isDead() {
		return dead;
	}

	@Override
	public void update(int ms) {
		super.update(ms);
		if (health <= 0) {
			this.getStage().remove(this);
			dead = true;
		}
	}
	
}

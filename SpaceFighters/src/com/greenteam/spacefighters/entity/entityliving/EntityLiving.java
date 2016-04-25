package com.greenteam.spacefighters.entity.entityliving;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.stage.Stage;

public abstract class EntityLiving extends Entity {
	private int maxHealth;
	private int health;

	public EntityLiving(Stage s, int maxHealth, int health) {
		super(s);
		this.maxHealth = maxHealth;
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
	
	public void damage(int damage) {
		this.health -= damage;
		this.health = Math.min(maxHealth, health);
	}
	
	public void damage(int damage, boolean noCap) {
		this.health -= damage;
		if (!noCap)
			this.health = Math.min(maxHealth, health);
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void uponDeath() {}

	@Override
	public void update(int ms) {
		super.update(ms);
		if (this.isDead()) {
			this.uponDeath();
			this.getStage().remove(this);
		}
	}
	
}

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

	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean healthIsAugmented() {
		return this.getHealth() > this.getMaxHealth();
	}
	
	public int getDamage() {
		return 5; //is only a default, override if you want something different
	}
	
	public void damage(int damage) {
			this.health -= damage;
	}
	
	public int getMaxHealth() {
		return maxHealth;
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
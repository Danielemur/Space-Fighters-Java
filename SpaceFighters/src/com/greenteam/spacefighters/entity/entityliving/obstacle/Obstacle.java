package com.greenteam.spacefighters.entity.entityliving.obstacle;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Obstacle extends EntityLiving {

	public Obstacle(Stage s, int health) {
		super(s, health, health);
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) &&
				e instanceof EntityLiving &&
				!((EntityLiving)e).isDead()) {
				((EntityLiving)e).damage(this.getDamage());
			}
		}
	}

	@Override
	public Class<?> getSource() {
		return this.getClass();
	}
}

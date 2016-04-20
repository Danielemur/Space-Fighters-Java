package com.greenteam.spacefighters.entity.entityliving.obstacle;

import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Obstacle extends EntityLiving {

	public Obstacle(Stage s, int health) {
		super(s, health);
	}

	@Override
	public Class<?> getSource() {
		return this.getClass();
	}
}

package com.greenteam.spacefighters.entity.entityliving.powerup;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Powerup extends EntityLiving{
	private static final int DURATION = 20000;
	
	protected Player player;
	private int timeRemaining;

	public Powerup(Stage s, Player pl) {
		super(s, 1);
		this.player = pl;
		timeRemaining = DURATION;
		this.setAcceleration(Vec2.ZERO);
		this.setVelocity(Vec2.ZERO);
	}

	public void resetTime() {
		timeRemaining = DURATION;
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		timeRemaining -= ms;
		if (timeRemaining <= 0)
			this.remove();
	}

}

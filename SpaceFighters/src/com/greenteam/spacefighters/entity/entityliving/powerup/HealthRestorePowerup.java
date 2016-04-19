package com.greenteam.spacefighters.entity.entityliving.powerup;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class HealthRestorePowerup extends Powerup {
	private static final int SELECT_NEW_POSITION_INTERVAL = 1000;
	private static final double SPAWNDIST = 400.0D;
	private static final int SPEED = 100;
	private static final int TIME_ON_SCREEN = 15000;
	private static final int BEGIN_FADING_TIME = 500;

	private Vec2 randpos;
	private int time;

	public HealthRestorePowerup(Stage s) {
		super(s);
		time = SELECT_NEW_POSITION_INTERVAL;
		timeRemaining = TIME_ON_SCREEN;
		
		this.setPosition(this.randSpawnPos(s.getPlayer(), SPAWNDIST));
		this.setOrientation(new Vec2(0,-1));
		
		randpos = new Vec2(Stage.WIDTH * Math.random(), Stage.HEIGHT * Math.random());
		
		this.setTexture(Powerup.getTexFromEnum(PowerupColor.RED));
		if (this.getTexture() != null) {
			couldLoadImage = true;
		} else {
			couldLoadImage = false;
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		time -= ms;
		if (time <= 0) {
			time = SELECT_NEW_POSITION_INTERVAL;
			randpos = new Vec2(Stage.WIDTH * Math.random(), Stage.HEIGHT * Math.random());
		}
		this.setVelocity(randpos.subtract(this.getPosition()).normalize().scale(SPEED));
		if (this.getHealth() <= 0) {
			stage.setScore(stage.getScore() + this.getPointValue());
		}
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) &&
					(e instanceof Player)) {
				this.setHealth(this.getHealth() - ((EntityLiving)e).getDamage());
			}
		}
		timeRemaining -= ms;
		if (timeRemaining <= 0) this.remove();
	}
	
	@Override
	public double getRadius() {
		return 24;
	}
	
	@Override
	public int getDamage() {
		return -20;
	}
	
	@Override
	public int getBeginFadingTime() {
		return BEGIN_FADING_TIME;
	}

	@Override
	public java.awt.Color noTextureColor() {
		return java.awt.Color.RED;
	}
	
}

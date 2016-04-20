package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.stage.Stage;

public class TestEnemy extends Enemy {
	private static final double SPAWNDIST = 400.0D;
	
	public TestEnemy(Stage s) {
		super(s, 1, 0, 0);
		this.setPosition(randSpawnPos(s.getPlayer(), SPAWNDIST));
		this.setVelocity(new Vec2(-440,200));
		
		this.setTexture(Enemy.getTexFromEnum(EnemyShipColor.GREEN));		
		if (this.getTexture() != null) {
			couldLoadImage = true;
			this.width = this.getTexture().getWidth(null);
			this.height = this.getTexture().getHeight(null);
		} else {
			couldLoadImage = false;
			this.width = 20;
			this.height = 60;
		}
	}
	
	@Override
	public double getRadius() {
		return 32;
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		this.setOrientation(this.getVelocity().normalize().multiply(new Vec2(1, -1)));
		if ((this.getPosition().getX() + width * 2 > Stage.WIDTH) || (this.getPosition().getX() < 0)) {
			this.getVelocity().setX(this.getVelocity().getX()*-1);
		}
		if ((this.getPosition().getY() + height * 2 > Stage.HEIGHT) || (this.getPosition().getY() < 0)) {
			this.getVelocity().setY(this.getVelocity().getY() * -1);
		}
	}

	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}

	@Override
	public void fire(int type) {}
	
	@Override
	public int getPointValue() {
		return 25;
	}

	@Override
	public java.awt.Color noTextureColor() {
		return java.awt.Color.GREEN;
	}

}

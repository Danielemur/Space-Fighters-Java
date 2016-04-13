package com.greenteam.spacefighters.entity.entityliving.projectile;

import java.util.List;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.stage.Stage;

public class HomingProjectile extends Projectile {
	private static final int SPEED = 600;
	
	public HomingProjectile(Stage s, int health, int damage, Vec2 position, Vec2 velocity, Class<?> source) {
		super(s, health, damage, velocity, source);
		this.setPosition(position);
		List<Entity> entities = s.getEntities();
		double shortestDistance = entities.get(0).getPosition().distance(this.getPosition());
		for (Entity e : s.getEntities()) {
			if (isOppositeFaction(e)) {
				
			}
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		this.setVelocity(velocity);
	}
}

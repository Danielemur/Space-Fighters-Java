package com.greenteam.spacefighters.entity.entityliving.projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public class HomingProjectile extends Projectile {
	
	private double speed;
	private Entity target;
	
	public HomingProjectile(Stage s, int health, int damage, Vec2 position, Vec2 velocity, Class<?> source) {
		super(s, health, damage, position, velocity, source);
		target = closestEntity();
		speed = velocity.magnitude();
	}
	
	private Entity closestEntity() {
		double shortestDistance = 99999;
		Entity shortest = null;
		for (Entity e : stage.getEntities()) {
			if (isOppositeFaction(e)) {
				if (e.getPosition().distance(this.getPosition()) < shortestDistance) {
					shortest = e;
				}
			}
		}
		return shortest;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)(this.getPosition().getX()), (int)(this.getPosition().getY()), 5, 12);
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		if (target != null) {
			if (((EntityLiving)target).getHealth() <= 0) {
				target = closestEntity();
			}
		}
		if (target != null) {
			Vec2 vectorToTarget = target.getPosition().subtract(this.getPosition()).normalize().scale(speed);
			this.setVelocity(vectorToTarget);
		}
	}
}

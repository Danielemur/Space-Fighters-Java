package com.greenteam.spacefighters.entity.entityliving.projectile;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public class HomingProjectile extends Projectile {
	private static final int INIT_DELAY = 50;
	
	private double speed;
	private Entity target;
	private int startTrackDelay;
	
	public HomingProjectile(Stage s, int health, int damage, Vec2 position, Vec2 velocity, Class<?> source) {
		super(s, health, damage, position, velocity, source);
		speed = velocity.magnitude();
		target = null;
		startTrackDelay = INIT_DELAY;
		
		this.setTexture(Projectile.getTexFromEnum(ProjectileColor.BLUE));

	}
	
	private Entity closestEntity() {
		double shortestDistance = 99999;
		Entity shortest = null;
		for (Entity e : stage.getEntities()) {
			if (isOppositeFaction(e)) {
				if (e.getPosition().distance(this.getPosition()) < shortestDistance) {
					shortestDistance = e.getPosition().distance(this.getPosition());
					shortest = e;
				}
			}
		}
		return shortest;
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		startTrackDelay -= ms;
		if (startTrackDelay <= 0) {
			if (target != null) {
				if (((EntityLiving)target).getHealth() <= 0) {
					target = closestEntity();
				}
			}
			if (target != null) {
				Vec2 vectorToTarget = target.getPosition().subtract(this.getPosition()).normalize().scale(speed);
				this.setVelocity(vectorToTarget);
			}
			if (target == null) {
				target = closestEntity();
			}
			for (Entity e : this.getStage().getEntities()) {
				if (e == this) continue;
				if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) && isOppositeFaction(e)) {
					this.setHealth(this.getHealth() - ((EntityLiving)e).getDamage());
				}
			}
		}
	}
	
	public static int getEnergyCost() {
		return 25;
	}
}

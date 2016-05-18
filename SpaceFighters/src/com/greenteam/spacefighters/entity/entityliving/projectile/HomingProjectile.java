package com.greenteam.spacefighters.entity.entityliving.projectile;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.stage.Stage;

public class HomingProjectile extends Projectile {
	private static final int INIT_DELAY = 50;
	
	private double speed;
	private Entity target;
	private int startTrackDelay;
	
	public HomingProjectile(Stage s, int health, int damage, Vec2 position, Vec2 velocity, Entity source) {
		super(s, health, damage, position, velocity, source);
		speed = velocity.magnitude();
		target = null;
		startTrackDelay = INIT_DELAY;
		
		this.setTexture(Projectile.getTexFromEnum(ProjectileColor.BLUE));

	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		Stage s = this.getStage();
		startTrackDelay -= ms;
		if (startTrackDelay <= 0) {
			if (target != null) {
				if (((EntityLiving)target).getHealth() <= 0) {
					target = s.getNearestEntity(this, Enemy.class);
				}
			}
			if (target != null) {
				Vec2 vectorToTarget = target.getPosition().subtract(this.getPosition()).normalize().scale(speed);
				this.setVelocity(vectorToTarget);
			}
			if (target == null) {
				target = s.getNearestEntity(this, Enemy.class);
			}
		}
	}
	
	public static int getEnergyCost() {
		return 50;
	}
}

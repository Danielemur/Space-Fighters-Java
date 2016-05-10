package com.greenteam.spacefighters.entity.entityliving.obstacle;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Obstacle extends EntityLiving {

	public Obstacle(Stage s, int health) {
		super(s, health, health);
	}
	
	protected boolean isOppositeFaction(Entity e) {
		return (Enemy.class.isAssignableFrom(e.getSource()) ||
				Player.class.isAssignableFrom(e.getSource()) ||
				Projectile.class.isAssignableFrom(e.getSource()));
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if (this.overlaps(e) &&
				e instanceof EntityLiving &&
				this.isOppositeFaction(e)) {
				((EntityLiving)e).damage(this.getDamage());
			}
		}
	}
	
	@Override
	public int getDefaultLayer() {
		return -1;
	}

	@Override
	public Class<?> getSource() {
		return this.getClass();
	}
}

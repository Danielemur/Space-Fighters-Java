package com.greenteam.spacefighters.entity.entityliving.projectile;

import java.io.IOException;

import javax.imageio.ImageIO;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public class LinearProjectile extends Projectile {
	
	public LinearProjectile(Stage s, int health, int damage, Vec2 position, Vec2 velocity, Class<?> source) {
		super(s, health, damage, position, velocity, source);
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/projectile-1.png")));
		} catch (IOException e) {}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) && isOppositeFaction(e)) {
				this.setHealth(this.getHealth() - ((EntityLiving)e).getDamage());
			}
		}
	}

}

package com.greenteam.spacefighters.entity.entityliving.projectile;

import java.awt.Color;
import java.awt.Graphics;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class Projectile extends EntityLiving {
	private Class<?> source;
	
	public Projectile(Stage s, int health, Class<?> source) {
		super(s, health);
		this.source = source;
	}
	
	private boolean isOppositeFaction(Entity e) {
		if (this.getSource() == Enemy.class) {
			if (Player.class.isAssignableFrom(e.getSource())|| Obstacle.class.isAssignableFrom(e.getSource())) return true;
		}
		else if (this.getSource() == Player.class) {
			if (Enemy.class.isAssignableFrom(e.getSource()) || Obstacle.class.isAssignableFrom(e.getSource())) return true;
		}
		else if (this.getSource() == Obstacle.class) {
			if (Enemy.class.isAssignableFrom(e.getSource()) || Player.class.isAssignableFrom(e.getSource())) return true;
		}
		return false;
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
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)(this.getPosition().getX()), (int)(this.getPosition().getY()), 5, 12);
	}

	@Override
	public Class<?> getSource() {
		return source;
	}
}

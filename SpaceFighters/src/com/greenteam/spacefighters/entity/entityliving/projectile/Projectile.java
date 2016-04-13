package com.greenteam.spacefighters.entity.entityliving.projectile;

import java.awt.Color;
import java.awt.Graphics;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class Projectile extends EntityLiving {
	private Class<?> source;
	private int damage;
	
	public Projectile(Stage s, int health, int damage, Class<?> source) {
		super(s, health);
		this.damage = damage;
		this.source = source;
	}
	
	public Projectile(Stage s, int health, int damage, Vec2 velocity, Class<?> source) {
		super(s, health);
		this.damage = damage;
		this.source = source;
		this.setVelocity(velocity);
	}
	
	protected boolean isOppositeFaction(Entity e) {
		if (this.getSource() == Enemy.class) {
			if (!(e instanceof Projectile)) {
				if (Player.class.isAssignableFrom(e.getSource())|| Obstacle.class.isAssignableFrom(e.getSource())) return true;
			}
		}
		else if (this.getSource() == Player.class) {
			if (!(e instanceof Projectile)) {
				if (Enemy.class.isAssignableFrom(e.getSource()) || Obstacle.class.isAssignableFrom(e.getSource())) return true;
			}
		}
		else if (this.getSource() == Obstacle.class) {
			if (!(e instanceof Projectile)) {
				if (Enemy.class.isAssignableFrom(e.getSource()) || Player.class.isAssignableFrom(e.getSource())) return true;
			}
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
		if ((this.getPosition().getX() > stage.getWidth()) ||
				(this.getPosition().getX() < 0) ||
				(this.getPosition().getY() > stage.getHeight()) ||
				(this.getPosition().getY() < 0)) {
			stage.remove(this);
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)(this.getPosition().getX()), (int)(this.getPosition().getY()), 5, 12);
	}

	@Override
	public int getDamage() {
		return damage;
	}
	
	@Override
	public Class<?> getSource() {
		return source;
	}
}

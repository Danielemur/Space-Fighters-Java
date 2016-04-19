package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.entity.entityliving.starship.Starship;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Enemy extends Starship {

	public Enemy(Stage s, int health, int armorMultiplier, int weaponryMultiplier) {
		super(s, health, armorMultiplier, weaponryMultiplier);
	}

	@Override
	public void update(int ms) {
		super.update(ms);
		if (this.getHealth() <= 0) {
			stage.setScore(stage.getScore() + this.getPointValue());
		}
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) &&
					((Obstacle.class.isAssignableFrom(e.getSource())) || ((Player.class.isAssignableFrom(e.getSource()))))) {
				this.setHealth(this.getHealth() - ((EntityLiving)e).getDamage());
			}
		}
	}
	
	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}
	
	public static BufferedImage getTexFromEnum(EnemyShipColor color) {
		try {
			switch(color) {
				case GREEN:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-0.png"));
				case BLUE:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-1.png"));
				case RED:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-2.png"));
				case BLACK:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-3.png"));
				default :
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-0.png"));
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	public enum EnemyShipColor {
		GREEN, BLUE, RED, BLACK
	}
	
}

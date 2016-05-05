package com.greenteam.spacefighters.entity.entityliving.starship.player;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.entity.entityliving.powerup.ChainBeamPowerup;
import com.greenteam.spacefighters.entity.entityliving.powerup.Powerup;
import com.greenteam.spacefighters.entity.entityliving.powerupcontainer.ChargeBoostPowerupContainer;
import com.greenteam.spacefighters.entity.entityliving.powerupcontainer.PowerupContainer;
import com.greenteam.spacefighters.entity.entityliving.projectile.ExplosiveProjectile;
import com.greenteam.spacefighters.entity.entityliving.projectile.HomingProjectile;
import com.greenteam.spacefighters.entity.entityliving.projectile.LinearProjectile;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.starship.Starship;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.stage.Stage;

public class Player extends Starship {
	private static final int DEFAULTARMORLEVEL = 0;
	private static final int DEFAULTWEAPONRYLEVEL= 0;
	private static final int DEFAULTWEAPONRYHEALTH = 2;
	private static final int FIREDRAIN = 15;
	private static final int FULLCHARGE = 500;
	public static final int MOVEMENT_SPEED = 500;
	private static final int PLAYER_PROJECTILE_SPEED = 1200;
	private static final int MISSILE_SPEED = 1000;
	private static final int EXPLOSIVE_SPEED = 400;
	private static final int HEALTH_REGEN_TIME = 1600;
	private static final int GUN_TO_MISSILE_RATIO = 5;
	private static final int MISSILE_SPREAD_COUNT = 12;
	
	private int timetofiremissile;
	private int chargeLevel;
	private int width;
	private int height;
	private boolean couldLoadImage;
	private java.awt.Color noTexColor;
	private int maxhealth;
	private int time;
	private int score;
	private int money;
	private HashSet<Powerup> powerups;

	public Player(Stage s, int maxHealth, int health, PlayerShipColor color) {
		super(s, maxHealth, health, DEFAULTARMORLEVEL, DEFAULTWEAPONRYLEVEL);
		maxhealth = health;
		time = 0;
		timetofiremissile = GUN_TO_MISSILE_RATIO;
		money = 0;
		score = 0;
		this.setTexture(Player.getTexFromEnum(color));
		if (this.getTexture() != null) {
			this.width = this.getTexture().getWidth(null);
			this.height = this.getTexture().getHeight(null);
			couldLoadImage = true;
		} else {
			couldLoadImage = false;
			this.width = 20;
			this.height = 30;
			noTexColor = noTextureColor(color);
		}
		chargeLevel = FULLCHARGE;
		powerups = new HashSet<Powerup>();
	}

	@Override
	public void uponDeath() {
		//TODO: do something special like a game over
	}

	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		if (couldLoadImage) {
			double angle = this.getOrientation().angle();
			double imagemidx = this.getTexture().getWidth(null)/2;
			double imagemidy = this.getTexture().getHeight(null)/2;
			AffineTransform tf = AffineTransform.getRotateInstance(angle, imagemidx, imagemidy);
			AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()-imagemidx), (int)(pos.getY()-imagemidy), null);
		}
		else {
			g.setColor(noTexColor);
			g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		Stage stage = this.getStage();
		if (this.getHealth() <= 0) {
			stage.gameOver();
		}
		time += ms;
		
		if (time > HEALTH_REGEN_TIME) {
			if (this.getHealth() < this.getMaxHealth()) {
				this.setHealth(this.getHealth()+1);
			}
			time = 0;
		}
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if (this.overlaps(e) &&
				(Obstacle.class.isAssignableFrom(e.getSource()) ||
				Enemy.class.isAssignableFrom(e.getSource()) 	||
				PowerupContainer.class.isAssignableFrom(e.getSource())) &&
				e instanceof EntityLiving && !((EntityLiving)e).isDead()) {
				((EntityLiving)e).damage(this.getDamage());
					boolean augmentChargePowerup = e instanceof ChargeBoostPowerupContainer;
					if (augmentChargePowerup) {
						chargeLevel = Math.min(chargeLevel + Player.FULLCHARGE, 2 * Player.FULLCHARGE);

				}
			}
		}
		if (this.getPosition().getX() > Stage.WIDTH) {
			this.getPosition().setX(Stage.WIDTH);
		}
		if (this.getPosition().getX() < 0) {
			this.getPosition().setX(0);
		}
		if (this.getPosition().getY() > Stage.HEIGHT) {
			this.getPosition().setY(Stage.HEIGHT);
		}
		if (this.getPosition().getY() < 0) {
			this.getPosition().setY(0);
		}
		boolean hasAugmentedCharge = this.getCharge() > this.getMaxCharge();
		if (!hasAugmentedCharge) {
			chargeLevel += ms/5;
			if (chargeLevel > Player.FULLCHARGE) {
				chargeLevel = Player.FULLCHARGE;
			}
		}
	}

	@Override
	public Class<?> getSource() {
		return this.getClass();
	}
	
	@Override
	public double getRadius() {
		return 20;
	}

	@Override
	public void fire(int type) {
		Stage stage = this.getStage();
		int damage = 10 * (getWeaponryMultiplier() + 1);
		Vec2 playerVel = this.getVelocity();
		switch(type) {
			case 0 :
			{
				if (chargeLevel >= FIREDRAIN) {
					--timetofiremissile;
					Projectile proj = new LinearProjectile(stage, DEFAULTWEAPONRYHEALTH, damage, this.getPosition(), getOrientation().scale(PLAYER_PROJECTILE_SPEED).multiply(new Vec2(1, -1)).add(playerVel), this.getSource());
					stage.add(proj);
					if (timetofiremissile == 0) {
						proj = new HomingProjectile(stage, DEFAULTWEAPONRYHEALTH, damage, this.getPosition(), getOrientation().scale(MISSILE_SPEED).multiply(new Vec2(1, -1)).add(playerVel), this.getSource());
						stage.add(proj);
						timetofiremissile = GUN_TO_MISSILE_RATIO;
					}
					chargeLevel -= FIREDRAIN;
				}
			}
			break;
			case 1 :
			{
				if (chargeLevel >= HomingProjectile.getEnergyCost()*MISSILE_SPREAD_COUNT) {
					for (int i = 0; i < MISSILE_SPREAD_COUNT; ++i) {
						Projectile proj = new HomingProjectile(stage, DEFAULTWEAPONRYHEALTH, damage, this.getPosition(), getOrientation().scale(MISSILE_SPEED).multiply(new Vec2(1, -1)).rotate(new Vec2(0,0), (i-(double)MISSILE_SPREAD_COUNT/2)/MISSILE_SPREAD_COUNT*2*Math.PI).add(playerVel), this.getSource());
						stage.add(proj);
					}
					chargeLevel -= HomingProjectile.getEnergyCost()*MISSILE_SPREAD_COUNT;
				}
			}
			break;
			case 2 :
			{
				if (chargeLevel >= ExplosiveProjectile.getEnergyCost()) {
					Projectile proj = new ExplosiveProjectile(stage, DEFAULTWEAPONRYHEALTH, damage, this.getPosition(), getOrientation().scale(EXPLOSIVE_SPEED).multiply(new Vec2(1, -1)).add(playerVel), this.getSource());
					stage.add(proj);
					chargeLevel -= ExplosiveProjectile.getEnergyCost();
				}
			}
			break;
			case 3 :
			{
				if (chargeLevel >= ExplosiveProjectile.getEnergyCost()) {
					Projectile proj = new ExplosiveProjectile(stage, DEFAULTWEAPONRYHEALTH, damage, this.getPosition(), Vec2.ZERO, this.getSource());
					stage.add(proj);
					chargeLevel -= ExplosiveProjectile.getEnergyCost();
				}
			}
			break;
			case 4 :
			{
				if (hasPowerup(ChainBeamPowerup.class)) {
					((ChainBeamPowerup)getPowerup(ChainBeamPowerup.class)).fire();
				}
			}
			break;
			default :
				break;
		}
	}
	
	public void addPowerup(Powerup p) {
		for (Powerup powerup : powerups) {
			if (p.getClass().isInstance(powerup)) {
				powerup.resetTime();
				return;
			}
		}
		powerups.add(p);
		this.getStage().add(p);
	}
	
	public Powerup getPowerup(Class<?> cl) {
		for (Powerup p : powerups)
			if (cl.isInstance(p))
				return p;
		return null;
	}
	
	public void removePowerup(Powerup p) {
		powerups.remove(p);
		p.remove();
	}
	
	private boolean hasPowerup(Class<?> cl) {
		return getPowerup(cl) != null;
	}
	
	public void setMaxHealth(int max) {
		this.maxhealth = max;
	}
	
	public int getMaxHealth() {
		return maxhealth;
	}
	
	public int getCharge() {
		return chargeLevel;
	}
	
	public int getMaxCharge() {
		return FULLCHARGE;
	}
	
	@Override
	public int getDamage() {
		return 50;
	}
	
	public static BufferedImage getTexFromEnum(PlayerShipColor color) {
		try {
			switch(color) {
				case RED:
					return ImageIO.read(Player.class.getResource("/com/greenteam/spacefighters/assets/spaceship-0.png"));
				case BLUE:
					return ImageIO.read(Player.class.getResource("/com/greenteam/spacefighters/assets/spaceship-1.png"));
				case GREEN:
					return ImageIO.read(Player.class.getResource("/com/greenteam/spacefighters/assets/spaceship-2.png"));
				case YELLOW:
					return ImageIO.read(Player.class.getResource("/com/greenteam/spacefighters/assets/spaceship-3.png"));
				default :
					return ImageIO.read(Player.class.getResource("/com/greenteam/spacefighters/assets/spaceship-0.png"));
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	public static java.awt.Color noTextureColor(PlayerShipColor color) {
		try {
			switch(color) {
				case RED:
					return java.awt.Color.RED;
				case BLUE:
					return java.awt.Color.BLUE;
				case GREEN:
					return java.awt.Color.GREEN;
				case YELLOW:
					return java.awt.Color.YELLOW;
				default :
					return java.awt.Color.RED;
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	public enum PlayerShipColor {
		RED, BLUE, GREEN, YELLOW
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
}

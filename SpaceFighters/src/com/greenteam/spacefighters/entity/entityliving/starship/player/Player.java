package com.greenteam.spacefighters.entity.entityliving.starship.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.starship.Starship;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.stage.Stage;

public class Player extends Starship {
	private static final int DEFAULTARMORLEVEL = 0;
	private static final int DEFAULTWEAPONRYLEVEL= 0;
	private static final int DEFAULTWEAPONRYHEALTH = 1;
	private static final int FIREDRAIN = 20;
	private static final int FULLCHARGE = 100;
	public static final int MOVEMENT_SPEED = 300;
	private static final int PLAYER_PROJECTILE_SPEED = 600;
	private static int chargeLevel;
	private int graphicsWidth;
	private int graphicsHeight;
	private int width;
	private int height;
	private boolean couldLoadImage;

	public Player(Stage s, int health, int graphicsWidth, int graphicsHeight) {
		super(s, health, DEFAULTARMORLEVEL, DEFAULTWEAPONRYLEVEL);
		this.graphicsWidth = graphicsWidth;
		this.graphicsHeight = graphicsHeight;
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/spaceship-3.png")));
			this.width = this.getTexture().getWidth(null);
			this.height = this.getTexture().getHeight(null);
			couldLoadImage = true;
		} catch (IOException e) {
			couldLoadImage = false;
			this.width = 20;
			this.height = 30;
		}
	}

	@Override
	public void remove() {
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
			g.setColor(Color.WHITE);
			g.fillRect((int)(pos.getX()), (int)(pos.getY()), 3, 3);
		}
		else {
			g.setColor(Color.GREEN);
			g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) &&
					((Obstacle.class.isAssignableFrom(e.getSource())) || ((Enemy.class.isAssignableFrom(e.getSource()))))) {
				this.setHealth(this.getHealth() - ((EntityLiving)e).getDamage());
			}
		}
		if (this.getPosition().getX() > graphicsWidth) {
			this.getPosition().setX(graphicsWidth);
		}
		if (this.getPosition().getX() < 0) {
			this.getPosition().setX(0);
		}
		if (this.getPosition().getY() > graphicsHeight) {
			this.getPosition().setY(graphicsHeight);
		}
		if (this.getPosition().getY() < 0) {
			this.getPosition().setY(0);
		}
		chargeLevel += ms;
		if (chargeLevel > Player.FULLCHARGE) {
			chargeLevel = Player.FULLCHARGE;
		}
	}

	@Override
	public Class<?> getSource() {
		return this.getClass();
	}

	@Override
	public void fire() {
		if (chargeLevel > FIREDRAIN) {
			int damage = 10 * (getWeaponryMultiplier() + 1);
			Projectile proj = new Projectile(stage, DEFAULTWEAPONRYHEALTH, damage, this.getSource());
			proj.setVelocity(new Vec2(0, -PLAYER_PROJECTILE_SPEED));
			proj.setPosition(this.getPosition());
			stage.add(proj);
			chargeLevel -= FIREDRAIN;
		}
	}
}

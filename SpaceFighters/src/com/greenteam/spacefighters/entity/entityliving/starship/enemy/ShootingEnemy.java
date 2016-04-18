package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.projectile.LinearProjectile;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.stage.Stage;

public class ShootingEnemy extends Enemy {
	private static final int SHOOTING_INTERVAL = 800;
	private static final int PROJECTILE_SPEED = 550;
	private static final double SPAWNDIST = 400.0D;
	private static final double SPEED = 300.0D;
	
	private int width;
	private int height;
	private boolean couldLoadImage;
	private int time;
	private Vec2 randpos;
	
	public ShootingEnemy(Stage s) {
		super(s, 1, 0, 0);
		time = 0;
		this.setPosition(randSpawnPos(s.getPlayer(), SPAWNDIST));
		this.setOrientation(new Vec2(0,-1));
		
		randpos = randSpawnPos(s.getPlayer(), 0);
		
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/enemy-2.png")));
			couldLoadImage = true;
			this.width = this.getTexture().getWidth(null);
			this.height = this.getTexture().getHeight(null);
		} catch (IOException e) {
			couldLoadImage = false;
			this.width = 20;
			this.height = 60;
		}
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
			g.setColor(Color.BLUE);
			g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		}
	}
	
	@Override
	public double getRadius() {
		return 32;
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		time += ms;
		if (time > SHOOTING_INTERVAL) {
			time = 0;
			Vec2 vectorToTarget = stage.getPlayer().getPosition().subtract(this.getPosition()).normalize().scale(PROJECTILE_SPEED);
			Projectile proj = new LinearProjectile(stage, 1, 3, this.getPosition(), vectorToTarget, Enemy.class);
			stage.add(proj);
		}
		if (getPosition().distance(randpos) < 5)
			randpos = randSpawnPos(this.getStage().getPlayer(), 0);
		this.setVelocity(randpos.subtract(this.getPosition()).normalize().scale(SPEED));
		this.setOrientation(this.getVelocity().normalize().multiply(new Vec2(1, -1)));
	}

	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}

	@Override
	public void fire(int type) {}
	
	@Override
	public int getPointValue() {
		return 100;
	}
}

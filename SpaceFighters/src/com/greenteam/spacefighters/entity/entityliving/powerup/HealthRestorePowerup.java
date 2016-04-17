package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class HealthRestorePowerup extends Powerup {
	private static final int SELECT_NEW_POSITION_INTERVAL = 1000;
	
	private boolean couldLoadImage;
	private Vec2 randpos;
	private int time;
	private static final double SPAWNDIST = 400.0D;

	public HealthRestorePowerup(Stage s) {
		super(s);
		time = SELECT_NEW_POSITION_INTERVAL;
		
		this.setPosition(this.randSpawnPos(SPAWNDIST));
		this.setOrientation(new Vec2(0,-1));
		
		randpos = new Vec2(Stage.WIDTH * Math.random(), stage.HEIGHT * Math.random());
		
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/powerup-0.png")));
			couldLoadImage = true;
		} catch (IOException e) {
			couldLoadImage = false;
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
			g.setColor(Color.RED);
			g.fillRect((int)pos.getX(), (int)pos.getY(), 10, 10);
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		time -= ms;
		if (time <= 0) {
			time = SELECT_NEW_POSITION_INTERVAL;
			randpos = new Vec2(Stage.WIDTH * Math.random(), stage.HEIGHT * Math.random());
		}
		this.setVelocity(randpos.subtract(this.getPosition()));
		if (this.getHealth() <= 0) {
			stage.setScore(stage.getScore() + this.getPointValue());
		}
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) &&
					(e instanceof Player)) {
				this.setHealth(this.getHealth() - ((EntityLiving)e).getDamage());
			}
		}
	}
	
	@Override
	public double getRadius() {
		return 12;
	}
	
	@Override
	public int getDamage() {
		return -20;
	}
}

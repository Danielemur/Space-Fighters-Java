package com.greenteam.spacefighters.entity.entityliving.projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.stage.Stage;

public class ExplosiveProjectile extends Projectile {
	private final static double PROJECTILERADIUS = 5.0D;
	private final static double BLASTRADIUS = 200.0D;
	private final static double SPEED = 400.0D;
	private final static int DAMAGE = 15;
	private final static int COUNTDOWNTIME = 500;
	private final static int EXPLOSIONDURATION = 100;
	private double hitRadius;
	private int countdown;
	private boolean isExploding;
	private double speed;
	
	public ExplosiveProjectile(Stage s, int health, int damage, Vec2 position, Vec2 velocity, Class<?> source) {
		super(s, health, damage, position, velocity.normalize().scale(SPEED), source);
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/projectile-2.png")));
		} catch (IOException e) {}
		hitRadius = PROJECTILERADIUS;
		countdown = COUNTDOWNTIME;
		isExploding = false;
	}
	
	@Override
	protected boolean isOppositeFaction(Entity e) {
		if (isExploding)
			return true;
		else
			return super.isOppositeFaction(e);
	}
	
	@Override
	public double getRadius() {
		return hitRadius;
	}
	
	public static int getEnergyCost() {
		return 125;
	}
	
	@Override
	public int getDamage() {
		return DAMAGE;
	}
	
	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		double angle = this.getOrientation().multiply(new Vec2(1, -1)).angle();
		double scale = hitRadius / PROJECTILERADIUS;
		double imagemidx = scale * this.getTexture().getWidth(null)/2;
		double imagemidy = scale * this.getTexture().getHeight(null)/2;
		AffineTransform tf = AffineTransform.getRotateInstance(angle, imagemidx, imagemidy);
		tf.rotate(angle, imagemidx, imagemidy);
		AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_BILINEAR);
		float opacity = (float) Math.pow(1 / scale, 0.25);
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawImage(op.filter((BufferedImage)this.getTexture(), null),
				(int)(pos.getX()-imagemidx), (int)(pos.getY()-imagemidy),
				2 * (int)imagemidx, 2 * (int)imagemidy,
				null);
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		countdown -= ms;
		if (countdown <= 0) {
			if (!isExploding) {
				isExploding = true;
				setVelocity(new Vec2(0, 0));
				countdown += EXPLOSIONDURATION;
			} else {
				stage.remove(this);
			}
		}
		
		if (isExploding) {
			hitRadius = PROJECTILERADIUS + (BLASTRADIUS - PROJECTILERADIUS) * (1 - (countdown / (float)EXPLOSIONDURATION));
		}
	}
	
}

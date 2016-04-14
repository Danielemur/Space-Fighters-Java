package com.greenteam.spacefighters.entity.entityliving.projectile;

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
import com.greenteam.spacefighters.stage.Stage;

public class HomingProjectile extends Projectile {
	private static final double IMAGE_RATIO = 0.05;
	
	private double speed;
	private Entity target;
	private boolean couldLoadImage;
	
	public HomingProjectile(Stage s, int health, int damage, Vec2 position, Vec2 velocity, Class<?> source) {
		super(s, health, damage, position, velocity, source);
		target = closestEntity();
		speed = velocity.magnitude();
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/missile.png")));
			couldLoadImage = true;
		} catch (IOException e) {
			couldLoadImage = false;
		}
	}
	
	private Entity closestEntity() {
		double shortestDistance = 99999;
		Entity shortest = null;
		for (Entity e : stage.getEntities()) {
			if (isOppositeFaction(e)) {
				if (e.getPosition().distance(this.getPosition()) < shortestDistance) {
					shortestDistance = e.getPosition().distance(this.getPosition());
					shortest = e;
				}
			}
		}
		return shortest;
	}
	
	@Override
	public void render(Graphics g) {
		if (couldLoadImage) {
			Vec2 pos = this.getPosition();
			double angle = this.getVelocity().multiply(new Vec2(-1,1)).angle()+Math.PI/2;
			double imagemidx = this.getTexture().getWidth(null)/2;
			double imagemidy = this.getTexture().getHeight(null)/2;
			AffineTransform tf = new AffineTransform();
			//AffineTransform scale = AffineTransform.getScaleInstance(.1, .1);
			tf.scale(IMAGE_RATIO, IMAGE_RATIO);
			tf.rotate(angle, imagemidx, imagemidy);
			AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()-imagemidx*IMAGE_RATIO), (int)(pos.getY()-imagemidy*IMAGE_RATIO), null);
		}
		else {
			g.setColor(Color.GREEN);
			g.fillRect((int)(this.getPosition().getX()), (int)(this.getPosition().getY()), 5, 12);
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		if (target != null) {
			if (((EntityLiving)target).getHealth() <= 0) {
				target = closestEntity();
			}
		}
		if (target != null) {
			Vec2 vectorToTarget = target.getPosition().subtract(this.getPosition()).normalize().scale(speed);
			this.setVelocity(vectorToTarget);
		}
	}
}

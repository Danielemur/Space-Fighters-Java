package com.greenteam.spacefighters.entity.entityliving;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.stage.Stage;

public class Explosion extends EntityLiving {
	private int timeRemaining;
	private int maxTime;
	
	public Explosion(Stage s, Vec2 position, int timeRemaining) {
		super(s, 1, 1);
		try {
			this.setTexture(ImageIO.read(Projectile.class.getResource("/com/greenteam/spacefighters/assets/explosion-0.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setPosition(position);
		this.maxTime = timeRemaining;
		this.timeRemaining = timeRemaining;
	}

	@Override
	public void update(int ms) {
		super.update(ms);
		timeRemaining -= ms;
		if (timeRemaining <= 0) {
			this.remove();
		}
	}
	
	@Override
	public void render(Graphics g) {
		float opacity = (timeRemaining < 0 ? 0 : timeRemaining) / (float)maxTime;
		opacity = (float)Math.pow(opacity, 0.25);
		Composite oldComposite = ((Graphics2D)g).getComposite();
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		
		//Vec2 pos = this.getPosition();
		//double angle = this.getVelocity().normalize().multiply(new Vec2(1, -1)).angle();
		//double imagemidx = this.getTexture().getWidth(null)/2;
		//double imagemidy = this.getTexture().getHeight(null)/2;
		//AffineTransform tf = AffineTransform.getRotateInstance(angle, imagemidx, imagemidy);
		//tf.rotate(angle, imagemidx, imagemidy);
		//AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_BILINEAR);
		//g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()-imagemidx), (int)(pos.getY()-imagemidy), null);
		g.drawImage(this.getTexture(), (int)(this.getPosition().getX()-this.getTexture().getWidth(null)/2), (int)(this.getPosition().getY()-this.getTexture().getHeight(null)/2), null);
		((Graphics2D)g).setComposite(oldComposite);
	}
	
	@Override
	public int getDamage() {
		return 0;
	}
	
	@Override
	public double getRadius() {
		return -Double.MAX_VALUE;
	}

	@Override
	public Class<?> getSource() {
		return Object.class;
	}
}

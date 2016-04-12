package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public class ErraticEnemy extends Enemy {
	private int width;
	private int height;
	private boolean couldLoadImage;
	private int time;
	
	public ErraticEnemy(Stage s, int width, int height) {
		super(s, 1, 0, 0);
		time = 0;
		this.setPosition(new Vec2((stage.getWidth()-40)*Math.random(),0));
		this.setVelocity(new Vec2(1000*Math.random()-500,200));
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/spaceship-2.png")));
			this.width = this.getTexture().getWidth(null);
			this.height = this.getTexture().getHeight(null);
			couldLoadImage = true;
		} catch (IOException e) {
			couldLoadImage = false;
		}
		this.width = width;
		this.height = height;
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
			g.setColor(Color.BLACK);
			g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		}
	}
	
	@Override
	public double getRadius() {
		return 25;
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		time += ms;
		//this.setOrientation(this.getOrientation().rotate(new Vec2(0,0), null, 0.1));
		this.setOrientation(new Vec2(0,1).rotate(new Vec2(0,0), null, this.getVelocity().angle()+Math.PI));
		if ((this.getPosition().getX() + width*2 > stage.getWidth()) || (this.getPosition().getX() < 0)) {
			this.getVelocity().setX(this.getVelocity().getX()*-1);
		}
		else {
			if (time > 400*Math.random()+300) {
				this.getVelocity().setX(1000*Math.random() - 500);
				time = 0;
			}
		}
		if (this.getPosition().getY() - 1 > stage.getHeight()) {
			this.remove();
		}
	}

	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}

	@Override
	public void fire() {}
}

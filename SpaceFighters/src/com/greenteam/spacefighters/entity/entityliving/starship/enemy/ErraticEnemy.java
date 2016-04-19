package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.stage.Stage;

public class ErraticEnemy extends Enemy {
	private int width;
	private int height;
	private boolean couldLoadImage;
	private int time;
	private static final double SPAWNDIST = 400.0D;
	
	public ErraticEnemy(Stage s) {
		super(s, 1, 0, 0);
		time = 0;
		this.setPosition(randSpawnPos(s.getPlayer(), SPAWNDIST));
		this.setVelocity(new Vec2(1000*Math.random()-500,200));
		
		this.setTexture(this.getTexFromEnum(EnemyShipColor.BLUE));		
		if (this.getTexture() != null) {
			couldLoadImage = true;
			this.width = this.getTexture().getWidth(null);
			this.height = this.getTexture().getHeight(null);
		} else {
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
			g.setColor(Color.WHITE);
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
		//this.setOrientation(this.getOrientation().rotate(new Vec2(0,0), null, 0.1));
		this.setOrientation(new Vec2(0,1).rotate(new Vec2(0,0), this.getVelocity().angle()+Math.PI));
		if ((this.getPosition().getX() + width * 2 > Stage.WIDTH) || (this.getPosition().getX() < 0)) {
			this.getVelocity().setX(this.getVelocity().getX()*-1);
		}
		if ((this.getPosition().getY() + height * 2 > Stage.HEIGHT) || (this.getPosition().getY() < 0)) {
			this.getVelocity().setY(this.getVelocity().getY() * -1);
		}
		else {
			if (time > 400 * Math.random() + 300) {
				this.setVelocity(new Vec2(1000 * Math.random() - 500,
										  1000 * Math.random() - 500));
				time = 0;
			}
		}
	}

	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}

	@Override
	public void fire(int type) {}
	
	@Override
	public int getPointValue() {
		return 50;
	}
}

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

public class TestEnemy extends Enemy {
	private int graphicsWidth;
	private int graphicsHeight;
	private int width;
	private int height;
	private boolean couldLoadImage;
	
	public TestEnemy(Stage s, int graphicsWidth, int graphicsHeight, int width, int height) {
		super(s, 1);
		this.setPosition(new Vec2(0,0));
		this.setVelocity(new Vec2(-440,200));
		try {
			this.setTexture(ImageIO.read(TestEnemy.class.getResource("../../../../assets/spaceship-1.png")));
			couldLoadImage = true;
		} catch (IOException e) {
			couldLoadImage = false;
		}
		this.graphicsWidth = graphicsWidth;
		this.graphicsHeight = graphicsHeight;
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
			g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()), (int)(pos.getY()), this.getTexture().getWidth(null), this.getTexture().getHeight(null), null);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		this.setOrientation(this.getOrientation().rotate(new Vec2(0,0), null, 0.1));
		if ((this.getPosition().getX() + width > graphicsWidth) || (this.getPosition().getX() < 0)) {
			this.getVelocity().setX(this.getVelocity().getX()*-1);
		}
		if (this.getPosition().getY() + height - 1 > graphicsHeight) {
			this.remove();
		}
	}
}

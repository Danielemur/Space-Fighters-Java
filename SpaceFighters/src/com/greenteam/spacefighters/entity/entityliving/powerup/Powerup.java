package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Powerup extends EntityLiving {
	protected boolean couldLoadImage;
	protected int timeRemaining;
	
	public Powerup(Stage s) {
		super(s, 1);
	}
	
	public abstract java.awt.Color noTextureColor();
	public abstract int getBeginFadingTime();
	
	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		if (couldLoadImage) {
			double angle = this.getOrientation().angle();
			double imagemidx = this.getTexture().getWidth(null)/2;
			double imagemidy = this.getTexture().getHeight(null)/2;
			AffineTransform tf = AffineTransform.getRotateInstance(angle, imagemidx, imagemidy);
			AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_BILINEAR);
			if ((timeRemaining <= getBeginFadingTime()) && (timeRemaining >= 0)) {
				float opacity = timeRemaining/(float)getBeginFadingTime();
				opacity = (float)Math.pow(opacity, 0.25);
				Composite oldComposite = ((Graphics2D)g).getComposite();
				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
				g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()-imagemidx), (int)(pos.getY()-imagemidy), null);
				((Graphics2D)g).setComposite(oldComposite);
			}
			else {
				g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()-imagemidx), (int)(pos.getY()-imagemidy), null);
			}
		} else {
			g.setColor(Color.RED);
			g.fillRect((int)pos.getX(), (int)pos.getY(), 10, 10);
		}
	}
	
	@Override
	public Class<?> getSource() {
		return Powerup.class;
	}
	
	public static BufferedImage getTexFromEnum(PowerupColor color) {
		try {
			switch(color) {
				case BLUE:
					return ImageIO.read(Powerup.class.getResource("/com/greenteam/spacefighters/assets/powerup-0.png"));
				case PURPLE:
					return ImageIO.read(Powerup.class.getResource("/com/greenteam/spacefighters/assets/powerup-1.png"));
				case ORANGE:
					return ImageIO.read(Powerup.class.getResource("/com/greenteam/spacefighters/assets/powerup-2.png"));
				case RED:
					return ImageIO.read(Powerup.class.getResource("/com/greenteam/spacefighters/assets/powerup-3.png"));
				case GREEN:
					return ImageIO.read(Powerup.class.getResource("/com/greenteam/spacefighters/assets/powerup-4.png"));
				case YELLOW:
					return ImageIO.read(Powerup.class.getResource("/com/greenteam/spacefighters/assets/powerup-5.png"));
				default :
					return ImageIO.read(Powerup.class.getResource("/com/greenteam/spacefighters/assets/powerup-0.png"));
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	public enum PowerupColor {
		BLUE, PURPLE, ORANGE, RED, GREEN, YELLOW
	}

}

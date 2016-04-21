package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;

import com.greenteam.spacefighters.common.Color;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class ForceFieldPowerup extends Powerup {

	public ForceFieldPowerup(Stage s) {
		super(s);
		this.setTexture(Powerup.getTexFromEnum(PowerupColor.BLUE));
		if (this.getTexture() != null) {
			couldLoadImage = true;
		} else {
			couldLoadImage = false;
		}
	}
	
	@Override
	public int getDamage() {
		return 0;
	}
	
	@Override
	public void update(int ms) {
		Player pl = this.getStage().getPlayer();
		if ((pl.getPosition().distance(this.getPosition()) < this.getRadius() + pl.getRadius())) {
			this.getStage().add(new ForceField(this.getStage(), this.getStage().getPlayer()));
		}
		super.update(ms);
	}

	@Override
	public java.awt.Color noTextureColor() {
		return java.awt.Color.BLUE;
	}
	
	private class ForceField extends EntityLiving {
		private static final double RADIUS = 50;
		private final Color COLOR = new Color(210, 210, 10);
		private Entity entity;
		
		public ForceField(Stage s, Entity e) {
			super(s, 1);
			this.entity = e;
		}

		@Override
		public void render(Graphics g) {
			Vec2 pos = this.getPosition();
			Color centerColor = new Color(COLOR);
			centerColor.setAlpha(0.1f);
			Color edgeColor = new Color(COLOR);
			edgeColor.setAlpha(0.7f);
			float[] fractions = new float[]{0.5f, 1.0f};
			java.awt.Color[] colors = new java.awt.Color[]{centerColor.toAWTColor(), edgeColor.toAWTColor()};
			Graphics2D g2 = (Graphics2D) g;
			//RadialGradientPaint grad = new RadialGradientPaint((float)pos.getX(), (float)pos.getY(), (float)RADIUS, fractions, colors);
			g.setColor(centerColor.toAWTColor());//Paint(grad);
			g.fillOval((int)(pos.getX() - RADIUS), (int)(pos.getY() - RADIUS), (int)(2 * RADIUS), (int)(2 * RADIUS));
		}
		
		@Override
		public double getRadius() {
			return RADIUS;
		}
		
		@Override
		public void update(int ms) {
			this.setPosition(entity.getPosition());
		}

		@Override
		public Class<?> getSource() {
			return Player.class;
		}
		
	}

}

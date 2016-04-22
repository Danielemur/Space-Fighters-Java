package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;

import com.greenteam.spacefighters.common.Color;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class ForceFieldPowerup extends Powerup {
	private static final double RADIUS = 50;
	private final Color COLOR = new Color(49, 11, 210);
	
	public ForceFieldPowerup(Stage s, Player pl) {
		super(s, pl);
	}

	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		Color centerColor = new Color(COLOR);
		centerColor.setAlpha(0.2f);
		Color edgeColor = new Color(COLOR);
		edgeColor.setAlpha(0.4f);
		float[] fractions = new float[]{0.5f, 1.0f};
		java.awt.Color[] colors = new java.awt.Color[]{centerColor.toAWTColor(), edgeColor.toAWTColor()};
		Graphics2D g2 = (Graphics2D) g;
		RadialGradientPaint grad = new RadialGradientPaint((float)pos.getX(), (float)pos.getY(), (float)RADIUS, fractions, colors);
		g2.setPaint(grad);
		g2.fillOval((int)(pos.getX() - RADIUS), (int)(pos.getY() - RADIUS), (int)(2 * RADIUS), (int)(2 * RADIUS));
	}
	
	@Override
	public double getRadius() {
		return RADIUS;
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		this.setPosition(player.getPosition());
	}

	@Override
	public Class<?> getSource() {
		return Player.class;
	}
}

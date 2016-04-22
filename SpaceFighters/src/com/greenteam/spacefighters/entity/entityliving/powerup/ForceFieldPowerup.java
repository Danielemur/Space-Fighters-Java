package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import com.greenteam.spacefighters.common.Color;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class ForceFieldPowerup extends Powerup {
	private static final double RADIUS = 50;
	private static final int BEGIN_FADING_TIME = 2000;
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
		
		if ((timeRemaining <= BEGIN_FADING_TIME) && (timeRemaining >= 0)) {
			float opacity = (float) ((Math.sin(4.5f * Math.PI * timeRemaining / BEGIN_FADING_TIME) + 1) / 2.0f);
			Composite oldComposite = ((Graphics2D)g).getComposite();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			g2.fillOval((int)(pos.getX() - RADIUS), (int)(pos.getY() - RADIUS), (int)(2 * RADIUS), (int)(2 * RADIUS));
			g2.setComposite(oldComposite);
		}
		else {
			g2.fillOval((int)(pos.getX() - RADIUS), (int)(pos.getY() - RADIUS), (int)(2 * RADIUS), (int)(2 * RADIUS));	
		}
		
		
		
	}
	
	@Override
	public double getRadius() {
		return 1.5 * RADIUS;
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

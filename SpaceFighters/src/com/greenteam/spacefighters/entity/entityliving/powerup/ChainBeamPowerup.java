package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.util.ArrayList;

import com.greenteam.spacefighters.common.Color;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class ChainBeamPowerup extends Powerup {
	private static final int DURATION = Integer.MAX_VALUE;
	private static final double TRAVELDISTANCE= 1000;
	private static final int SCANINTERVAL = 250;
	private ArrayList<Enemy> targets;
	private double remainingDistance;
	private int elapsedTime;
	
	public ChainBeamPowerup(Stage s, Player pl) {
		super(s, pl);
		targets = new ArrayList<Enemy>();
		remainingDistance = TRAVELDISTANCE;
		elapsedTime = SCANINTERVAL;
	}

	@Override
	public void render(Graphics g) {
		//TODO:this
//		Vec2 pos = this.getPosition();
//		Color centerColor = new Color(COLOR);
//		centerColor.setAlpha(0.2f);
//		Color edgeColor = new Color(COLOR);
//		edgeColor.setAlpha(0.4f);
//		float[] fractions = new float[]{0.5f, 1.0f};
//		java.awt.Color[] colors = new java.awt.Color[]{centerColor.toAWTColor(), edgeColor.toAWTColor()};
//		Graphics2D g2 = (Graphics2D) g;
//		RadialGradientPaint grad = new RadialGradientPaint((float)pos.getX(), (float)pos.getY(), (float)RADIUS, fractions, colors);
//		g2.setPaint(grad);
//		
//		if ((timeRemaining <= BEGIN_FADING_TIME) && (timeRemaining >= 0)) {
//			float opacity = (float)((Math.exp(-timeRemaining/BEGIN_FADING_TIME*5)*(Math.sin(4.5f * Math.PI * timeRemaining / BEGIN_FADING_TIME) + 3) / 2.0f)/2);
//			Composite oldComposite = ((Graphics2D)g).getComposite();
//			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
//			g2.fillOval((int)(pos.getX() - RADIUS), (int)(pos.getY() - RADIUS), (int)(2 * RADIUS), (int)(2 * RADIUS));
//			g2.setComposite(oldComposite);
//		}
//		else {
//			g2.fillOval((int)(pos.getX() - RADIUS), (int)(pos.getY() - RADIUS), (int)(2 * RADIUS), (int)(2 * RADIUS));	
//		}

	}
	
	@Override
	public double getRadius() {
		return Double.NEGATIVE_INFINITY;
	}
	
	@Override
	public int getDamage() {
		return 50;
	}
	
	protected boolean isOppositeFaction(Entity e) {
		return (Enemy.class.isAssignableFrom(e.getSource()));
	}

	protected int getDuration() {
		return DURATION;
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		this.setPosition(player.getPosition());
		elapsedTime += ms;
		
		if (elapsedTime >= SCANINTERVAL) {
			elapsedTime = 0;
			Stage s = this.getStage();
			Enemy target = (Enemy)s.getNearestEntity(targets.get(targets.size()), Enemy.class);
			double dist = this.getPosition().distance(target.getPosition()); 
			if (dist <= remainingDistance) {
				remainingDistance -= dist;
				target.setUpdatable(false);
				targets.add(target);
			} else {
				for (Enemy e : targets) {
					e.damage(this.getDamage());
					e.setUpdatable(true);
				}
				player.removePowerup(this);
			}
		}
	}

}

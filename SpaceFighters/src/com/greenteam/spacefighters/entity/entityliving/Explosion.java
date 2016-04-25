package com.greenteam.spacefighters.entity.entityliving;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;

public class TestEnemy extends Enemy {
	private int graphicsWidth;
	private int graphicsHeight;
	private int width;
	private int height;
	
	public TestEnemy(int graphicsWidth, int graphicsHeight, int width, int height) {
		super();
		this.setPosition(new Vec2(0,0));
		//this.setAcceleration(new Vec2(400,100));
		this.setVelocity(new Vec2(-440,200));
		this.graphicsWidth = graphicsWidth;
		this.graphicsHeight = graphicsHeight;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		g.setColor(Color.BLACK);
		g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		if ((this.getPosition().getX() + width > graphicsWidth) || (this.getPosition().getX() < 0)) {
			this.getVelocity().setX(this.getVelocity().getX()*-1);
		}
		/*
		if ((this.getPosition().getY() + height  - 1 > graphicsHeight) || (this.getPosition().getY() < 0)) {
			this.getVelocity().setY(this.getVelocity().getY()*-1);
		}
		*/
		if (this.getPosition().getY() + height - 1 > graphicsHeight) {
			this.remove();
		}
	}
}

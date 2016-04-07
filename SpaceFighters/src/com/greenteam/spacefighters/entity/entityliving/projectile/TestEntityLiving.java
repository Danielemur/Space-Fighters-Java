package com.greenteam.spacefighters.entity.entityliving.projectile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;

public class TestEntityLiving extends EntityLiving {
	
	public TestEntityLiving() {
		super();
		this.setPosition(new Vec2(100,400));
		this.setAcceleration(new Vec2(40,10));
		this.setVelocity(new Vec2(-80,-60));
	}
	
	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		g.fillRect((int)pos.getX(), (int)pos.getY(), 20, 50);
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
	}
}

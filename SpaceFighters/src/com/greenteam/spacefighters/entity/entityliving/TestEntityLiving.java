package com.greenteam.spacefighters.entity.entityliving;

import java.awt.Color;
import java.awt.Graphics;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.stage.Stage;

public class TestEntityLiving extends EntityLiving {
	private int graphicsWidth;
	private int graphicsHeight;
	private int width;
	private int height;
	
	public TestEntityLiving(Stage s, int graphicsWidth, int graphicsHeight, int width, int height) {
		super(s, 1);
		this.setPosition(new Vec2(100,100));
		this.setAcceleration(new Vec2(400,100));
		this.setVelocity(new Vec2(-160,-120));
		this.graphicsWidth = graphicsWidth;
		this.graphicsHeight = graphicsHeight;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		g.setColor(Color.MAGENTA);
		g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		if ((this.getPosition().getX() + width - 1 > graphicsWidth) || (this.getPosition().getX() < 0)) {
			this.getVelocity().setX(this.getVelocity().getX()*-1);
		}
		if ((this.getPosition().getY() + height - 1 > graphicsHeight) || (this.getPosition().getY() < 0)) {
			this.getVelocity().setY(this.getVelocity().getY()*-1);
		}
	}

	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}
}

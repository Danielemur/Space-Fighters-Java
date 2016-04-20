package com.greenteam.spacefighters.entity.entityliving.obstacle.asteroid;

import java.awt.Graphics;

import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.stage.Stage;

public class Asteroid extends Obstacle {
	
	public Asteroid(Stage s, int size) {
		super(s, (int)Math.ceil(size * Math.random()));
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getSource() {
		return Obstacle.class;
	}
	
}

package com.greenteam.spacefighters.entity.entityliving.asteroid;

import java.awt.Graphics;

import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public class Asteroid extends EntityLiving {
	
	public Asteroid(Stage s, int size) {
		super(s, (int)Math.ceil(size * Math.random()));
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
}

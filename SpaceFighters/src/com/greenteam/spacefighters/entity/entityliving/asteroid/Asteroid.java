package com.greenteam.spacefighters.entity.entityliving.asteroid;

import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public class Asteroid extends EntityLiving {
	
	public Asteroid(Stage s, int size) {
		super(s, (int)Math.ceil(size * Math.random()));
		
	}
	
}

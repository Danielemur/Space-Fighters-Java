package com.greenteam.spacefighters.entity.entityliving.asteroid;

import com.greenteam.spacefighters.entity.entityliving.EntityLiving;

public class Asteroid extends EntityLiving {
	
	public Asteroid(int size) {
		super((int)Math.ceil(size * Math.random()));
		
	}
	
}

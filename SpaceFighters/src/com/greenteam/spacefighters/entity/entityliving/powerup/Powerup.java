package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.Graphics;

import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Powerup extends EntityLiving {

	public Powerup(Stage s) {
		super(s, 1);
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(int ms) {
		super.update(ms);
		//TODO make it do something
	}
	
	@Override
	public Class<?> getSource() {
		return Powerup.class;
	}

}

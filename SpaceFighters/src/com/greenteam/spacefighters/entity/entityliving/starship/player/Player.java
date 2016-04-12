package com.greenteam.spacefighters.entity.entityliving.starship.player;

import java.awt.Graphics;

import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.starship.Starship;
import com.greenteam.spacefighters.stage.Stage;

public class Player extends Starship {
	private static final int DEFAULTARMORLEVEL = 0;
	private static final int DEFAULTWEAPONRYLEVEL= 0;
	private static final int DEFAULTWEAPONRYHEALTH = 1;
	private static final int FIREDRAIN = 20;
	private static final int FULLCHARGE = 100;
	private static int chargeLevel;
	
	public Player(Stage s, int health) {
		super(s, health, DEFAULTARMORLEVEL, DEFAULTWEAPONRYLEVEL);
	}

	@Override
	public void remove() {
		//TODO: do something special like a game over
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getSource() {
		return this.getClass();
	}
	
	@Override
	public void fire() {
	    if (chargeLevel > FIREDRAIN) {
	        int damage = 10 * (getWeaponryMultiplier() + 1);
	        stage.add(new Projectile(stage, DEFAULTWEAPONRYHEALTH, damage, getSource()));
	        chargeLevel -= FIREDRAIN;
	    }
	}

}

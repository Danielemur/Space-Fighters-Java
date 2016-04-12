package com.greenteam.spacefighters.entity.entityliving.starship;

import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Starship extends EntityLiving {
	private int armorMultiplier;
	private int weaponryMultiplier;

	public Starship(Stage s, int health, int armorMultiplier, int weaponryMultiply) {
		super(s, health);
	}	
	
	public abstract void fire();

	public int getArmorMultiplier() {
		return armorMultiplier;
	}
	
	public void setArmorMultiplier(int mult) {
		armorMultiplier = mult;
	}
	
	public int getWeaponryMultiplier() {
		return weaponryMultiplier;
	}
	
	public void setWeaponryMultiplier(int mult) {
		weaponryMultiplier = mult;
	}
	
}

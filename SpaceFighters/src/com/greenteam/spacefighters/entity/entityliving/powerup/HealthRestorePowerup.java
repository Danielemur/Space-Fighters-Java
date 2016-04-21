package com.greenteam.spacefighters.entity.entityliving.powerup;

import com.greenteam.spacefighters.stage.Stage;

public class HealthRestorePowerup extends Powerup {

	public HealthRestorePowerup(Stage s) {
		super(s);
		this.setTexture(Powerup.getTexFromEnum(PowerupColor.RED));
		if (this.getTexture() != null) {
			couldLoadImage = true;
		} else {
			couldLoadImage = false;
		}
	}
	
	@Override
	public int getDamage() {
		return -20;
	}

	@Override
	public java.awt.Color noTextureColor() {
		return java.awt.Color.RED;
	}
	
}

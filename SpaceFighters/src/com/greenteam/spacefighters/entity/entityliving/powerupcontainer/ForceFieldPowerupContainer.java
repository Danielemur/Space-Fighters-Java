package com.greenteam.spacefighters.entity.entityliving.powerupcontainer;

import com.greenteam.spacefighters.entity.entityliving.powerup.ForceFieldPowerup;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class ForceFieldPowerupContainer extends PowerupContainer {

	public ForceFieldPowerupContainer(Stage s) {
		super(s);
		this.setTexture(PowerupContainer.getTexFromEnum(PowerupColor.BLUE));
		if (this.getTexture() != null) {
			couldLoadImage = true;
		} else {
			couldLoadImage = false;
		}
	}
	
	@Override
	public void update(int ms) {
		super.update(ms);
		Player pl = this.getStage().getPlayer();
		if ((pl.getPosition().distance(this.getPosition()) < this.getRadius() + pl.getRadius())) {
			new ForceFieldPowerup(this.getStage(), this.getStage().getPlayer());
		}
	}

	@Override
	public java.awt.Color noTextureColor() {
		return java.awt.Color.BLUE;
	}

}

package com.greenteam.spacefighters.entity.entityliving.powerup;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Powerup extends EntityLiving {

	public Powerup(Stage s) {
		super(s, 1);
	}
	
	@Override
	public Class<?> getSource() {
		return Powerup.class;
	}
	
	public BufferedImage getTexFromEnum(PowerupColor color) {
		try {
			switch(color) {
				case BLUE:
					return ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/Powerup-0.png"));
				case PURPLE:
					return ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/Powerup-1.png"));
				case ORANGE:
					return ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/Powerup-2.png"));
				case RED:
					return ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/Powerup-3.png"));
				case GREEN:
					return ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/Powerup-4.png"));
				case YELLOW:
					return ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/Powerup-5.png"));
				default :
					return ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/Powerup-0.png"));
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	public enum PowerupColor {
		BLUE, PURPLE, ORANGE, RED, GREEN, YELLOW
	}

}

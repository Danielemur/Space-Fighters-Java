package com.greenteam.spacefighters.entity.entityliving.starship.player;

import java.awt.Graphics;

import com.greenteam.spacefighters.entity.entityliving.starship.Starship;
import com.greenteam.spacefighters.stage.Stage;

public class Player extends Starship {
	
	public Player(Stage s, int health) {
		super(s, health);
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

}

package com.greenteam.spacefighters.entity.entityliving.starship.player;

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

}

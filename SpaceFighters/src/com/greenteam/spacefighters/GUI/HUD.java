package com.greenteam.spacefighters.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.greenteam.spacefighters.stage.Stage;

public class HUD {
	private Stage stage;
	
	public HUD(Stage stage) {
		this.stage = stage;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(String.format("%d", stage.getScore()), 10, 10);
	}
}

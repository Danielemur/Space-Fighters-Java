package com.greenteam.spacefighters.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class HUD {
	private Stage stage;
	
	public HUD(Stage stage) {
		this.stage = stage;
	}
	
	public void render(Graphics g) {
		Player p = stage.getPlayer();
		
		int charge = p.getCharge();
		int fullcharge = p.getMaxCharge();
		g.setColor(new Color(0f,.8f,0f,.5f));
		g.fillRect(150, 40, (int)(200*(double)charge/fullcharge), 10);
		g.setColor(new Color(.7f,.2f,0f,.5f));
		g.fillRect(150+(int)(200*charge/fullcharge), 40, (int)(200*(1-(double)charge/fullcharge)), 10);
		
		g.setColor(Color.WHITE);
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 18);
		g.setFont(f);
		g.drawString(String.format("Score: %d", stage.getScore()), 10, 23);
		g.drawString("CHARGE", 215, 50);
		
		int health = p.getHealth();
		if (health < 0) health = 0;
		g.setColor(new Color(0f,.8f,0f,.5f));
		g.fillRect(150, 5, (int)(200*(double)health/p.getMaxHealth()), 25);
		g.setColor(new Color(.7f,.2f,0f,.5f));
		g.fillRect(150+(int)(200*health/p.getMaxHealth()), 5, (int)(200*(1-(double)health/p.getMaxHealth())), 25);
		g.setColor(Color.WHITE);
		g.drawString(String.format("%d/%d", health, p.getMaxHealth()), 215, 23);
	}
}

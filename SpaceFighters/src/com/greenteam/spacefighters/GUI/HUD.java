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
		g.setColor(Color.WHITE);
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
		g.setFont(f);
		g.drawString(String.format("Score: %d", stage.getScore()), 10, 30);
		Player p = stage.getPlayer();
		g.setColor(new Color(0f,.8f,0f,.5f));
		g.fillRect(150, 5, (int)(200*(double)p.getHealth()/p.getMaxHealth()), 30);
		g.setColor(new Color(.7f,.2f,0f,.5f));
		g.fillRect(150+(int)(200*p.getHealth()/p.getMaxHealth()), 5, (int)(200*(1-(double)p.getHealth()/p.getMaxHealth())), 30);
		g.setColor(Color.WHITE);
		g.drawString(String.format("%d/%d", p.getHealth(), p.getMaxHealth()), 220, 27);
	}
}

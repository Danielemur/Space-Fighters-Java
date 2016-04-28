package com.greenteam.spacefighters.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class HUD {
	private Stage stage;
	private boolean gameOver;
	
	public HUD(Stage stage) {
		this.stage = stage;
		this.gameOver = false;
	}
	
	public void render(Graphics g) {
		Player p = stage.getPlayer();
		
		int charge = p.getCharge();
		int fullcharge = p.getMaxCharge();
		g.setColor(new Color(.8f,.7f,.0f,.7f));
		g.fillRect(150, 40, (int)(200*(double)charge/fullcharge), 10);
		g.setColor(new Color(.6f,.3f,.2f,.4f));
		if (charge <= fullcharge) g.fillRect(150+(int)(200*charge/fullcharge), 40, (int)(200*(1-(double)charge/fullcharge)), 10);
		
		g.setColor(Color.WHITE);
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 18);
		
		g.setFont(f);
		g.drawString(String.format("Score: %d", stage.getPlayer().getScore()), 10, 23);
		g.drawString(String.format("Money: $%d", stage.getPlayer().getMoney()), 10, 50);
		g.drawString("CHARGE", 215, 50);
		
		int health = p.getHealth();
		if (health < 0) health = 0;
		g.setColor(new Color(0f,.8f,0f,.7f));
		g.fillRect(150, 5, (int)(200*(double)health/p.getMaxHealth()), 25);
		g.setColor(new Color(.7f,.2f,0f,.4f));
		if (health <= p.getMaxHealth()) g.fillRect(150+(int)(200*health/p.getMaxHealth()), 5, (int)(200*(1-(double)health/p.getMaxHealth())), 25);
		g.setColor(Color.WHITE);
		g.drawString(String.format("%d/%d", health, p.getMaxHealth()), 215, 23);
		
		if (gameOver) {
			g.setColor(Color.RED);
			f = new Font(Font.MONOSPACED, Font.BOLD, 42);
			g.setFont(f);
			
			g.drawString(String.format("GAME OVER", health, p.getMaxHealth()), stage.getBounds().width/2-120, stage.getBounds().height/2-30);
			f = new Font(Font.MONOSPACED, Font.BOLD, 28);
			g.setFont(f);
			g.drawString(String.format("Your ship has exploded.", health, p.getMaxHealth()), stage.getBounds().width/2-190, stage.getBounds().height/2+50);
		}
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}

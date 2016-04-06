package com.greenteem.spacefighters.GUI;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.greenteam.spacefighters.entity.Entity;

public class GameArea extends JPanel {
	private static final long serialVersionUID = -2937557151448523567L;

	private ArrayList<Entity> entities;
	
	GameArea(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (Entity e : entities) {
			e.render(g);
		}
	}
}

package com.greenteem.spacefighters.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.greenteam.spacefighters.entity.Entity;

public class GameArea extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2937557151448523567L;

	private ArrayList<Entity> entities;
	private Timer timer;
	
	GameArea() {
		this.entities = new ArrayList<Entity>();
		this.setPreferredSize(new Dimension(320, 600));
		this.setSize(new Dimension(320, 600));
		timer = new Timer((int)(1000/Window.FPS), this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Entity e : entities) {
			e.render(g);
		}
	}
	
	public ArrayList<Entity> getEntities() {return entities;}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			for (Entity e : entities) {
				e.update((int)(1000/Window.FPS));
			}
			this.repaint();
		}
	}
}

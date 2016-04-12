package com.greenteam.spacefighters.stage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.entity.Entity;

public class Stage extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2937557151448523567L;

	private CopyOnWriteArrayList<Entity> entities;
	private Timer timer;
	private int width;
	private int height;
	
	public Stage(int width, int height) {
		this.entities = new CopyOnWriteArrayList<Entity>();
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		timer = new Timer((int)(1000/Window.FPS), this);
		System.out.println((int)(1000/Window.FPS));
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage image = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
		for (Entity e : entities) {
			e.render(image.getGraphics());
		}
		g.drawImage(image, 0, 0, this.getSize().width, this.getSize().height, null);
	}
	
	public List<Entity> getEntities() {return entities;}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			for (Entity e : entities) {
				e.update((int)(1000/Window.FPS));
			}
			this.repaint();
		}
	}

	public void remove(Entity entity) {
		entities.remove(entity);
	}
}

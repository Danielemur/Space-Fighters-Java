package com.greenteam.spacefighters.stage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;

public class Stage extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -2937557151448523567L;
	private static final int NUM_STARS = 40;

	private CopyOnWriteArrayList<Entity> entities;
	private Timer timer;
	private int width;
	private int height;
	private Player player;
	private Image background;
	
	public Stage(int width, int height, Player player) {
		this.entities = new CopyOnWriteArrayList<Entity>();
		this.width = width;
		this.height = height;
		this.player = player;
		/*
		try {
			background = ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/space.png"));
		} catch (IOException e) {
			background = null;
		}
		*/
		background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = background.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		for (int i = 0; i < Stage.NUM_STARS; ++i) {
			g.fillRect((int)(width*Math.random()), (int)(height*Math.random()), 1, 1);
		}
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		this.addKeyListener(this);
		timer = new Timer((int)(1000/Window.FPS), this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage image = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
		if (background != null) {
			image.getGraphics().drawImage(background, 0, 0, null);
		}
		for (Entity e : entities) {
			e.render(image.getGraphics());
		}
		g.drawImage(image, 0, 0, null);
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
	
	public void add(Entity entity) {
		entities.add(entity);
	}

	@Override
	public void keyPressed(KeyEvent ev) {
		if (player != null) {
			switch (ev.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.getVelocity().setX(-Player.MOVEMENT_SPEED);
				break;
			case KeyEvent.VK_RIGHT:
				player.getVelocity().setX(Player.MOVEMENT_SPEED);
				break;
			case KeyEvent.VK_UP:
				player.getVelocity().setY(-Player.MOVEMENT_SPEED);
				break;
			case KeyEvent.VK_DOWN:
				player.getVelocity().setY(Player.MOVEMENT_SPEED);
				break;
			case KeyEvent.VK_SPACE:
				player.fire();
				break;
			default: break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent ev) {
		if (player != null) {
			switch (ev.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
				player.getVelocity().setX(0);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				player.getVelocity().setY(0);
				break;
			default: break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}

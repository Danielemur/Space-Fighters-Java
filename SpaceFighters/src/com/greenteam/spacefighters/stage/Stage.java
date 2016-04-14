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

import com.greenteam.spacefighters.GUI.HUD;
import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;

public class Stage extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -2937557151448523567L;
	private static final int NUM_STARS = 40;
	private static final int BACKGROUND_SCROLL_SPEED = 2;
	private static final int BACKGROUND_OVERSIZE_RATIO = 5;
	private static final int STARFIELD_LAYERS = 3;

	private CopyOnWriteArrayList<Entity> entities;
	private Timer timer;
	private int width;
	private int height;
	private Player player;
	private int score;
	private HUD hud;
	private Timer firePrimaryTimer;
	private Timer fireSecondaryTimer;
	private Image[] starfields;
	private double[] backgroundOffsets;
	
	public Stage(int width, int height, Player player) {
		this.entities = new CopyOnWriteArrayList<Entity>();
		this.width = width;
		this.height = height;
		this.player = player;
		this.score = 0;
		this.hud = null;
		this.backgroundOffsets = new double[STARFIELD_LAYERS];
		this.starfields = new BufferedImage[STARFIELD_LAYERS];
		for (int i = 0; i < STARFIELD_LAYERS; ++i) {
			starfields[i] = new BufferedImage(width, height*Stage.BACKGROUND_OVERSIZE_RATIO, BufferedImage.TYPE_INT_ARGB);
			Graphics g = starfields[i].getGraphics();
			g.setColor(Color.WHITE);
			for (int j = 0; j < Stage.NUM_STARS*Stage.BACKGROUND_OVERSIZE_RATIO; ++j) {
				g.fillRect((int)(width*Math.random()), (int)(height*Stage.BACKGROUND_OVERSIZE_RATIO*Math.random()), 1, 1);
			}
		}
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		this.addKeyListener(this);
		firePrimaryTimer = new Timer((int)(500/Window.FPS), this);
		fireSecondaryTimer = new Timer((int)(500/Window.FPS), this);
		timer = new Timer((int)(1000/Window.FPS), this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		BufferedImage image = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < STARFIELD_LAYERS; ++i) {
			if (starfields[i] != null) {
				image.getGraphics().drawImage(starfields[i], 0, (int)backgroundOffsets[i], null);
				image.getGraphics().drawImage(starfields[i], 0, (int)(backgroundOffsets[i] - starfields[i].getHeight(null)), null);
			}
		}
		for (Entity e : entities) {
			e.render(image.getGraphics());
		}
		g.drawImage(image, 0, 0, null);
		if (hud != null) {
			hud.render(g);
		}
	}
	
	public List<Entity> getEntities() {return entities;}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			for (int i = 0; i < STARFIELD_LAYERS; ++i) {
				backgroundOffsets[i] += (double)Stage.BACKGROUND_SCROLL_SPEED/Math.pow((i+1),0.5);
				if (backgroundOffsets[i] > Stage.BACKGROUND_OVERSIZE_RATIO*this.getHeight()) {
					backgroundOffsets[i] = 0;
				}
			}
			for (Entity e : entities) {
				e.update((int)(1000/Window.FPS));
			}
			this.repaint();
		}
		else if (ev.getSource() == firePrimaryTimer) {
			player.fire(0);
		}
		else if (ev.getSource() == fireSecondaryTimer) {
			player.fire(1);
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
			case KeyEvent.VK_Z:
				firePrimaryTimer.start();
				break;
			case KeyEvent.VK_X:
				fireSecondaryTimer.start();
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
			case KeyEvent.VK_Z:
				firePrimaryTimer.stop();
				break;
			case KeyEvent.VK_X:
				fireSecondaryTimer.stop();
				break;
			default: break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//do nothing
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setHUD(HUD hud) {
		this.hud = hud;
	}
	
	public void pause() {
		timer.stop();
	}
	
	public Entity getNearestEntity(Entity entity) {
	    double bestDistance = Double.POSITIVE_INFINITY;
	    Entity nearestEntity = null;
	    for (Entity testEntity : entities) {
	    	if (testEntity != entity) {
	    		double distance = entity.getPosition().distance(testEntity.getPosition()); 
	    		if (distance < bestDistance) {
	                nearestEntity = testEntity;
	                bestDistance = distance;
	            }	
	    	}
	    }
	    return nearestEntity;
	}
	
	public Entity getNearestEntity(Entity entity, Class<?> cl) {
	    double bestDistance = Double.POSITIVE_INFINITY;
	    Entity nearestEntity = null;
	    for (Entity testEntity : entities) {
	    	if (testEntity != entity && cl.isInstance(testEntity)) {
	    		double distance = entity.getPosition().distance(testEntity.getPosition()); 
	    		if (distance < bestDistance) {
	                nearestEntity = testEntity;
	                bestDistance = distance;
	            }	
	    	}
	    }
	    return nearestEntity;
	}
	
}

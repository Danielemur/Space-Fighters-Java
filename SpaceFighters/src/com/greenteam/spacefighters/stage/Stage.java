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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.greenteam.spacefighters.GUI.HUD;
import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;

public class Stage extends JPanel implements ActionListener, KeyListener {
	public static final int WIDTH = 2400;
	public static final int HEIGHT= 2400;
	private static final long serialVersionUID = -2937557151448523567L;
	private static final int NUM_STARS = 120;
	private static final double BACKGROUND_SCROLL_SPEED = 2.5;
	private static final int BACKGROUND_OVERSIZE_RATIO = 5;
	private static final int STARFIELD_LAYERS = 3;

	private CopyOnWriteArrayList<Entity> entities;
	private Timer timer;
	private Player player;
	private int score;
	private HUD hud;
	private Timer firePrimaryTimer;
	private Timer fireSecondaryTimer;
	private Timer fireTertiaryTimer;
	private Image[] starfields;
	private double[] backgroundOffsets;
	private boolean upKeyPressed;
	private boolean leftKeyPressed;
	private boolean rightKeyPressed;
	
	public Stage(int width, int height, Player player) {
		this.entities = new CopyOnWriteArrayList<Entity>();
		this.player = player;
		this.score = 0;
		this.hud = null;
		this.backgroundOffsets = new double[STARFIELD_LAYERS];
		this.starfields = new BufferedImage[STARFIELD_LAYERS];
		for (int i = 0; i < STARFIELD_LAYERS; ++i) {
			starfields[i] = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics g = starfields[i].getGraphics();
			g.setColor(Color.WHITE);
			for (int j = 0; j < Stage.NUM_STARS; ++j) {
				g.fillRect((int)(WIDTH * Math.random()), (int)(HEIGHT * Math.random()), 1, 1);
			}
		}
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		this.addKeyListener(this);
		firePrimaryTimer = new Timer((int)(500/Window.FPS), this);
		firePrimaryTimer.setInitialDelay(0);
		fireSecondaryTimer = new Timer((int)(500/Window.FPS), this);
		fireSecondaryTimer.setInitialDelay(0);
		fireTertiaryTimer = new Timer((int)(10000/Window.FPS), this);
		fireTertiaryTimer.setInitialDelay(0);
		timer = new Timer((int)(1000/Window.FPS), this);
		timer.start();
		upKeyPressed = false;
		leftKeyPressed = false;
		rightKeyPressed = false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Vec2 offsetMax = new Vec2(WIDTH - this.getWidth(), HEIGHT - this.getHeight());
		Vec2 offsetMin = Vec2.ZERO;
		
		Vec2 offset = new Vec2(player.getPosition().getX() - this.getWidth() / 2,
							   player.getPosition().getY() - this.getHeight() / 2);
		
		offset = offset.min(offsetMax).max(offsetMin);
		//g.setClip((int)offset.getX(), (int)offset.getY(), viewWidth, viewHeight);
		g.translate(-(int)offset.getX(), -(int)offset.getY());
		player.render(g);
		
		for (int i = 0; i < STARFIELD_LAYERS; ++i) {
			if (starfields[i] != null) {
				g.drawImage(starfields[i], 0, 0/*(int)backgroundOffsets[i]*/, null);
				g.drawImage(starfields[i], 0, 0/*(int)(backgroundOffsets[i] - starfields[i].getHeight(null))*/, null);
			}
		}
		for (Entity e : entities) {
			if (e != player) {
				e.render(g);
			}
		}
		g.translate((int)offset.getX(), (int)offset.getY());
		if (hud != null) {
			hud.render(g);
		}
	}
	
	public List<Entity> getEntities() {return entities;}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			if (leftKeyPressed && !rightKeyPressed) {
				player.setOrientation(player.getOrientation().rotate(Vec2.ZERO, Math.PI / 32));
				if (upKeyPressed) {
					Vec2 orientation = player.getOrientation();
					player.setVelocity(orientation.scale(Player.MOVEMENT_SPEED).multiply(new Vec2(1, -1)));
				}
			} else if (rightKeyPressed && !leftKeyPressed) {
				player.setOrientation(player.getOrientation().rotate(Vec2.ZERO, -Math.PI / 32));
				if (upKeyPressed) {
					Vec2 orientation = player.getOrientation();
					player.setVelocity(orientation.scale(Player.MOVEMENT_SPEED).multiply(new Vec2(1, -1)));
				}
			}
			for (int i = 0; i < STARFIELD_LAYERS; ++i) {
				backgroundOffsets[i] += (double)Stage.BACKGROUND_SCROLL_SPEED/Math.pow((i+1),0.5);
				if (backgroundOffsets[i] > Stage.BACKGROUND_OVERSIZE_RATIO * this.getHeight()) {
					backgroundOffsets[i] = 0;
				}
			}
			for (Entity e : entities) {
				e.update((int)(1000 / Window.FPS));
			}
			this.repaint();
		}
		else if (ev.getSource() == firePrimaryTimer) {
			player.fire(0);
		}
		else if (ev.getSource() == fireSecondaryTimer) {
			player.fire(1);
		}
		else if (ev.getSource() == fireTertiaryTimer) {
			player.fire(2);
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
			{
				leftKeyPressed = true;
			}
				break;
			case KeyEvent.VK_RIGHT:
			{
				rightKeyPressed = true;
			}
				break;
			case KeyEvent.VK_UP:
				upKeyPressed = true;
				Vec2 orientation = player.getOrientation();
				player.setVelocity(orientation.scale(Player.MOVEMENT_SPEED).multiply(new Vec2(1, -1)));
				break;
			case KeyEvent.VK_DOWN:
				break;
			case KeyEvent.VK_Z:
				if (!firePrimaryTimer.isRunning()) {
					firePrimaryTimer.restart();
					firePrimaryTimer.start();
				}
				break;
			case KeyEvent.VK_X:
				if (!fireSecondaryTimer.isRunning()) {
					fireSecondaryTimer.restart();
					fireSecondaryTimer.start();
				}
				break;
			case KeyEvent.VK_C:
				if (!fireTertiaryTimer.isRunning()) {
					fireTertiaryTimer.restart();
					fireTertiaryTimer.start();
				}
				break;
			case KeyEvent.VK_SPACE:
				if (this.isPaused()) {
					this.resume();
				}
				else {
					this.pause();
				}
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
				leftKeyPressed = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightKeyPressed = false;
				break;
			case KeyEvent.VK_UP:
				player.setVelocity(Vec2.ZERO);
				upKeyPressed = false;
				break;
			case KeyEvent.VK_DOWN:
				break;
			case KeyEvent.VK_Z:
				firePrimaryTimer.stop();
				break;
			case KeyEvent.VK_X:
				fireSecondaryTimer.stop();
				break;
			case KeyEvent.VK_C:
				fireTertiaryTimer.stop();
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
		this.add(player);
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
	
	public void resume() {
		timer.start();
	}
	
	public boolean isPaused() {
		return !timer.isRunning();
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public static boolean inStage(Vec2 pos) {
		return (pos.getX() > 0 && pos.getX() < Stage.WIDTH &&
				pos.getY() > 0 && pos.getY() < Stage.HEIGHT);
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

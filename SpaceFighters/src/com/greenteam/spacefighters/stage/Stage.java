package com.greenteam.spacefighters.stage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import com.greenteam.spacefighters.GUI.HUD;
import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;

public class Stage extends JPanel implements ActionListener, MouseListener {
	public static final int WIDTH = 2400;
	public static final int HEIGHT= 2400;
	private static final long serialVersionUID = -2937557151448523567L;
	private static final int NUM_STARS = 120;
	private static final double BACKGROUND_SCROLL_SPEED = 2.5;
	private static final int BACKGROUND_OVERSIZE_RATIO = 5;
	private static final int STARFIELD_LAYERS = 3;
	
	private static final String MOVE_FORWARD = "FORWARD";
	private static final String MOVE_BACKWARD = "BACKWARD";
	private static final String MOVE_LEFT = "LEFT";
	private static final String MOVE_RIGHT = "RIGHT";
	private static final String FIRE_PRIMARY = "FIREPRIMARY";
	private static final String FIRE_SECONDARY = "FIRESECONDARY";
	private static final String FIRE_TERTIARY = "FIRETERTIARY";
	private static final String FIRE_QUATERNARY = "FIREQUATERNARY";
	private static final String FIRE_CHAIN_BEAM = "FIRECHAINBEAM";
	private static final String PAUSE = "PAUSE";
	
	private static final String PRESSED = " pressed";
	private static final String RELEASED = " released";
	
	private static final double ALL_MOVEMENT_DEAD_ZONE = 20;
	private static final double LINEAR_MOVEMENT_DEAD_ZONE = 120;
	private static final double ROTATION_DEAD_ZONE_SECTOR_SIZE = 0.2; //radians

	
	private Map<Integer, CopyOnWriteArrayList<Entity>> entities;
	private Timer timer;
	private Player player;
	private HUD hud;
	private Timer firePrimaryTimer;
	private Timer fireSecondaryTimer;
	private Timer fireTertiaryTimer;
	private Timer fireQuaternaryTimer;
	private Image[] starfields;
	private double[] backgroundOffsets;
	private boolean upKeyPressed;
	private boolean downKeyPressed;
	private boolean leftKeyPressed;
	private boolean rightKeyPressed;
	private boolean mouseEnabled;
	
	public Stage(int width, int height, Player player) {
		this.entities = Collections.synchronizedSortedMap(new TreeMap<Integer, CopyOnWriteArrayList<Entity>>());//CopyOnWriteArrayList<Entity>();
		entities.put(-1, new CopyOnWriteArrayList<Entity>());
		entities.put(0, new CopyOnWriteArrayList<Entity>());
		entities.put(1, new CopyOnWriteArrayList<Entity>());
		this.player = player;
		this.hud = null;
		this.mouseEnabled = true;
		this.backgroundOffsets = new double[STARFIELD_LAYERS];
		
		this.starfields = new BufferedImage[STARFIELD_LAYERS];
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
		for (int i = 0; i < STARFIELD_LAYERS; ++i) {
			starfields[i] = config.createCompatibleImage(WIDTH, HEIGHT);
			Graphics g = starfields[i].getGraphics();
			g.setColor(java.awt.Color.WHITE);
			for (int j = 0; j < Stage.NUM_STARS; ++j) {
				g.fillRect((int)(WIDTH * Math.random()), (int)(HEIGHT * Math.random()), 1, 1);
			}
		}
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed UP"),MOVE_FORWARD+PRESSED);
		this.getActionMap().put(MOVE_FORWARD+PRESSED, new MoveActionPressed(DirectionKey.FORWARD));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed DOWN"),MOVE_BACKWARD+PRESSED);
		this.getActionMap().put(MOVE_BACKWARD+PRESSED, new MoveActionPressed(DirectionKey.BACK));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed LEFT"),MOVE_LEFT+PRESSED);
		this.getActionMap().put(MOVE_LEFT+PRESSED, new MoveActionPressed(DirectionKey.LEFT));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed RIGHT"),MOVE_RIGHT+PRESSED);
		this.getActionMap().put(MOVE_RIGHT+PRESSED, new MoveActionPressed(DirectionKey.RIGHT));
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"),MOVE_FORWARD+RELEASED);
		this.getActionMap().put(MOVE_FORWARD+RELEASED, new MoveActionReleased(DirectionKey.FORWARD));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"),MOVE_BACKWARD+RELEASED);
		this.getActionMap().put(MOVE_BACKWARD+RELEASED, new MoveActionReleased(DirectionKey.BACK));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"),MOVE_LEFT+RELEASED);
		this.getActionMap().put(MOVE_LEFT+RELEASED, new MoveActionReleased(DirectionKey.LEFT));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"),MOVE_RIGHT+RELEASED);
		this.getActionMap().put(MOVE_RIGHT+RELEASED, new MoveActionReleased(DirectionKey.RIGHT));
		
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed Z"),FIRE_PRIMARY+PRESSED);
		this.getActionMap().put(FIRE_PRIMARY+PRESSED, new FireKeyPressed(FireKey.PRIMARY));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed X"),FIRE_SECONDARY+PRESSED);
		this.getActionMap().put(FIRE_SECONDARY+PRESSED, new FireKeyPressed(FireKey.SECONDARY));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed C"),FIRE_TERTIARY+PRESSED);
		this.getActionMap().put(FIRE_TERTIARY+PRESSED, new FireKeyPressed(FireKey.TERTIARY));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed F"),FIRE_QUATERNARY+PRESSED);
		this.getActionMap().put(FIRE_QUATERNARY+PRESSED, new FireKeyPressed(FireKey.QUATERNARY));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed V"),FIRE_CHAIN_BEAM+PRESSED);
		this.getActionMap().put(FIRE_CHAIN_BEAM+PRESSED, new FireKeyPressed(FireKey.CHAINBEAM));
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released Z"),FIRE_PRIMARY+RELEASED);
		this.getActionMap().put(FIRE_PRIMARY+RELEASED, new FireKeyReleased(FireKey.PRIMARY));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released X"),FIRE_SECONDARY+RELEASED);
		this.getActionMap().put(FIRE_SECONDARY+RELEASED, new FireKeyReleased(FireKey.SECONDARY));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released C"),FIRE_TERTIARY+RELEASED);
		this.getActionMap().put(FIRE_TERTIARY+RELEASED, new FireKeyReleased(FireKey.TERTIARY));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released F"),FIRE_QUATERNARY+RELEASED);
		this.getActionMap().put(FIRE_QUATERNARY+RELEASED, new FireKeyReleased(FireKey.QUATERNARY));
		
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed SPACE"),PAUSE+PRESSED);
		this.getActionMap().put(PAUSE+PRESSED, new PauseAction());
		
		this.addMouseListener(this);
		
		firePrimaryTimer = new Timer((int)(2000/Window.FPS), this);
		firePrimaryTimer.setInitialDelay(0);
		fireSecondaryTimer = new Timer((int)(10000/Window.FPS), this);
		fireSecondaryTimer.setInitialDelay(0);
		fireTertiaryTimer = new Timer((int)(10000/Window.FPS), this);
		fireTertiaryTimer.setInitialDelay(0);
		fireQuaternaryTimer = new Timer((int)(10000/Window.FPS), this);
		fireQuaternaryTimer.setInitialDelay(0);
		timer = new Timer((int)(1000/Window.FPS), this);
		upKeyPressed = false;
		downKeyPressed = false;
		leftKeyPressed = false;
		rightKeyPressed = false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Vec2 offsetMax = new Vec2(WIDTH - this.getWidth(), HEIGHT - this.getHeight());
		Vec2 offsetMin = Vec2.ZERO;
		
		Vec2 offset = new Vec2(player.getPosition().getX() - this.getWidth() / 2,
							   player.getPosition().getY() - this.getHeight() / 2);
		
		offset = offset.min(offsetMax).max(offsetMin);
		g.translate(-(int)offset.getX(), -(int)offset.getY());
		g.setClip((int)(offset.getX()), (int)(offset.getY()), (int)(offset.getX() + this.getWidth()), (int)(offset.getY() + this.getHeight()));
		for (int i = 0; i < STARFIELD_LAYERS; ++i) {
			if (starfields[i] != null) {
				g.drawImage(starfields[i], 0, 0, null);
				g.drawImage(starfields[i], 0, 0, null);
			}
		}
		for (Entry<Integer, CopyOnWriteArrayList<Entity>> entry : entities.entrySet()) {
			for (Entity e : entry.getValue()) {
				if (e != player) {
					e.render(g);
				}
			}
			if (entry.getKey() == player.getDefaultLayer())
				player.render(g);
		}
		
		g.translate((int)offset.getX(), (int)offset.getY());
		if (hud != null) {
			hud.render(g);
		}
	}
	
	private void doUpKey() {
		Vec2 orientation = player.getOrientation();
		player.setVelocity(orientation.scale(Player.MOVEMENT_SPEED).multiply(new Vec2(1, -1)));
	}
	
	private void doDownKey() {
		Vec2 orientation = player.getOrientation();
		player.setVelocity(orientation.scale(-Player.MOVEMENT_SPEED).multiply(new Vec2(1, -1)));
	}
	
	public Map<Integer, CopyOnWriteArrayList<Entity>> getEntities() {return entities;}

	private Vec2 getPlayerOffset() {
		Vec2 offsetMax = new Vec2(WIDTH - this.getWidth(), HEIGHT - this.getHeight());
		Vec2 offsetMin = Vec2.ZERO;
		
		Vec2 offset = new Vec2(player.getPosition().getX() - this.getWidth() / 2,
							   player.getPosition().getY() - this.getHeight() / 2);
		
		offset = offset.min(offsetMax).max(offsetMin);
		return new Vec2((player.getPosition().getX()-offset.getX()),(player.getPosition().getY()-offset.getY()));
	}
	
	private Vec2 convertMousePosition() {
		Point p = getMousePosition();
		if (p==null) {
			return null;
		}
		else {
			return new Vec2(p.x, p.y);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			if (mouseEnabled) {
				Vec2 playerPos = this.getPlayerOffset();
				Vec2 mousePos = this.convertMousePosition();
				if ((playerPos != null) && (mousePos != null)) {
					Vec2 relativeMousePosition = playerPos.subtract(mousePos);
					if (relativeMousePosition.magnitude() > ALL_MOVEMENT_DEAD_ZONE) {
						Vec2 transformedPosition = relativeMousePosition.rotate(Vec2.ZERO, -player.getOrientation().angle());
						double angle = transformedPosition.angle();
						if (Math.abs(angle) > ROTATION_DEAD_ZONE_SECTOR_SIZE) {
							if (angle > 0) {
								leftKeyPressed = true;
								rightKeyPressed = false;
							}
							else {
								rightKeyPressed = true;
								leftKeyPressed = false;
							}
						}
						else {
							leftKeyPressed = false;
							rightKeyPressed = false;
						}
						if (relativeMousePosition.magnitude() > LINEAR_MOVEMENT_DEAD_ZONE) {
							upKeyPressed = true;
							downKeyPressed = false;
							doUpKey();
						}
						else {
							player.setVelocity(Vec2.ZERO);
							upKeyPressed = false;
							downKeyPressed = false;
						}
					}
				}
				/*
				else if ((mousePos == null) && (playerPos != null)) {
					player.setVelocity(Vec2.ZERO);
					upKeyPressed = false;
					leftKeyPressed = false;
					rightKeyPressed = false;
				}
				*/
			}
			if (leftKeyPressed && !rightKeyPressed) {
				player.setOrientation(player.getOrientation().rotate(Vec2.ZERO, Math.PI / 32));
				if (upKeyPressed && !downKeyPressed) {
					doUpKey();
				} else if (downKeyPressed && !upKeyPressed) {
					doDownKey();
				}
			} else if (rightKeyPressed && !leftKeyPressed) {
				player.setOrientation(player.getOrientation().rotate(Vec2.ZERO, -Math.PI / 32));
				if (upKeyPressed && !downKeyPressed) {
					doUpKey();
				} else if (downKeyPressed && !upKeyPressed) {
					doDownKey();
				}
			}
			for (int i = 0; i < STARFIELD_LAYERS; ++i) {
				backgroundOffsets[i] += (double)Stage.BACKGROUND_SCROLL_SPEED/Math.pow((i+1),0.5);
				if (backgroundOffsets[i] > Stage.BACKGROUND_OVERSIZE_RATIO * this.getHeight()) {
					backgroundOffsets[i] = 0;
				}
			}
			
			for (CopyOnWriteArrayList<Entity> array : entities.values()) {
				for (Entity e : array) {
					if (e.isUpdatable())
						e.update((int)(700 / Window.FPS));
				}
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
		else if (ev.getSource() == fireQuaternaryTimer) {
			player.fire(3);
		}
	}

	public void remove(Entity entity) {
		for (CopyOnWriteArrayList<Entity> arr : entities.values())
			if (arr.contains(entity))
				arr.remove(entity);
	}
	
	public void add(Entity entity) {
		entities.get(entity.getDefaultLayer()).add(entity);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.add(player);
		this.player = player;
	}
	
	public void setHUD(HUD hud) {
		this.hud = hud;
	}
	
	public void gameOver() {
		hud.setGameOver(true);
		this.pause();
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
	    for (CopyOnWriteArrayList<Entity> array : entities.values()) {
	    	for (Entity testEntity : array) {
	    		if (testEntity != entity) {
	    			double distance = entity.getPosition().distance(testEntity.getPosition());
	    			if (distance < bestDistance) {
	    				nearestEntity = testEntity;
	    				bestDistance = distance;
	    			}	
	    		}
	    	}
	    }
	    return nearestEntity;
	}
	
	public Entity getNearestEntity(Entity entity, Class<?> cl) {
	    double bestDistance = Double.POSITIVE_INFINITY;
	    Entity nearestEntity = null;
		for (CopyOnWriteArrayList<Entity> array : entities.values()) {
			for (Entity testEntity : array) {
				if (testEntity != entity && cl.isInstance(testEntity)) {
					double distance = entity.getPosition().distance(testEntity.getPosition()); 
					if (distance < bestDistance) {
						nearestEntity = testEntity;
						bestDistance = distance;
					}	
				}
			}
		}
	    return nearestEntity;
	}
	
	public Entity getNearestEntity(Entity entity, Set<Entity> ignore) {
	    double bestDistance = Double.POSITIVE_INFINITY;
	    Entity nearestEntity = null;
		for (CopyOnWriteArrayList<Entity> array : entities.values()) {
			for (Entity testEntity : array) {
				if (testEntity != entity && !ignore.contains(testEntity)) {
					double distance = entity.getPosition().distance(testEntity.getPosition());
					if (distance < bestDistance) {
						nearestEntity = testEntity;
						bestDistance = distance;
					}	
				}
			}
		}
	    return nearestEntity;
	}
	
	public Entity getNearestEntity(Entity entity, Set<Entity> ignore, Class<?> cl) {
	    double bestDistance = Double.POSITIVE_INFINITY;
	    Entity nearestEntity = null;
		for (CopyOnWriteArrayList<Entity> array : entities.values()) {
			for (Entity testEntity : array) {
				if (testEntity != entity && cl.isInstance(testEntity) && !ignore.contains(testEntity)) {
					double distance = entity.getPosition().distance(testEntity.getPosition()); 
					if (distance < bestDistance) {
						nearestEntity = testEntity;
						bestDistance = distance;
					}
				}
			}
		}
	    return nearestEntity;
	}
	
	private class MoveActionPressed extends AbstractAction {
		private static final long serialVersionUID = 481979749241664534L;
		
		private DirectionKey key;
		
		public MoveActionPressed(DirectionKey key) {
			super();
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (player != null) {
				switch (key) {
				case LEFT:
				{
					leftKeyPressed = true;
				}
					break;
				case RIGHT:
				{
					rightKeyPressed = true;
				}
					break;
				case FORWARD:
					upKeyPressed = true;
					doUpKey();
					break;
				case BACK:
					downKeyPressed = true;
					doDownKey();
					break;
				default:
					break;
				}
			}
		}
	}
	
	private class MoveActionReleased extends AbstractAction {
		private static final long serialVersionUID = 3135951535219119594L;
		
		private DirectionKey key;
		
		public MoveActionReleased(DirectionKey key) {
			super();
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (player != null) {
				switch (key) {
				case LEFT:
					leftKeyPressed = false;
					break;
				case RIGHT:
					rightKeyPressed = false;
					break;
				case FORWARD:
					player.setVelocity(Vec2.ZERO);
					upKeyPressed = false;
					break;
				case BACK:
					player.setVelocity(Vec2.ZERO);
					downKeyPressed = false;
					break;
				default:
					break;
				}
			}
		}
	}
	
	private class FireKeyPressed extends AbstractAction {
		private static final long serialVersionUID = -6212427508037713506L;
		
		private FireKey weapon;
		
		public FireKeyPressed(FireKey weapon) {
			this.weapon = weapon;
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			switch (weapon) {
			case PRIMARY:
				if (!firePrimaryTimer.isRunning()) {
					firePrimaryTimer.restart();
					firePrimaryTimer.start();
				}
				break;
			case SECONDARY:
				if (!fireSecondaryTimer.isRunning()) {
					fireSecondaryTimer.restart();
					fireSecondaryTimer.start();
				}
				break;
			case TERTIARY:
				if (!fireTertiaryTimer.isRunning()) {
					fireTertiaryTimer.restart();
					fireTertiaryTimer.start();
				}
				break;
			case QUATERNARY:
				if (!fireQuaternaryTimer.isRunning()) {
					fireQuaternaryTimer.restart();
					fireQuaternaryTimer.start();
				}
				break;
			case CHAINBEAM:
				player.fire(4);
				break;
			default:
				break;
			}
		}
	}
	
	private class FireKeyReleased extends AbstractAction {
		private static final long serialVersionUID = -2618332729409396890L;
		
		private FireKey weapon;
		
		public FireKeyReleased(FireKey weapon) {
			this.weapon = weapon;
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			switch (weapon) {
			case PRIMARY:
				firePrimaryTimer.stop();
				break;
			case SECONDARY:
				fireSecondaryTimer.stop();
				break;
			case TERTIARY:
				fireTertiaryTimer.stop();
				break;
			case QUATERNARY:
				fireQuaternaryTimer.stop();
				break;
			default:
				break;
			}
		}
	}
	
	private class PauseAction extends AbstractAction {
		private static final long serialVersionUID = 5280811003086658604L;

		public PauseAction() {}

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (Stage.this.isPaused()) {
				Stage.this.resume();
			}
			else {
				Stage.this.pause();
			}
		}
	}
	
	private enum DirectionKey {
		FORWARD, BACK, LEFT, RIGHT
	}
	
	private enum FireKey {
		PRIMARY, SECONDARY, TERTIARY, QUATERNARY, CHAINBEAM
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		if (mouseEnabled) {
			switch (ev.getButton()) {
			case 1: //left mouse button (primary)
				if (!firePrimaryTimer.isRunning()) {
					firePrimaryTimer.restart();
					firePrimaryTimer.start();
				}
				break;
			case 2: //middle mouse button (secondary)
				if (!fireSecondaryTimer.isRunning()) {
					fireSecondaryTimer.restart();
					fireSecondaryTimer.start();
				}
				break;
			case 3: //right mouse button (tertiary)
				if (!fireTertiaryTimer.isRunning()) {
					fireTertiaryTimer.restart();
					fireTertiaryTimer.start();
				}
				break;
			default: break; //do nothing
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		if (mouseEnabled) {
			switch (ev.getButton()) {
			case 1: //left mouse button (primary)
				firePrimaryTimer.stop();
				break;
			case 2: //middle mouse button (secondary)
				fireSecondaryTimer.stop();
				break;
			case 3: //right mouse button (tertiary)
				fireTertiaryTimer.stop();
				break;
			default: break; //do nothing
			}
		}
	}
	
	public void setMouseEnabled(boolean mouseEnabled) {
		this.mouseEnabled = mouseEnabled;
	}
}
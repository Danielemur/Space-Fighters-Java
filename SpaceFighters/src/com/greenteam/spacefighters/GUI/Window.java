package com.greenteam.spacefighters.GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player.PlayerShipColor;
import com.greenteam.spacefighters.stage.LevelLoader;
import com.greenteam.spacefighters.stage.Stage;
import com.greenteam.spacefighters.GUI.tutorial.*;

public class Window extends JFrame implements WindowListener {
	private static final long serialVersionUID = 8514984102701282740L;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	
	public static final double FPS = 60;
	public static final String TITLE_SCREEN = "TITLE";
	public static final String STAGE = "STAGE";
	public static final String STORESCREEN = "STORESCREEN";
	public static final String MOVEMENT_TUTORIAL = "MOVEMENT";
	public static final String PROJECTILE_TUTORIAL = "PROJECTILE";
	public static final String ENEMY_TUTORIAL = "ENEMY";
	public static final String POWERUPTUTORIAL = "POWERUP";
	
	private Stage stage;
	private LevelLoader loader;
	private TitleScreen title;
	private StoreScreen store;
	private MovementTutorialScreen movementTutorial; 
	private ProjectileTutorialScreen projectileTutorial; 
	private EnemyTutorialScreen enemyTutorial; 
	private PowerupTutorialScreen powerupTutorial; 
	
	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loader = new LevelLoader(Window.WIDTH, Window.HEIGHT, null);
		stage = loader.getStage();
		store = new StoreScreen(stage);
		title = new TitleScreen(this);
		movementTutorial = new MovementTutorialScreen(this);
		projectileTutorial = new ProjectileTutorialScreen(this); 
		enemyTutorial = new EnemyTutorialScreen(this);
		powerupTutorial = new PowerupTutorialScreen(this);
		
		final JPanel contentPane = new JPanel();
		contentPane.setLayout(new CardLayout());
		contentPane.add(title, TITLE_SCREEN);
		contentPane.add(movementTutorial, MOVEMENT_TUTORIAL);
		contentPane.add(projectileTutorial, PROJECTILE_TUTORIAL);
		contentPane.add(enemyTutorial, ENEMY_TUTORIAL);
		contentPane.add(powerupTutorial, POWERUPTUTORIAL);
		contentPane.add(stage, STAGE);
		contentPane.setBounds(new Rectangle(Window.WIDTH, Window.HEIGHT));
		contentPane.add(store, STORESCREEN);
		
		this.setBounds(new Rectangle(Window.WIDTH, Window.HEIGHT));

		this.setTitle("SpaceFighters");
		this.setIconImage(Player.getTexFromEnum(PlayerShipColor.RED));
		this.setMinimumSize(new Dimension(Window.WIDTH, Window.HEIGHT));
		this.setLocationRelativeTo(null);
		this.setContentPane(contentPane);
		this.setVisible(true);
		this.createBufferStrategy(2);
	}
	
    public static void main(String[] args) {
    	new Window();
    }

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
	
	public void setCard(String card) {
		((CardLayout)this.getContentPane().getLayout()).show(this.getContentPane(), card);
		if (card.equals(STAGE)) {
			stage.resume();
		}
		else {
			stage.pause();
		}
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public TitleScreen getTitleScreen() {
		return title;
	}
	
	public StoreScreen getStoreScreen() {
		return store;
	}
}

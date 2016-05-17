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

public class Window extends JFrame implements WindowListener {
	private static final long serialVersionUID = 8514984102701282740L;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	
	public static final double FPS = 60;
	public static final String TITLE_SCREEN_CARDLAYOUT_NAME = "TITLE";
	public static final String STAGE_CARDLAYOUT_NAME = "STAGE";
	public static final String STORESCREEN_CARDLAYOUT_NAME = "STORESCREEN";
	
	private Stage stage;
	private LevelLoader loader;
	private TitleScreen title;
	private StoreScreen store;
	
	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loader = new LevelLoader(Window.WIDTH, Window.HEIGHT, null);
		stage = loader.getStage();
		store = new StoreScreen(stage);
		title = new TitleScreen(this);
		
		final JPanel contentPane = new JPanel();
		KeyboardInputHandlerHolder.handler = new KeyboardInputHandler(contentPane);
		contentPane.setLayout(new CardLayout());
		contentPane.add(title, TITLE_SCREEN_CARDLAYOUT_NAME);
		contentPane.add(stage, STAGE_CARDLAYOUT_NAME);
		contentPane.setBounds(new Rectangle(Window.WIDTH, Window.HEIGHT));
		contentPane.add(store, STORESCREEN_CARDLAYOUT_NAME);
		
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
		if (card.equals(STAGE_CARDLAYOUT_NAME)) {
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

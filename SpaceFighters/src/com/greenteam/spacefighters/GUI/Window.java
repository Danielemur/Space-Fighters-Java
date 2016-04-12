package com.greenteam.spacefighters.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.greenteam.spacefighters.entity.entityliving.TestEntityLiving;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TestEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.LevelLoader;
import com.greenteam.spacefighters.stage.Stage;
import com.greenteam.spacefighters.stage.TestLevelLoader;

public class Window extends JFrame implements WindowListener {
	private static final long serialVersionUID = 8514984102701282740L;
	private static final int WIDTH = 320;
	private static final int HEIGHT = 600;
	
	public static final double FPS = 60;
	
	private Player player;
	private Stage stage;
	private LevelLoader loader;
	
	Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stage = new Stage(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout(0,0));
		stage.setBorder(BorderFactory.createEmptyBorder());
		this.add(stage, BorderLayout.CENTER);
		this.setBounds(new Rectangle(WIDTH, HEIGHT));
		//this.pack();
		
		stage.getEntities().add(new TestEntityLiving(stage, WIDTH, HEIGHT, 100, 200));
		stage.getEntities().add(new TestEnemy(stage, WIDTH, HEIGHT, 20, 60));
		
		loader = new TestLevelLoader(stage, null);
		
		this.setTitle("Window!");
		this.setIconImage(new ImageIcon(this.getClass().getResource("/com/greenteam/spacefighters/assets/Pershing_Icon.png")).getImage());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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
	
}

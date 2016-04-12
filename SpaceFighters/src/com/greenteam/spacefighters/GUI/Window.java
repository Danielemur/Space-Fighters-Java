package com.greenteam.spacefighters.GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.greenteam.spacefighters.entity.entityliving.projectile.TestEntityLiving;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TestEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.LevelLoader;
import com.greenteam.spacefighters.stage.Stage;
import com.greenteam.spacefighters.stage.TestLevelLoader;

public class Window extends JFrame implements WindowListener {
	private static final long serialVersionUID = 8514984102701282740L;
	
	public static final double FPS = 60;
	
	private Player player;
	private Stage stage;
	private LevelLoader loader;
	
	Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stage = new Stage(320, 600);
		this.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(stage, BorderLayout.CENTER);
		
		stage.getEntities().add(new TestEntityLiving(stage, 320, 600, 100, 200));
		stage.getEntities().add(new TestEnemy(stage, 320, 600, 20, 60));
		
		loader = new TestLevelLoader(stage, null);
		
		this.pack();
		this.setTitle("Window!");
		this.setIconImage(new ImageIcon(this.getClass().getResource("../assets/Pershing_Icon.png")).getImage());
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

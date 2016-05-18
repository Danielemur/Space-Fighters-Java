package com.greenteam.spacefighters.GUI;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.greenteam.spacefighters.stage.Stage;

public class GameOverScreen extends JPanel implements ActionListener, ComponentListener {
	private static final long serialVersionUID = -20160518L;
	
	private JLabel score;
	private JButton returnToMain;
	private Stage stage;
	private JFrame window;
	
	public GameOverScreen(Stage stage, JFrame window) {
		this.stage = stage;
		this.window = window;
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		score = new JLabel("Score: 0");
		this.add(score, gbc);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		returnToMain = new JButton("Return to Main Menu");
		returnToMain.addActionListener(this);
		this.add(returnToMain, gbc);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == returnToMain) {
			((CardLayout)window.getContentPane().getLayout()).show(window.getContentPane(), Window.TITLE_SCREEN);
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		//do nothing
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		//do nothing
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		//do nothing
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		score.setText("Score: "+Integer.toString(stage.getPlayer().getScore()));
	}
}

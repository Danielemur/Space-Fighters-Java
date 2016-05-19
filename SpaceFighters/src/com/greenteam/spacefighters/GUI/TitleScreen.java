package com.greenteam.spacefighters.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class TitleScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8833873967148164038L;

	private static final int NUM_STARS = 120;
	
	private JLabel title;
	private JButton startButtonKeyboardInput;
	private JButton startButtonMouseInput;
	private JButton tutorialButton;
	private Window window;

	private double[] xpositions;
	private double[] ypositions;
	
	public TitleScreen(Window window) {
		this.window = window;
		
		this.setBackground(Color.BLACK);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		title = new JLabel("SpaceFighters");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
		title.setForeground(Color.LIGHT_GRAY);
		this.add(title, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextArea keyboardInstructions = new JTextArea("Use arrow keys to move, and use Z, X, C, V, and F to fire. Use Space to pause.");
		keyboardInstructions.setLineWrap(true);
		keyboardInstructions.setWrapStyleWord(true);
		keyboardInstructions.setEditable(false);
		keyboardInstructions.setOpaque(false);
		keyboardInstructions.setForeground(Color.LIGHT_GRAY);
		this.add(keyboardInstructions, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		startButtonKeyboardInput = new JButton("Start (Keyboard input)");
		startButtonKeyboardInput.addActionListener(this);
		this.add(startButtonKeyboardInput, gbc);
		
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextArea mouseInstructions = new JTextArea("Move mouse to move, use mouse buttons to fire, use Space to pause.");
		mouseInstructions.setLineWrap(true);
		mouseInstructions.setWrapStyleWord(true);
		mouseInstructions.setEditable(false);
		mouseInstructions.setOpaque(false);
		mouseInstructions.setForeground(Color.LIGHT_GRAY);
		this.add(mouseInstructions, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		startButtonMouseInput = new JButton("Start (Mouse Input)");
		startButtonMouseInput.addActionListener(this);
		this.add(startButtonMouseInput, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 1;
		tutorialButton = new JButton("Tutorial");
		tutorialButton.addActionListener(this);
		this.add(tutorialButton, gbc);
		
		xpositions = new double[TitleScreen.NUM_STARS];
		ypositions = new double[TitleScreen.NUM_STARS];
		for (int i = 0; i < TitleScreen.NUM_STARS; ++i) {
			xpositions[i] = Math.random();
			ypositions[i] = Math.random();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == startButtonKeyboardInput) {
			window.getStage().setMouseEnabled(false);
			window.setCard(Window.STAGE);
		}
		else if (ev.getSource() == tutorialButton) {
			window.getStage().setMouseEnabled(true);
			window.setCard(Window.MOVEMENT_TUTORIAL);
		}
		else if (ev.getSource() == startButtonMouseInput) {
			window.getStage().setMouseEnabled(true);
			window.setCard(Window.STAGE);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		for (int i = 0; i < TitleScreen.NUM_STARS; ++i) {
			g.fillRect((int)(xpositions[i]*this.getWidth()), (int)(ypositions[i]*this.getHeight()), 1, 1);
		}
	}
}

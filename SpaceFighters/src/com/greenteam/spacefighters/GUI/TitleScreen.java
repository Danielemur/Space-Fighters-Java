package com.greenteam.spacefighters.GUI;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8833873967148164038L;
	
	private JLabel title;
	private JButton startButtonKeyboardInput;
	private JButton startButtonMouseInput;
	private Window window;
	
	public TitleScreen(Window window) {
		this.window = window;
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		title = new JLabel("Title");
		this.add(title, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
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
		startButtonMouseInput = new JButton("Start (Mouse input)");
		startButtonMouseInput.addActionListener(this);
		this.add(startButtonMouseInput, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == startButtonKeyboardInput) {
			window.getStage().setMouseEnabled(false);
			window.setCard(Window.STAGE_CARDLAYOUT_NAME);
		}
		if (ev.getSource() == startButtonMouseInput) {
			window.getStage().setMouseEnabled(true);
			window.setCard(Window.STAGE_CARDLAYOUT_NAME);
		}
	}
}

package com.greenteam.spacefighters.GUI;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleScreen extends JPanel implements ActionListener {
	private JLabel title;
	private JButton startButton;
	private Window window;
	
	public TitleScreen(Window window) {
		this.window = window;
		
		this.setLayout(new GridLayout(2,1));
		
		title = new JLabel("Title");
		this.add(title);
		
		startButton = new JButton("Start");
		startButton.addActionListener(this);
		this.add(startButton);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == startButton) {
			window.setCard(Window.STAGE_CARDLAYOUT_NAME);
		}
	}
}

package com.greenteam.spacefighters.GUI.tutorial;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.greenteam.spacefighters.GUI.Window;

public abstract class TutorialScreen extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3798035225065059125L;

	private static final int NUM_STARS = 120;
	
	protected Window window;
	protected JPanel centerGrid;
	protected JButton prevScreen;
	protected JButton nextScreen;

	private double[] xpositions;
	private double[] ypositions;
	
	public TutorialScreen(Window w) {
		window = w;
		this.setLayout(new BorderLayout());
		centerGrid = new JPanel(new GridBagLayout());
		this.add(centerGrid, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		prevScreen = new JButton("Back");
		nextScreen = new JButton("next");
		prevScreen.addActionListener(this);
		nextScreen.addActionListener(this);
		buttonPanel.add(prevScreen);
		buttonPanel.add(nextScreen);
		this.add(buttonPanel, BorderLayout.SOUTH);
		xpositions = new double[TutorialScreen.NUM_STARS];
		ypositions = new double[TutorialScreen.NUM_STARS];
		for (int i = 0; i < TutorialScreen.NUM_STARS; ++i) {
			xpositions[i] = Math.random();
			ypositions[i] = Math.random();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == prevScreen) {
			((CardLayout)this.getParent().getLayout()).next(this.getParent());
		} else if (ev.getSource() == nextScreen) {
			((CardLayout)this.getParent().getLayout()).previous(this.getParent());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		for (int i = 0; i < TutorialScreen.NUM_STARS; ++i) {
			g.fillRect((int)(xpositions[i]*this.getWidth()), (int)(ypositions[i]*this.getHeight()), 1, 1);
		}
	}

}

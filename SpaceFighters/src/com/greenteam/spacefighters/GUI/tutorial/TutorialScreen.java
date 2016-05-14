package com.greenteam.spacefighters.GUI.tutorial;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.greenteam.spacefighters.GUI.Window;

public abstract class TutorialScreen extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3798035225065059125L;
	protected Window window;
	protected JButton prevScreen;
	protected JButton nextScreen;
	
	public TutorialScreen(Window w) {
		window = w;
		this.setLayout(new BorderLayout());
		prevScreen = new JButton("Back");
		nextScreen = new JButton("next");
		prevScreen.addActionListener(this);
		nextScreen.addActionListener(this);
		this.add(prevScreen);
		this.add(nextScreen);
	}

}

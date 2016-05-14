package com.greenteam.spacefighters.GUI.tutorial;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.greenteam.spacefighters.GUI.Window;

public class PowerupTutorialScreen extends TutorialScreen {
	
	public PowerupTutorialScreen(Window w) {
		super(w);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == prevScreen) {
			((CardLayout)this.getParent().getLayout()).next(this.getParent());
		} else if (ev.getSource() == nextScreen) {
			((CardLayout)this.getParent().getLayout()).previous(this.getParent());
		}
	}

}

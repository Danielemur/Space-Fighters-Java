package com.greenteam.spacefighters.GUI.tutorial;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.entity.entityliving.powerupcontainer.PowerupContainer;
import com.greenteam.spacefighters.entity.entityliving.powerupcontainer.PowerupContainer.PowerupColor;

public class PowerupTutorialScreen extends TutorialScreen {
	private static final long serialVersionUID = 7246934844521923388L;
	private static final String[] infoText = {
		"Health Restore-\tRestores 25 health to player.",
		"Health Boost-\tRestores 20 health to player, augmenting players health when possible.",
		"Charge Boost-\tTemporarily increases players charge level.",
		"Force Field-\tCreates a forcefield around the player for 10 seconds.",
		"Chain Beam-\tSeeks out and destroys nearby enemies."
	};
	
	public PowerupTutorialScreen(Window w) {
		super(w);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		for (PowerupColor color : PowerupColor.values()) {
			if (!color.equals(PowerupColor.ORANGE)) {
				int i = color.ordinal();
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.gridx = 0;
				gbc.gridy = i;
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.gridwidth = 1;
				ImageIcon pUpIcon = new ImageIcon(PowerupContainer.getTexFromEnum(color));
				centerGrid.add(new JLabel(pUpIcon), gbc);
				gbc.anchor = GridBagConstraints.WEST;
				gbc.gridx = 1;
				centerGrid.add(new JLabel(infoText[i]), gbc);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == prevScreen) {
			((CardLayout)this.getParent().getLayout()).next(this.getParent());
		} else if (ev.getSource() == nextScreen) {
			((CardLayout)this.getParent().getLayout()).show(window.getContentPane(), Window.TITLE_SCREEN);
		}
	}

}

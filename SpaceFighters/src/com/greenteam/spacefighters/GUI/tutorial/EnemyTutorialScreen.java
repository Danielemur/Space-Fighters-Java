package com.greenteam.spacefighters.GUI.tutorial;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.entity.entityliving.obstacle.asteroid.Asteroid;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.Enemy.EnemyShipColor;

public class EnemyTutorialScreen extends TutorialScreen {
	private static final long serialVersionUID = -3840244545725760175L;
	private static final String[] infoText = {
		"Asteroid-\tDamages the player and enemies alike on collision. Can shield from projectiles.",
		"Invader-\tTravels in straight lines, turning at the edge of the screen.",
		"Scout-\tTravels toward an arbitrary direction.",
		"Tiger Shark-\tFires toward the player while travelling to selected destination points.",
		"Raptor-\tTracks whiles firing toward the player."
	};
	
	public EnemyTutorialScreen(Window w) {
		super(w);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		ImageIcon asteroidIcon = new ImageIcon(Asteroid.getTexFromID(0));
		centerGrid.add(new JLabel(asteroidIcon), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		centerGrid.add(new JLabel(infoText[0]), gbc);
		for (EnemyShipColor color : EnemyShipColor.values()) {
			int i = color.ordinal() + 1;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridwidth = 1;
			ImageIcon enemyIcon = new ImageIcon(Enemy.getTexFromEnum(color));
			centerGrid.add(new JLabel(enemyIcon), gbc);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			centerGrid.add(new JLabel(infoText[i]), gbc);
		}
	}

}

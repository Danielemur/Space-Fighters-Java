package com.greenteam.spacefighters.GUI.tutorial;

import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile.ProjectileColor;

public class ProjectileTutorialScreen extends TutorialScreen {
	private static final long serialVersionUID = 2491164676643249778L;
	private static final String[] infoText = {
		"Linear Projectile-\tTravels in a straight line before dissipating.",
		"Homing Projectile-\tSeeks out and follows the nearest enemy.",
		"Explosive Projectile-\tTravels in a straight line before exploding.",
	};
	
	public ProjectileTutorialScreen(Window w) {
		super(w);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		for (ProjectileColor color : ProjectileColor.values()) {
			if (!color.equals(ProjectileColor.GREEN)) {
				int i = color.ordinal();
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.gridx = 0;
				gbc.gridy = i;
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.gridwidth = 1;
				ImageIcon projIcon = new ImageIcon(Projectile.getTexFromEnum(color));
				centerGrid.add(new JLabel(projIcon), gbc);
				gbc.anchor = GridBagConstraints.WEST;
				gbc.gridx = 1;
				centerGrid.add(new JLabel(infoText[i]), gbc);
			}
		}
	}

}

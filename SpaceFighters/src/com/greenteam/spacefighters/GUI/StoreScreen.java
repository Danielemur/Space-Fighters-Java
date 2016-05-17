package com.greenteam.spacefighters.GUI;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import com.greenteam.spacefighters.stage.Stage;
import com.greenteam.spacefighters.storeupgrades.StoreUpgrade;

public class StoreScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8105021008085531123L;
	
	private ArrayList<StoreUpgrade> upgrades;
	private JButton returnToGame;
	private Stage stage;
	private JList upgradeJList;

	public StoreScreen(Stage stage) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		returnToGame = new JButton("Return to Game");
		returnToGame.addActionListener(this);
		this.add(returnToGame, gbc);
		this.stage = stage;
	}
	
	public void setUpgrades(ArrayList<StoreUpgrade> upgrades) {
		this.upgrades = upgrades;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == returnToGame) {
			stage.resume();
			((CardLayout)this.getParent().getLayout()).show(this.getParent(), Window.STAGE);
		}
	}
}

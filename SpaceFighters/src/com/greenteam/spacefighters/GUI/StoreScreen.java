package com.greenteam.spacefighters.GUI;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import com.greenteam.spacefighters.stage.Stage;
import com.greenteam.spacefighters.storeupgrades.StoreUpgrade;

public class StoreScreen extends JPanel {
	private static final long serialVersionUID = -8105021008085531123L;
	
	private ArrayList<StoreUpgrade> upgrades;
	private Stage stage;
	private JList upgradeJList;

	StoreScreen(Stage stage, ArrayList<StoreUpgrade> upgrades) {
		this.setLayout(new GridBagLayout());
		
		this.stage = stage;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
	}
}

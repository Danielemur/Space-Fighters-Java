package com.greenteam.spacefighters.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.greenteam.spacefighters.stage.Stage;

public class DeathScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 20160518L;

	private Stage stage;
	private JButton returnToGame;
	
	public DeathScreen(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		
	}
}

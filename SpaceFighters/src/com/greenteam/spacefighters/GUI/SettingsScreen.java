package com.greenteam.spacefighters.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player.PlayerShipColor;

public class SettingsScreen extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3798035225065059125L;

	private static final int NUM_STARS = 120;
	
	protected Window window;
	protected JLabel inputInfo;
	protected JLabel colorInfo;
	protected JPanel centerGrid;
	protected JButton inputSelect;
	protected ShipButton colorSelect;
	protected JButton back;

	private double[] xpositions;
	private double[] ypositions;
	
	public SettingsScreen(Window w) {
		window = w;
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		this.setLayout(new GridBagLayout());		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(20, 20, 0, 0);
		inputInfo = new JLabel("Toggle user input mode:");
		inputInfo.setOpaque(false);
		inputInfo.setForeground(Color.LIGHT_GRAY);
		this.add(inputInfo, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weighty = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(20, 0, 0, 20);
		inputSelect = new JButton("Enable mouse mode");
		inputSelect.addActionListener(this);
		this.add(inputSelect, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 20, 0);
		colorInfo = new JLabel("Select ship color:");
		inputInfo.setOpaque(false);
		colorInfo.setForeground(Color.LIGHT_GRAY);
		this.add(colorInfo, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weighty = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 20);
		Image buttonTex = Player.getTexFromEnum(window.getStage().getPlayer().getColor());
		buttonTex = buttonTex.getScaledInstance(2 * buttonTex.getWidth(null), 2 * buttonTex.getHeight(null), Image.SCALE_SMOOTH);
		colorSelect = new ShipButton(buttonTex);
		colorSelect.addActionListener(this);
		this.add(colorSelect, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 20, 20, 20);
		back = new JButton("Back");
		back.addActionListener(this);
		this.add(back, gbc);
		
		xpositions = new double[SettingsScreen.NUM_STARS];
		ypositions = new double[SettingsScreen.NUM_STARS];
		for (int i = 0; i < SettingsScreen.NUM_STARS; ++i) {
			xpositions[i] = Math.random();
			ypositions[i] = Math.random();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == back) {
			((CardLayout)this.getParent().getLayout()).show(window.getContentPane(), Window.TITLE_SCREEN);
		} else if (ev.getSource() == inputSelect) {
			window.setMouseInput(!window.useMouseInput());
			if (window.useMouseInput())
				inputSelect.setText("Disable mouse mode");
			else
				inputSelect.setText("Enable mouse mode");
		} else if (ev.getSource() == colorSelect) {
			PlayerShipColor[] colors = PlayerShipColor.values();
			PlayerShipColor newColor = colors[(window.getStage().getPlayer().getColor().ordinal() + 1) % colors.length];
			window.getStage().getPlayer().setColor(newColor);
			Image newTex = Player.getTexFromEnum(newColor);
			newTex = newTex.getScaledInstance(2 * newTex.getWidth(null), 2 * newTex.getHeight(null), Image.SCALE_SMOOTH);
			colorSelect.setTex(newTex);
		}
	}
	
	private class ShipButton extends JButton{
		private static final long serialVersionUID = 8308089634069044941L;
		
		public ShipButton(Image img) {
			super(new ImageIcon(img));
			this.setOpaque(false);
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
			this.setFocusPainted(false);
		}
		
		public void setTex(Image img) {
			this.setIcon(new ImageIcon(img));
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		for (int i = 0; i < SettingsScreen.NUM_STARS; ++i) {
			g.fillRect((int)(xpositions[i]*this.getWidth()), (int)(ypositions[i]*this.getHeight()), 1, 1);
		}
	}

}

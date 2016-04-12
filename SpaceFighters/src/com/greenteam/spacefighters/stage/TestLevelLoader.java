package com.greenteam.spacefighters.stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TestEnemy;

public class TestLevelLoader extends LevelLoader implements ActionListener {
	private Timer timer;
	private Stage stage;
	
	public TestLevelLoader(Stage s, File f) {
		super(s, f);
		stage = s;
		timer = new Timer(500, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			stage.add(new TestEnemy(stage, 320, 600, 20, 60));
		}
	}

}

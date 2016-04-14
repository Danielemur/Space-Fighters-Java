package com.greenteam.spacefighters.stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import com.greenteam.spacefighters.GUI.HUD;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.ErraticEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.ShootingEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TestEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TrackerEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;

public class TestLevelLoader extends LevelLoader implements ActionListener {
	private Timer testEnemyTimer;
	private Timer erraticEnemyTimer;
	private Timer shootingEnemyTimer;
	private Timer trackerEnemyTimer;
	private Stage stage;
	
	public TestLevelLoader(Stage s, File f) {
		super(s, f);
		stage = s;
		testEnemyTimer = new Timer(550, this);
		erraticEnemyTimer = new Timer(750, this);
		shootingEnemyTimer = new Timer(2500, this);
		trackerEnemyTimer = new Timer(1000, this);
		
		Player player = new Player(stage, 100);
		player.setPosition(new Vec2(stage.getWidth()/2, stage.getHeight()*3/4));
		stage.add(player);
		stage.setPlayer(player);
		stage.setHUD(new HUD(stage));
		testEnemyTimer.start();
		erraticEnemyTimer.start();
		shootingEnemyTimer.start();
		trackerEnemyTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == testEnemyTimer) {
			stage.add(new TestEnemy(stage, 20, 60));
		}
		else if (ev.getSource() == erraticEnemyTimer) {
			stage.add(new ErraticEnemy(stage, 20, 60));
		}
		else if (ev.getSource() == shootingEnemyTimer) {
			stage.add(new ShootingEnemy(stage, 20, 60));
		}
		else if (ev.getSource() == trackerEnemyTimer) {
			stage.add(new TrackerEnemy(stage, 1, 0, 0));
		}
	}

}

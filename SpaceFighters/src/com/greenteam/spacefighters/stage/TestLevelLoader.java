package com.greenteam.spacefighters.stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.ErraticEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TestEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;

public class TestLevelLoader extends LevelLoader implements ActionListener {
	private Timer testEnemyTimer;
	private Timer erraticEnemyTimer;
	private Timer playerProjectileTimer;
	private Stage stage;
	private int width;
	private int height;
	
	public TestLevelLoader(Stage s, File f, int width, int height) {
		super(s, f, width, height);
		stage = s;
		this.width = width;
		this.height = height;
		testEnemyTimer = new Timer(550, this);
		erraticEnemyTimer = new Timer(750, this);
		playerProjectileTimer = new Timer(500, this);
		testEnemyTimer.start();
		erraticEnemyTimer.start();
		playerProjectileTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == testEnemyTimer) {
			stage.add(new TestEnemy(stage, width, height, 20, 60));
		}
		else if (ev.getSource() == erraticEnemyTimer) {
			stage.add(new ErraticEnemy(stage, width, height, 20, 60));
		}
		else if (ev.getSource() == playerProjectileTimer) {
			Projectile proj = new Projectile(stage, 1, 1, Player.class);
			proj.setVelocity(new Vec2(0, -500));
			proj.setPosition(new Vec2(160, 600));
			stage.add(proj);
		}
	}

}

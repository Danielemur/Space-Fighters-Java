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
	private Stage stage;
	
	public TestLevelLoader(Stage s, File f) {
		super(s, f);
		stage = s;
		testEnemyTimer = new Timer(550, this);
		erraticEnemyTimer = new Timer(750, this);
		
		Player player = new Player(stage, 100);
		player.setPosition(new Vec2(stage.getWidth()/2, stage.getHeight()*3/4));
		stage.add(player);
		stage.setPlayer(player);
		testEnemyTimer.start();
		erraticEnemyTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == testEnemyTimer) {
			stage.add(new TestEnemy(stage, 20, 60));
		}
		else if (ev.getSource() == erraticEnemyTimer) {
			stage.add(new ErraticEnemy(stage, 20, 60));
		}
//		else if (ev.getSource() == playerProjectileTimer) {
//			Projectile proj = new Projectile(stage, 1, 1, Player.class);
//			proj.setVelocity(new Vec2(0, -500));
//			proj.setPosition(new Vec2(160, 600));
//			stage.add(proj);
//		}
	}

}

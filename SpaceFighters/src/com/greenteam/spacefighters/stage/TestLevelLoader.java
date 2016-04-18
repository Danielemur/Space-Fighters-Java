package com.greenteam.spacefighters.stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import com.greenteam.spacefighters.GUI.HUD;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.powerup.HealthRestorePowerup;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.ErraticEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.ShootingEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TestEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TrackerEnemy;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;

public class TestLevelLoader extends LevelLoader implements ActionListener {
	private static final int TESTENEMY_SPAWNINTERVAL = 800;
	private static final int ERRATICENEMY_SPAWNINTERVAL = 1040;
	private static final int SHOOTINGENEMY_SPAWNINTERVAL = 1080;
	private static final int TRACKERENEMY_SPAWNINTERVAL = 1400;
	private static final int POWERUP_SPAWNINTERVAL = 5000; //remove this after implementing powerup spawning in Enemy
	
	private Stage stage;
	private Timer timer;
	private int time;
	
	public TestLevelLoader(Stage s, File f) {
		super(s, f);
		stage = s;
		timer = stage.getTimer();
		timer.addActionListener(this);
		
		Player player = new Player(stage, 100);
		player.setPosition(new Vec2(Stage.WIDTH / 2 , Stage.HEIGHT / 2));
		stage.setPlayer(player);
		stage.setHUD(new HUD(stage));
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			time += timer.getDelay();
			if ((time % TESTENEMY_SPAWNINTERVAL)==0) {
				stage.add(new TestEnemy(stage));
			}
			if ((time % ERRATICENEMY_SPAWNINTERVAL)==0) {
				stage.add(new ErraticEnemy(stage));
			}
			if ((time % SHOOTINGENEMY_SPAWNINTERVAL)==0) {
				stage.add(new ShootingEnemy(stage));
			}
			if ((time % TRACKERENEMY_SPAWNINTERVAL)==0) {
				stage.add(new TrackerEnemy(stage));
			}
			if ((time % POWERUP_SPAWNINTERVAL)==0) {
				stage.add(new HealthRestorePowerup(stage));
			}
		}
	}
}

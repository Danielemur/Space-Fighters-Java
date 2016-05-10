package com.greenteam.spacefighters.stage;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import com.greenteam.spacefighters.GUI.HUD;
import com.greenteam.spacefighters.GUI.Window;
import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.obstacle.asteroid.Asteroid;
import com.greenteam.spacefighters.entity.entityliving.powerupcontainer.*;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.*;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player.PlayerShipColor;

public class TestLevelLoader extends LevelLoader implements ActionListener {
	private static final double[] LEVEL_INTERVAL_RATIOS = {1.0, 0.9, 0.8, 0.6, 0.5};
	private static final int ASTEROID_COUNT = 50;
	private static final int ASTEROID_MAXSIZE = 50;
	private static final int ASTEROID_MINSIZE = 20;
	private static final int TESTENEMY_SPAWNINTERVAL = 800;
	private static final int ERRATICENEMY_SPAWNINTERVAL = 1040;
	private static final int SHOOTINGENEMY_SPAWNINTERVAL = 1080;
	private static final int TRACKERENEMY_SPAWNINTERVAL = 1400;
	private static final int ASTEROID_SPAWNINTERVAL = 1600;
	private static final int POWERUP_SPAWNINTERVAL = 10000; //remove this after implementing powerup spawning in Enemy
	private static final int POWERUP_TYPENUMBER = 5;
	
	private static final int LEVEL_SCORE_THRESHOLD = 1000;
	
	private Stage stage;
	private Timer timer;
	private int time;
	private int level;
	
	public TestLevelLoader(Stage s, File f) {
		super(s, f);
		stage = s;
		stage.getEntities().clear();
		timer = stage.getTimer();
		timer.addActionListener(this);
		level = 0;
		
		Player player = new Player(stage, 100, 100, PlayerShipColor.RED);
		player.setPosition(new Vec2(Stage.WIDTH / 2 , Stage.HEIGHT / 2));
		stage.setPlayer(player);
		stage.setHUD(new HUD(stage));
		for (int i = 0; i < ASTEROID_COUNT; i++) {
			int size = (int)((ASTEROID_MAXSIZE - ASTEROID_MINSIZE) * Math.random()) + ASTEROID_MINSIZE;
			stage.add(new Asteroid(stage, size));
		}
		stage.resume();
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			time += timer.getDelay();
			if ((time % (int)(TESTENEMY_SPAWNINTERVAL*LEVEL_INTERVAL_RATIOS[level])) == 0) {
				stage.add(new TestEnemy(stage));
			}
			if ((time % (int)(ERRATICENEMY_SPAWNINTERVAL*LEVEL_INTERVAL_RATIOS[level])) == 0) {
				stage.add(new ErraticEnemy(stage));
			}
			if ((time % (int)(SHOOTINGENEMY_SPAWNINTERVAL*LEVEL_INTERVAL_RATIOS[level])) == 0) {
				stage.add(new ShootingEnemy(stage));
			}
			if ((time % (int)(TRACKERENEMY_SPAWNINTERVAL*LEVEL_INTERVAL_RATIOS[level])) == 0) {
				stage.add(new TrackerEnemy(stage));
			}
			if ((time % (int)(ASTEROID_SPAWNINTERVAL*LEVEL_INTERVAL_RATIOS[level])) == 0) {
				int size = (int)((ASTEROID_MAXSIZE - ASTEROID_MINSIZE) * Math.random()) + ASTEROID_MINSIZE;
				stage.add(new Asteroid(stage, size));
			}
			if ((time % (int)(POWERUP_SPAWNINTERVAL*LEVEL_INTERVAL_RATIOS[level])) == 0) {
				switch((int)(Math.random() * POWERUP_TYPENUMBER)) {
					case 0 :
						stage.add(new HealthRestorePowerupContainer(stage));
						break;
					case 1 :
						stage.add(new ForceFieldPowerupContainer(stage));
						break;
					case 2 :
						stage.add(new HealthBoostPowerupContainer(stage));
						break;
					case 3 :
						stage.add(new ChargeBoostPowerupContainer(stage));
						break;
					case 4 :
						stage.add(new ChainBeamPowerupContainer(stage));
						break;
					default :
						stage.add(new HealthRestorePowerupContainer(stage));
						break;
				}
			}
		}
		if (stage.getPlayer().getScore() >= LEVEL_SCORE_THRESHOLD*(level+1)) {
			if (level < LEVEL_INTERVAL_RATIOS.length-1) {
				stage.pause();
				((CardLayout)stage.getParent().getLayout()).show(stage.getParent(), Window.STORESCREEN_CARDLAYOUT_NAME);
				Player p = stage.getPlayer();
				stage.getEntities().clear();
				stage.add(p);
				p.setPosition(new Vec2(Stage.WIDTH / 2 , Stage.HEIGHT / 2));
				++level;
			}
		}
	}
}
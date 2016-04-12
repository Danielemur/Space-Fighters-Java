package com.greenteam.spacefighters.stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.projectile.PlayerProjectile;
import com.greenteam.spacefighters.entity.entityliving.starship.enemy.TestEnemy;

public class TestLevelLoader extends LevelLoader implements ActionListener {
	private Timer timer1;
	private Timer timer2;
	private Stage stage;
	
	public TestLevelLoader(Stage s, File f) {
		super(s, f);
		stage = s;
		timer1 = new Timer(10, this);
		timer2 = new Timer(15, this);
		timer1.start();
		timer2.start();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer1) {
			stage.add(new TestEnemy(stage, 320, 600, 20, 60));
		}
		else if (ev.getSource() == timer2) {
			PlayerProjectile proj = new PlayerProjectile(stage, 1);
			proj.setVelocity(new Vec2(0, -100));
			proj.setPosition(new Vec2(160, 600));
			stage.add(proj);
		}
	}

}

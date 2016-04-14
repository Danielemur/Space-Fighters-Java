package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.entityliving.projectile.Projectile;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public class TrackerEnemy extends Enemy {
	private static final double ACCELERATION = 1500;
	private static final double DRAG = 5.0 / 40000.0;
	private static final int DEFAULTARMORLEVEL = 0;
	private static final int DEFAULTWEAPONRYLEVEL= 0;
	private static final int DEFAULTWEAPONRYHEALTH = 1;
	private static final int FIREDRAIN = 200;
	private static final int FULLCHARGE = 1000;
	private static final int PROJECTILESPEED = 1200;
	private static int chargeLevel;
	
	public TrackerEnemy(Stage s, int health, int armorMultiplier, int weaponryMultiplier) {
		super(s, health, armorMultiplier, weaponryMultiplier);
		this.setPosition(new Vec2((stage.getWidth()-40)*Math.random(),0));
		this.setVelocity(new Vec2(1000*Math.random()-500,200));
		this.setOrientation(new Vec2(0, -1));
		try {
			this.setTexture(ImageIO.read(this.getClass().getResource("/com/greenteam/spacefighters/assets/boss.png")));
		} catch (IOException e) {}
		chargeLevel = FULLCHARGE;
	}

	@Override
	public void update(int ms) {
		Player target = (Player) stage.getNearestEntity(this, Player.class);
		if (target != null) {
			double dist = getPosition().distance(target.getPosition()); 
	  	
			if (dist < 1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.0D)
				fire();
			double speed = getVelocity().magnitude();
			double drag = speed * speed * DRAG;
			Vec2 direction = target.getPosition().subtract(getPosition()).normalize();
			setAcceleration(direction.scale(ACCELERATION).subtract(getVelocity().scale(drag)));
			this.setOrientation(direction);
			System.out.println("" + target.getPosition().subtract(getPosition()).normalize().scale(ACCELERATION).subtract(getVelocity().scale(drag)));
		} else {
			double speed = getVelocity().magnitude();
			double drag = speed * speed * DRAG;
			setAcceleration(new Vec2(0, -1).scale(ACCELERATION).subtract(getVelocity().scale(drag)));
		}
		chargeLevel += ms;
		super.update(ms);	
	}

	@Override
	public void fire() {
		if (chargeLevel >= FIREDRAIN) {
			System.out.println("hi");
			int damage = 10 * (getWeaponryMultiplier() + 1);
			Projectile proj = new Projectile(stage, DEFAULTWEAPONRYHEALTH, damage, this.getSource());
			proj.setVelocity(this.getOrientation().scale(PROJECTILESPEED));
			proj.setPosition(this.getPosition());
			stage.add(proj);
			chargeLevel -= FIREDRAIN;
		}
	}

	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		double angle = this.getOrientation().multiply(new Vec2(1, -1)).angle();
		double imagemidx = this.getTexture().getWidth(null)/2;
		double imagemidy = this.getTexture().getHeight(null)/2;
		AffineTransform tf = AffineTransform.getRotateInstance(angle, imagemidx, imagemidy);
		AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()-imagemidx), (int)(pos.getY()-imagemidy), null);
	}

	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}
	
	@Override
	public double getRadius() {
		return 25.0D;
	}

}

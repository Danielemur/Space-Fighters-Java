package com.greenteam.spacefighters.entity.entityliving.starship.enemy;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.entity.Entity;
import com.greenteam.spacefighters.entity.entityliving.EntityLiving;
import com.greenteam.spacefighters.entity.entityliving.obstacle.Obstacle;
import com.greenteam.spacefighters.entity.entityliving.starship.Starship;
import com.greenteam.spacefighters.entity.entityliving.starship.player.Player;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Enemy extends Starship {
	protected boolean couldLoadImage;
	protected int width;
	protected int height;
	
	public Enemy(Stage s, int health, int armorMultiplier, int weaponryMultiplier) {
		super(s, health, armorMultiplier, weaponryMultiplier);
	}
	
	public abstract java.awt.Color noTextureColor();

	@Override
	public void render(Graphics g) {
		Vec2 pos = this.getPosition();
		if (couldLoadImage) {
			double angle = this.getOrientation().multiply(new Vec2(1,-1)).angle();
			double imagemidx = this.getTexture().getWidth(null)/2;
			double imagemidy = this.getTexture().getHeight(null)/2;
			AffineTransform tf = AffineTransform.getRotateInstance(angle, imagemidx, imagemidy);
			AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter((BufferedImage)this.getTexture(), null), (int)(pos.getX()-imagemidx), (int)(pos.getY()-imagemidy), null);
		}
		else {
			g.setColor(this.noTextureColor());
			g.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		}
	}

	@Override
	public void update(int ms) {
		Stage stage = this.getStage();
		if (this.getHealth() <= 0) {
			stage.getPlayer().setScore(stage.getPlayer().getScore() + this.getPointValue());
			stage.getPlayer().setMoney(stage.getPlayer().getMoney() + this.getPointValue()/10);
		}
		for (Entity e : this.getStage().getEntities()) {
			if (e == this) continue;
			if ((e.getPosition().distance(this.getPosition()) < this.getRadius() + e.getRadius()) &&
					((Obstacle.class.isAssignableFrom(e.getSource())) || ((Player.class.isAssignableFrom(e.getSource()))))) {
				this.setHealth(this.getHealth() - ((EntityLiving)e).getDamage());
			}
		}
		super.update(ms);
	}
	
	@Override
	public Class<?> getSource() {
		return Enemy.class;
	}
	
	public static BufferedImage getTexFromEnum(EnemyShipColor color) {
		try {
			switch(color) {
				case GREEN:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-0.png"));
				case BLUE:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-1.png"));
				case RED:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-2.png"));
				case BLACK:
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-3.png"));
				default :
					return ImageIO.read(Enemy.class.getResource("/com/greenteam/spacefighters/assets/enemy-0.png"));
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	public enum EnemyShipColor {
		GREEN, BLUE, RED, BLACK
	}
	
}

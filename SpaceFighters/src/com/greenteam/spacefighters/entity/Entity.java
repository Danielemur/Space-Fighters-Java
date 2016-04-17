package com.greenteam.spacefighters.entity;

import java.awt.Graphics;
import java.awt.Image;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.stage.Stage;

public abstract class Entity {
	private Vec2 position;
	private Vec2 velocity;
	private Vec2 acceleration;
	private Vec2 orientation;
	protected Image texture;
	protected Stage stage;
	
	public Entity(Stage s) {
		position = new Vec2(0, 0);
		velocity = new Vec2(0, 0);
		acceleration = new Vec2(0, 0);
		orientation = new Vec2(0, 1);
		texture = null;
		stage = s;
	}
	
	public abstract void render(Graphics g);
	
	public void update(int ms) {
		velocity = velocity.add(acceleration.scale(((double)ms)/1000));
		position = position.add(velocity.scale(((double)ms)/1000));
	}
	
	public Vec2 randSpawnPos(double minDist) {
		Stage s = this.getStage();
		Vec2 spawnPos = Vec2.random(Stage.WIDTH, Stage.HEIGHT);
		Vec2 playerPos = s.getPlayer().getPosition();
		double dist = playerPos.distance(spawnPos);
		if (dist < minDist) {
			Vec2 player2Enemy = spawnPos.subtract(playerPos);
			spawnPos = spawnPos.add(player2Enemy).scale(minDist / dist);
			int i = 0;
			while (!Stage.inStage(spawnPos)) {
				if (i < 3) {
					spawnPos = spawnPos.rotate(playerPos, Math.PI / 2);
					i++;
				} else {
					System.err.println("Enemy placement failed!");
					spawnPos = Vec2.ZERO;
					break;
				}
			}
		}
		return spawnPos;
	}

	public Vec2 getOrientation() {
		return orientation;
	}

	public void setOrientation(Vec2 orientation) {
		this.orientation = orientation;
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public Vec2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vec2 acceleration) {
		this.acceleration = acceleration;
	}
	
	public void remove() {
		stage.remove(this);
	}
	
	public Vec2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vec2 velocity) {
		this.velocity = velocity;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	protected Image getTexture() {
		return texture;
	}

	protected void setTexture(Image texture) {
		this.texture = texture;
	}
	
	public double getRadius() {
		return 5;
	}
	
	public abstract Class<?> getSource();
	
	public int getPointValue() {
		return 100; //default
	}
}

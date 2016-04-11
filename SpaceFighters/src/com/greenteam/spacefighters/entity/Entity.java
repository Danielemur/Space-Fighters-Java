package com.greenteam.spacefighters.entity;

import java.awt.Graphics;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.stage.Stage;
import com.greenteem.spacefighters.GUI.Window;

public class Entity {
	private Vec2 position;
	private Vec2 velocity;
	private Vec2 acceleration;
	private Vec2 orientation;
	protected Stage stage;
	
	public Entity() {
		position = new Vec2(0,0);
		velocity = new Vec2(0,0);
		acceleration = new Vec2(0,0);
		orientation = new Vec2(0,0);
	}
	
	public void render(Graphics g) {
		
	}
	
	public void update(int ms) {
		velocity = velocity.add(acceleration.scale(((double)ms)/1000));
		position = position.add(velocity.scale(((double)ms)/1000));
		if (velocity.magnitude2() > 0)
			setOrientation(velocity.normalize());
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
}

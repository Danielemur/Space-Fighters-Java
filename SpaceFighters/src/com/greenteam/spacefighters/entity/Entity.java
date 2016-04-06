package com.greenteam.spacefighters.entity;

import com.greenteam.spacefighters.common.Vec2;
import com.greenteam.spacefighters.renderable.Renderable;
import com.greenteem.spacefighters.GUI.Window;

public class Entity implements Renderable{
	private Vec2 position;
	private Vec2 velocity;
	private Vec2 acceleration;
	private Vec2 orientation;
	
	public void render() {
		
	}
	
	public void update() {
		velocity = velocity.add(acceleration.scale(1.0 / Window.FPS));
		position = position.add(velocity.scale(1.0 / Window.FPS));
		if (velocity.magnitude2() > 0)
			orientation = velocity.normalize();
	}
	
}

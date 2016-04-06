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
	
	public void render(Graphics g) {
		
	}
	
	public void update() {
		velocity = velocity.add(acceleration.scale(1.0 / Window.FPS));
		position = position.add(velocity.scale(1.0 / Window.FPS));
		if (velocity.magnitude2() > 0)
			orientation = velocity.normalize();
	}
	
}

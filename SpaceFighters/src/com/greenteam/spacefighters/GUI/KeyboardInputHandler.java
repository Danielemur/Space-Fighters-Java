package com.greenteam.spacefighters.GUI;

import java.awt.event.ActionEvent;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.greenteam.spacefighters.stage.Stage;

public class KeyboardInputHandler {
	private Stage stage;
	private ConcurrentHashMap<String, Boolean> keys;
	
	public KeyboardInputHandler(Stage stage) {
		this.stage = stage;
		
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed UP"),MOVE_FORWARD+PRESSED);
		stage.getActionMap().put(MOVE_FORWARD+PRESSED, new MoveActionPressed(DirectionKey.FORWARD));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed DOWN"),MOVE_BACKWARD+PRESSED);
		stage.getActionMap().put(MOVE_BACKWARD+PRESSED, new MoveActionPressed(DirectionKey.BACK));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed LEFT"),MOVE_LEFT+PRESSED);
		stage.getActionMap().put(MOVE_LEFT+PRESSED, new MoveActionPressed(DirectionKey.LEFT));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed RIGHT"),MOVE_RIGHT+PRESSED);
		stage.getActionMap().put(MOVE_RIGHT+PRESSED, new MoveActionPressed(DirectionKey.RIGHT));
		
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"),MOVE_FORWARD+RELEASED);
		stage.getActionMap().put(MOVE_FORWARD+RELEASED, new MoveActionReleased(DirectionKey.FORWARD));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"),MOVE_BACKWARD+RELEASED);
		stage.getActionMap().put(MOVE_BACKWARD+RELEASED, new MoveActionReleased(DirectionKey.BACK));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"),MOVE_LEFT+RELEASED);
		stage.getActionMap().put(MOVE_LEFT+RELEASED, new MoveActionReleased(DirectionKey.LEFT));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"),MOVE_RIGHT+RELEASED);
		stage.getActionMap().put(MOVE_RIGHT+RELEASED, new MoveActionReleased(DirectionKey.RIGHT));
		
		
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed Z"),FIRE_PRIMARY+PRESSED);
		stage.getActionMap().put(FIRE_PRIMARY+PRESSED, new FireKeyPressed(FireKey.PRIMARY));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed X"),FIRE_SECONDARY+PRESSED);
		stage.getActionMap().put(FIRE_SECONDARY+PRESSED, new FireKeyPressed(FireKey.SECONDARY));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed C"),FIRE_TERTIARY+PRESSED);
		stage.getActionMap().put(FIRE_TERTIARY+PRESSED, new FireKeyPressed(FireKey.TERTIARY));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed F"),FIRE_QUATERNARY+PRESSED);
		stage.getActionMap().put(FIRE_QUATERNARY+PRESSED, new FireKeyPressed(FireKey.QUATERNARY));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed V"),FIRE_CHAIN_BEAM+PRESSED);
		stage.getActionMap().put(FIRE_CHAIN_BEAM+PRESSED, new FireKeyPressed(FireKey.CHAINBEAM));
		
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released Z"),FIRE_PRIMARY+RELEASED);
		stage.getActionMap().put(FIRE_PRIMARY+RELEASED, new FireKeyReleased(FireKey.PRIMARY));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released X"),FIRE_SECONDARY+RELEASED);
		stage.getActionMap().put(FIRE_SECONDARY+RELEASED, new FireKeyReleased(FireKey.SECONDARY));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released C"),FIRE_TERTIARY+RELEASED);
		stage.getActionMap().put(FIRE_TERTIARY+RELEASED, new FireKeyReleased(FireKey.TERTIARY));
		stage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released F"),FIRE_QUATERNARY+RELEASED);
		stage.getActionMap().put(FIRE_QUATERNARY+RELEASED, new FireKeyReleased(FireKey.QUATERNARY));
		
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed SPACE"),PAUSE+PRESSED);
		this.getActionMap().put(PAUSE+PRESSED, new PauseAction());

	}
	
	class KeyPressedAction extends AbstractAction {
		String key;
		
		public KeyPressedAction(String key) {
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			KeyboardInputHandler.this.keys.put(key, true);
		}
	}
	
	class KeyReleasedAction extends AbstractAction {
		String key;
		
		public KeyReleasedAction(String key) {
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			KeyboardInputHandler.this.keys.put(key, false);
		}
	}
}

package com.greenteam.spacefighters.GUI;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.greenteam.spacefighters.stage.Stage;

public class KeyboardInputHandler {
	private static final String PRESSED = "pressed ";
	private static final String RELEASED = "released ";
	private static final String[] keysToListen = {
			"UP", "DOWN", "LEFT", "RIGHT", "Z", "X", "C", "V", "F", "SPACE"
	};
	
	private JComponent component;
	private Map<String, Boolean> keys;
	
	public KeyboardInputHandler(JComponent component) {
		this.component = component;
		this.keys = new ConcurrentHashMap<String, Boolean>();
		
		for (String key : keysToListen) {
			component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(PRESSED+key),PRESSED+key);
			component.getActionMap().put(PRESSED+key, new KeyPressedAction(key));
		}
		for (String key : keysToListen) {
			component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(RELEASED+key),RELEASED+key);
			component.getActionMap().put(RELEASED+key, new KeyReleasedAction(key));
		}
		for (String key : keysToListen) {
			keys.put(key, false);
		}
		System.out.println("A");
	}
	
	public Map<String, Boolean> getKeys() {
		return keys;
	}
	
	class KeyPressedAction extends AbstractAction {
		private static final long serialVersionUID = 20160517L;
		
		String key;
		
		public KeyPressedAction(String key) {
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			KeyboardInputHandler.this.keys.put(key, true);
			System.out.println("P");
		}
	}
	
	class KeyReleasedAction extends AbstractAction {
		private static final long serialVersionUID = 20160517L;
		
		String key;
		
		public KeyReleasedAction(String key) {
			this.key = key;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			KeyboardInputHandler.this.keys.put(key, false);
			System.out.println("R");
		}
	}
}

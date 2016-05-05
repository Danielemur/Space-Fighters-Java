package com.greenteam.spacefighters.common;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class UniqueQueue<T> {
	private Set<T> alreadyAdded;
	private Queue<T> objects;
	
	public UniqueQueue() {
		alreadyAdded = new TreeSet<T>();
		objects = new ArrayDeque<T>();
	}
	
	public void add(T newobject) {
		if (!alreadyAdded.contains(newobject)) {
			alreadyAdded.add(newobject);
			
		}
	}
}

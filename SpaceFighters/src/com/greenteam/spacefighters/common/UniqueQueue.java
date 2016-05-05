package com.greenteam.spacefighters.common;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class UniqueQueue<T> extends ArrayDeque<T> {
	private static final long serialVersionUID = 20160505L; //May 5, 2016
	
	private Set<T> alreadyAdded;
	
	public UniqueQueue() {
		super();
		alreadyAdded = new TreeSet<T>();
	}
	
	@Override
	public boolean add(T newobject) {
		if (!alreadyAdded.contains(newobject)) {
			alreadyAdded.add(newobject);
			return super.add(newobject);
		}
		else {
			return false;
		}
	}
	
	@Override
	public void addFirst(T newobject) {
		if (!alreadyAdded.contains(newobject)) {
			alreadyAdded.add(newobject);
			super.addFirst(newobject);
		}
	}
	
	@Override
	public void addLast(T newobject) {
		if (!alreadyAdded.contains(newobject)) {
			alreadyAdded.add(newobject);
			super.addFirst(newobject);
		}
	}
	
	@Override
	public boolean offer(T newobject) {
		return this.offerLast(newobject);
	}
	
	@Override
	public boolean offerLast(T newobject) {
		if (!alreadyAdded.contains(newobject)) {
			alreadyAdded.add(newobject);
			super.addLast(newobject);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean offerFirst(T newobject) {
		if (!alreadyAdded.contains(newobject)) {
			alreadyAdded.add(newobject);
			super.addFirst(newobject);
			return true;
		}
		else {
			return false;
		}
	}
	
	
}

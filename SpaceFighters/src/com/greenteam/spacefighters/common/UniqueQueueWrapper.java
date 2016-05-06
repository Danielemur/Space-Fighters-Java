package com.greenteam.spacefighters.common;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class UniqueQueueWrapper<T> {
	private Set<T> alreadyAdded;
	private Deque<T> objects;
	
	public UniqueQueueWrapper() {
		alreadyAdded = new TreeSet<T>();
		objects = new ArrayDeque<T>();
	}
	
	public boolean maybeAdd(T newobject) {
		if (!alreadyAdded.contains(newobject)) {
			alreadyAdded.add(newobject);
			objects.addLast(newobject);
			return true;
		}
		else {
			return false;
		}
	}
	
	public T getFirst() {
		return objects.getFirst();
	}
	
	public Queue<T> getQueue() {
		return objects;
	}
	
	@Override
	public String toString() {
		String result = "UniqueQueueWrapper: { ";
		for (Object o : objects) {
			result += o.toString()+", ";
		}
		result = result.substring(0,result.length()-2)+" }";
		return result;
	}
}

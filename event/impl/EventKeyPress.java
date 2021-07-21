package genius.event.impl;

import genius.event.Event;

public class EventKeyPress extends Event {
	private int key;

	public EventKeyPress(int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
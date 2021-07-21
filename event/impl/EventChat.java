package genius.event.impl;

import genius.event.Event;

public class EventChat extends Event {
	private String text;

	public EventChat(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
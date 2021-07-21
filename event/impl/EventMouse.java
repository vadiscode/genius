package genius.event.impl;

import genius.event.Event;

public class EventMouse extends Event {
	private int buttonID;
	private boolean mouseDown;

	public EventMouse(int buttonID, boolean mouseDown) {
		this.buttonID = buttonID;
	}

	public int getButtonID() {
		return buttonID;
	}

	public void setButtonID(int buttonID) {
		this.buttonID = buttonID;
	}

	public boolean isMouseDown() {
		return mouseDown;
	}

	public boolean isMotionEvent() {
		return buttonID == -1;
	}

	public void setMouseDown(boolean mouseDown) {
		this.mouseDown = mouseDown;
	}
}
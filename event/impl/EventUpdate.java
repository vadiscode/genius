package genius.event.impl;

import genius.event.Event;

public class EventUpdate extends Event {
	private boolean isPre;
	private float yaw;
	private float pitch;
	private double y;
	private boolean onground;
	private boolean alwaysSend;

	public EventUpdate(double y, float yaw, float pitch, boolean ground) {
		this.isPre = true;
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
		this.onground = ground;
	}

	public EventUpdate() {
		this.isPre = false;
	}

	public boolean isPre() {
		return isPre;
	}

	public boolean isPost() {
		return !isPre;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	public double getY() {
		return this.y;
	}

	public boolean isOnground() {
		return this.onground;
	}

	public boolean shouldAlwaysSend() {
		return this.alwaysSend;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setGround(boolean ground) {
		this.onground = ground;
	}

	public void setAlwaysSend(boolean alwaysSend) {
		this.alwaysSend = alwaysSend;
	}
}
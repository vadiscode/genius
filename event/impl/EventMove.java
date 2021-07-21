package genius.event.impl;

import genius.event.Event;

public class EventMove extends Event {
	private boolean isPre;
	private double x;
	private double y;
	private double z;

	public EventMove(final double x, final double y, final double z) {
		this.isPre = true;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public EventMove() {
		this.isPre = false;
	}

	public boolean isPre() {
		return isPre;
	}

	public boolean isPost() {
		return !isPre;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void setZ(final double z) {
		this.z = z;
	}
}
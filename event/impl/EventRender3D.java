package genius.event.impl;

import genius.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class EventRender3D extends Event {
	private boolean offset;
	public float partialTicks;
	private int x, y, z, ix, iy, iz;

	public EventRender3D(float partialTicks, int x, int y, int z) {
		this.partialTicks = partialTicks;
		this.x = x;
		this.y = y;
		this.z = z;
		ix = x;
		iy = y;
		iz = z;
	}

	public boolean isOffset() {
		return offset;
	}

	public void offset(int renderOffsets) {
		double ox = Minecraft.getMinecraft().getRenderManager().getRenderPosX();
		double oy = Minecraft.getMinecraft().getRenderManager().getRenderPosY();
		double oz = Minecraft.getMinecraft().getRenderManager().getRenderPosZ();
		if (renderOffsets < 0) {
			GlStateManager.translate(-ox, -oy, -oz);
			x -= ox;
			y -= oy;
			z -= oz;
			offset = true;
		} else if (renderOffsets > 0) {
			GlStateManager.translate(ox, oy, oz);
			x += ox;
			y += oy;
			z += oz;
			offset = true;
		}
	}

	public void reset() {
		x = ix;
		y = iy;
		z = iz;
		offset = false;
	}

	public float getPartialTicks() {
		return this.partialTicks;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
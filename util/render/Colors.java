package genius.util.render;

import java.awt.*;

public class Colors {
	public static int getColor(Color color) {
		return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public static int getColor(int brightness) {
		return getColor(brightness, brightness, brightness, 255);
	}

	public static int getColor(int brightness, int alpha) {
		return getColor(brightness, brightness, brightness, alpha);
	}

	public static int getColor(int red, int green, int blue) {
		return getColor(red, green, blue, 255);
	}

	public static int getColor(int red, int green, int blue, int alpha) {
		int color = 0;
		color |= alpha << 24;
		color |= red << 16;
		color |= green << 8;
		color |= blue;
		return color;
	}

	public static int createRainbowFromOffset(int speed, int offset) {
		float hue = (System.currentTimeMillis() + (long)offset) % (long)speed;
		return Color.getHSBColor(hue /= (float)speed, 0.6f, 1.0f).getRGB();
	}
}
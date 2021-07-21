package genius.management.animate;

import genius.util.AnimationUtil;

public class Opacity {
    private float opacity;
    private long lastMS;

    public Opacity(int opacity) {
        this.opacity = opacity;
        lastMS = System.currentTimeMillis();
    }

    public void interpolate(int targetOpacity) {
        opacity = (int) AnimationUtil.calculateCompensation(targetOpacity, opacity, 16,5);
    }

    public void interp(int targetOpacity, int speed) {
        long currentMS = System.currentTimeMillis();
        long delta = currentMS - lastMS;//16.66666
        lastMS = currentMS;
        opacity = (AnimationUtil.calculateCompensation(targetOpacity, opacity, delta, speed));
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
}
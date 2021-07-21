package genius.event.impl;

import genius.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D extends Event {
    private ScaledResolution resolution;

    public EventRender2D(ScaledResolution resolution) {
        this.resolution = resolution;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }
}
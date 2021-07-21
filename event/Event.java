package genius.event;

import genius.Client;

public abstract class Event {
    private boolean cancelled;

    public static void call(Event event) {
        Client.EVENT_BUS.post(event).dispatch();
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean state) {
        this.cancelled = true;
    }
}
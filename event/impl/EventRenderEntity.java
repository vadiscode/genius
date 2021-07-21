package genius.event.impl;

import genius.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity extends Event {
    private EntityLivingBase entity;
    private boolean pre;

    public EventRenderEntity(EntityLivingBase entity, boolean pre) {
        this.entity = entity;
        this.pre = pre;
    }

    public EntityLivingBase getEntity() {
        return entity;
    }

    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }
}
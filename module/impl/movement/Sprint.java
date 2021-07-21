package genius.module.impl.movement;

import genius.event.impl.EventUpdate;
import genius.module.Module;
import genius.module.data.ModuleData;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.client.Minecraft;

public class Sprint extends Module {
    public Sprint(ModuleData data) {
        super(data);
    }

    @Handler
    public void onUpdate(EventUpdate event) {
        if(!Minecraft.getMinecraft().thePlayer.onGround || Minecraft.getMinecraft().thePlayer.isSprinting() == canSprint()) return;
        Minecraft.getMinecraft().thePlayer.setSprinting(canSprint());
    }

    private boolean canSprint() { return Minecraft.getMinecraft().thePlayer.getFoodStats().getFoodLevel() > 6 && Minecraft.getMinecraft().thePlayer.onGround && (Minecraft.getMinecraft().thePlayer.moveForward > 0); }
}
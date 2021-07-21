package genius.module.impl.combat;

import genius.event.impl.EventPacket;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Setting;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AntiVelocity extends Module {
    public static String HORIZONTAL = "HORIZONTAL";
    public static String VERTICAL = "VERTICAL";

    public AntiVelocity(ModuleData data) {
        super(data);
        settings.put(HORIZONTAL, new Setting<>(HORIZONTAL, 0, "Horizontal velocity factor.", 10, 0, 100));
        settings.put(VERTICAL, new Setting<>(VERTICAL, 0, "Vertical velocity factor.", 10, 0, 100));
        this.setDisplayName("Anti Velocity");
    }

    @Handler
    public void onPacket(EventPacket event) {
        // Check for incoming packets only
        if (event.isOutgoing()) {
            if(event.getPacket() instanceof C03PacketPlayer)
            return;
        }
        // If the packet handles velocity
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                int vertical = ((Number) settings.get(VERTICAL).getValue()).intValue();
                int horizontal = ((Number) settings.get(HORIZONTAL).getValue()).intValue();
                if (vertical != 0 || horizontal != 0) {
                    packet.setMotionX(horizontal * packet.getMotionX() / 100);
                    packet.setMotionY(vertical * packet.getMotionY() / 100);
                    packet.setMotionZ(horizontal * packet.getMotionZ() / 100);
                } else {
                    event.setCancelled(true);
                }
            }
        }
        if (event.getPacket() instanceof S27PacketExplosion) {
            event.setCancelled(true);
        }
    }
}
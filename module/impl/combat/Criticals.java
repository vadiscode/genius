package genius.module.impl.combat;

import genius.event.impl.EventPacket;
import genius.event.impl.EventUpdate;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S2APacketParticles;

public class Criticals extends Module {
    private static String MODE = "MODE";
    private static String HURTTIME = "HURTTIME";
    //0.0625101D
    public Criticals(ModuleData data) {
        super(data);
        settings.put(MODE, new Setting<>(MODE, new Options("Mode", "Packet", new String[]{"Packet", "Jump", "Spoof"}), "Critical attack method."));
        settings.put(HURTTIME, new Setting<>(HURTTIME, 15, "The hurtTime tick to crit at.", 1, 0, 20));
    }

    @Handler
    public void onUpdate(EventUpdate event) {
        setSuffix(((Options) settings.get(MODE).getValue()).getSelected() + " " + ((Number) settings.get(HURTTIME).getValue()).intValue());
    }

    @Handler
    public void onPacket(EventPacket event) {
        if(event.getPacket() instanceof S2APacketParticles || event.getPacket().toString().contains("S2APacketParticles")) {
            return;
        }
        try {
            if (event.isOutgoing() && event.getPacket() instanceof C02PacketUseEntity && !(event.getPacket() instanceof S2APacketParticles) && !(event.getPacket() instanceof C0APacketAnimation)) {
                C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();
                if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && mc.thePlayer.isCollidedVertically && KillAura.allowCrits && hurtTimeCheck(packet.getEntityFromWorld(mc.theWorld))) {
                    switch (((Options) settings.get(MODE).getValue()).getSelected()) {
                        case "Packet":
                            doCrits();
                            break;
                        case "Jump":
                            if(!mc.thePlayer.isJumping)
                                mc.thePlayer.jump();
                            break;
                        case "Spoof":
                            if (event.getPacket() instanceof C03PacketPlayer && this.mc.thePlayer.onGround) {
                                ((C03PacketPlayer)event.getPacket()).onGround = false;
                            }
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    private boolean hurtTimeCheck(Entity entity) {
        return entity != null && entity.hurtResistantTime <= ((Number) settings.get(HURTTIME).getValue()).intValue();
    }

    static void doCrits() {
        for (double offset : new double[]{0.06, 0, 0.03, 0})
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
    }

}
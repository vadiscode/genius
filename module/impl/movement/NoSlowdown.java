package genius.module.impl.movement;

import genius.event.impl.EventUpdate;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdown extends Module {
    private String MODE = "MODE";

    public NoSlowdown(ModuleData data) {
        super(data);
        settings.put(MODE, new Setting<>(MODE, new Options("Mode", "NCP", new String[]{"Vanilla", "NCP"}), "Bypass method."));
        this.setDisplayName("No Slowdown");
    }

    @Handler
    public void onUpdate(EventUpdate event) {
        setSuffix(((Options) settings.get(MODE).getValue()).getSelected());
        switch (((Options) settings.get(MODE).getValue()).getSelected()) {
            case "NCP":
                if (mc.thePlayer.isBlocking() && isMoving()) {
                    if (event.isPre()) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                    else if (event.isPost()) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                    }
                }
                break;
        }
    }

    private boolean isMoving() {
        if ((!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
            return ((mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
        }
        return false;
    }
}

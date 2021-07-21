package genius.module.impl.movement;

import genius.event.impl.EventMove;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Setting;
import genius.util.Timer;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module {
    private Timer timer = new Timer();
    private boolean saveMe;
    private String VOID = "VOID";
    private String DISTANCE = "DIST";

    public AntiVoid(ModuleData data) {
        super(data);
        settings.put(VOID, new Setting<>(VOID, true, "Only catch when falling into void."));
        settings.put(DISTANCE, new Setting<>(DISTANCE, 5, "The fall distance needed to catch.", 1, 4, 10));
        this.setDisplayName("Anti Void");
    }

    @Handler
    public void onMove(EventMove event) {
        if((saveMe && timer.hasReached(150)) || mc.thePlayer.isCollidedVertically) {
            saveMe = false;
            timer.reset();
        }
        int dist = ((Number)settings.get(DISTANCE).getValue()).intValue();
        if (mc.thePlayer.fallDistance > dist) {
            if (!((Boolean) settings.get(VOID).getValue()) || !isBlockUnder()) {
                if(!saveMe) {
                    saveMe = true;
                    timer.reset();
                }
                mc.thePlayer.fallDistance = 0;
                event.setY(mc.thePlayer.motionY = 0);
            }
        }
    }

    private boolean isBlockUnder() {
        for (int i = (int) (mc.thePlayer.posY - 1); i > 0; i--) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
}
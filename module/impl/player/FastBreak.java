package genius.module.impl.player;

import genius.event.impl.EventUpdate;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FastBreak extends Module {
    private String DELAY = "DELAY";
    private String MODE = "MODE";

    public FastBreak(ModuleData data) {
        super(data);
        settings.put(DELAY, new Setting<>(DELAY, 0.10, "Delay.", 0.05, 0.00, 1.00));
        settings.put(MODE, new Setting<>(MODE, new Options("Mode", "Normal", new String[]{"Haste 1", "Haste 2", "Normal"}), "Fast breaking method."));
        this.setDisplayName("Fast Break");
    }

    @Handler
    public void onUpdate(EventUpdate event) {
        setSuffix(((Options) settings.get(MODE).getValue()).getSelected());
        switch (((Options) settings.get(MODE).getValue()).getSelected()) {
            case "Normal":
                this.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
                int delay = (int) ((Number) settings.get(DELAY).getValue()).floatValue();
                if(Minecraft.getMinecraft().playerController.curBlockDamageMP < delay) {
                    Minecraft.getMinecraft().playerController.curBlockDamageMP = 1;
                }
                Minecraft.getMinecraft().playerController.blockHitDelay = 0;
                break;
            case "Haste 1":
                this.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 5200, 0));
                break;
            case "Haste 2":
                this.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 5200, 1));
                break;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
    }
}

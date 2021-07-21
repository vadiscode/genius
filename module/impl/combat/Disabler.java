package genius.module.impl.combat;

import genius.event.impl.EventUpdate;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import genius.util.Timer;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Disabler extends Module {
    private Timer timer = new Timer();
    private String MODE = "MODE";

    public Disabler(ModuleData data) {
        super(data);
        settings.put(MODE, new Setting<>(MODE, new Options("AntiCheat", "Viper", new String[]{"Rainbow", "Viper"}), "Disabler mode."));
    }

    @Handler
    public void onUpdate (EventUpdate event) {
        setSuffix(((Options) settings.get(MODE).getValue()).getSelected());
        switch (((Options) settings.get(MODE).getValue()).getSelected()) {
            case "Viper":
                if (this.timer.hasReached(3000)) {
                    event.setY(event.getY() + 0.42);
                    return;
                }
                for (int i = 0; i < 10; ++i) {
                    boolean i2 = i > 2 && i < 8;
                    double x = i2 ? 0.2 : -226.0;
                    C03PacketPlayer.C04PacketPlayerPosition packet = new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + x, this.mc.thePlayer.posZ, true);
                    this.mc.thePlayer.sendQueue.addToSendQueue(packet);
                }
                break;
        }
    }
}

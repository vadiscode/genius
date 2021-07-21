package genius.module.impl.render;

import genius.event.impl.EventTick;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import me.hippo.api.lwjeb.annotation.Handler;

public class Animations extends Module {
    public static String BLOCKMODE = "BLOCKMODE";
    public Animations(ModuleData data) {
        super(data);
        settings.put(BLOCKMODE, new Setting<>(BLOCKMODE, new Options("Block Style", "Slide", new String[]{"Tap", "Genius", "Slide"}), "Blocking style."));
    }

    @Handler
    public void onTick(EventTick event) {
        setSuffix(((Options) settings.get(BLOCKMODE).getValue()).getSelected());
    }
}

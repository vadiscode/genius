package genius.module.impl.render;

import genius.event.impl.EventTick;
import genius.module.Module;
import genius.module.data.ModuleData;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Brightness extends Module {
	public Brightness(ModuleData data) {
		super(data);
	}

	@Handler
	public void onTick(EventTick event) {
		mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5200, 1));
	}

	@Override
	public void onDisable() {
		super.onDisable();
		this.mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
	}
}
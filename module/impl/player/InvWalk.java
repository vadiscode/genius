package genius.module.impl.player;

import genius.event.impl.EventTick;
import genius.module.Module;
import genius.module.data.ModuleData;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

public class InvWalk extends Module {
	public InvWalk(ModuleData data) {
		super(data);
		setDisplayName("Inv Walk");
	}

	@Handler
	public void onTick(EventTick event) {
		if (mc.currentScreen instanceof GuiChat) {
			return;
		}
		if (mc.currentScreen != null) {
			if (Keyboard.isKeyDown(200)) {
				mc.thePlayer.rotationPitch -= 1;
			}
			if (Keyboard.isKeyDown(208)) {
				mc.thePlayer.rotationPitch += 1;
			}
			if (Keyboard.isKeyDown(203)) {
				mc.thePlayer.rotationYaw -= 3;
			}
			if (Keyboard.isKeyDown(205)) {
				mc.thePlayer.rotationYaw += 3;
			}
		}
	}
}
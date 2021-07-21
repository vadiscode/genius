package genius.module.impl.render;

import genius.Client;
import genius.event.impl.EventRender2D;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Setting;
import genius.util.RenderingUtil;
import genius.util.render.Colors;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Crosshair extends Module {
	private String GAP = "GAP";
	private String WIDTH = "WIDTH";
	private String SIZE = "SIZE";
	private String DYNAMIC = "DYNAMIC";


	public Crosshair(ModuleData data) {
		super(data);
		settings.put(DYNAMIC, new Setting<>(DYNAMIC, true, "Expands when moving."));
		settings.put(GAP, new Setting<>(GAP, 5,"Crosshair Gap", 0.25, 0.25, 15));
		settings.put(WIDTH, new Setting<>(WIDTH, 2,"Crosshair Width", 0.25, 0.25, 10));
		settings.put(SIZE, new Setting<>(SIZE, 7,"Crosshair Size/Length", 0.25, 0.25, 15));
	}

	@Handler
	public void onRender2D(EventRender2D event) {
		int red = Client.colorManager.getCrosshairColor().getRed();
		int green = Client.colorManager.getCrosshairColor().getGreen();
		int blue = Client.colorManager.getCrosshairColor().getBlue();
		int alph = Client.colorManager.getCrosshairColor().getAlpha();
		double gap = ((Number)settings.get(GAP).getValue()).doubleValue();
		double width = ((Number)settings.get(WIDTH).getValue()).doubleValue();
		double size = ((Number)settings.get(SIZE).getValue()).doubleValue();

		ScaledResolution scaledRes = new ScaledResolution(mc);
		RenderingUtil.rectangleBordered(
				scaledRes.getScaledWidth() / 2 - width,
				scaledRes.getScaledHeight() / 2 - gap - size  - (isMoving() ? 2 : 0) ,
				scaledRes.getScaledWidth() / 2 + 1.0f + width,
				scaledRes.getScaledHeight() / 2 - gap - (isMoving() ? 2 : 0) ,
				0.5f, Colors.getColor(red,green,blue,alph),
				new Color(0, 0, 0,alph).getRGB());
		RenderingUtil.rectangleBordered(
				scaledRes.getScaledWidth() / 2 - width,
				scaledRes.getScaledHeight() / 2 + gap + 1 + (isMoving() ? 2 : 0) - 0.15,
				scaledRes.getScaledWidth() / 2 + 1.0f + width,
				scaledRes.getScaledHeight() / 2 + 1 + gap + size + (isMoving() ? 2 : 0) - 0.15, 0.5f, Colors.getColor(red,green,blue,alph),
				new Color(0, 0, 0,alph).getRGB());
		RenderingUtil.rectangleBordered(
				scaledRes.getScaledWidth() / 2 - gap - size - (isMoving() ? 2 : 0) + 0.15,
				scaledRes.getScaledHeight() / 2 - width,
				scaledRes.getScaledWidth() / 2 - gap - (isMoving() ? 2 : 0) + 0.15,
				scaledRes.getScaledHeight() / 2 + 1.0f + width, 0.5f, Colors.getColor(red,green,blue,alph) ,
				new Color(0, 0, 0,alph).getRGB());
		RenderingUtil.rectangleBordered(
				scaledRes.getScaledWidth() / 2 + 1 + gap + (isMoving() ? 2 : 0),
				scaledRes.getScaledHeight() / 2 - width,
				scaledRes.getScaledWidth() / 2 + size + gap + 1 + (isMoving() ? 2 : 0),
				scaledRes.getScaledHeight() / 2 + 1.0f +  width, 0.5f, Colors.getColor(red,green,blue,alph),
				new Color(0, 0, 0,alph).getRGB());
	}

	public boolean isMoving() {
		return (Boolean) settings.get(DYNAMIC).getValue() && (!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking()) && ((mc.thePlayer.movementInput.moveForward != 0.0F) || (mc.thePlayer.movementInput.moveStrafe != 0.0F));
	}
}
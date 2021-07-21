package genius.module.impl.render;

import genius.Client;
import genius.management.ColorManager;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import genius.util.RenderingUtil;
import genius.util.render.Colors;
import me.hippo.api.lwjeb.annotation.Handler;
import genius.event.impl.EventRender2D;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hud extends Module {
    private String TIME = "TIME";
    private String POTIONS = "POTIONS";
    private String COORDINATES = "COORDINATES";
    private String FPS = "FPS";
    private String ARRAYLISTCOLOR = "ARRAYLISTCOLOR";

    public Hud(ModuleData data) {
        super(data);
        settings.put(TIME, new Setting<>(TIME, true, "Show time after client watermark."));
        settings.put(POTIONS, new Setting<>(POTIONS, true, "Show potion status."));
        settings.put(COORDINATES, new Setting<>(COORDINATES, true, "Show your coordinates (in xyz)."));
        settings.put(FPS, new Setting<>(FPS, true, "Show frame per seconds"));
        settings.put(ARRAYLISTCOLOR, new Setting<>(ARRAYLISTCOLOR, new Options("Arraylist color", "Category", new String[]{"Custom", "Rainbow", "Category"}), "Arraylist color mode."));
    }

    @Handler
    public void onRender2D(EventRender2D event) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        if (time.startsWith("0")) {
            time = time.replaceFirst("0", "");
        }
        Client.fontManager.hud.drawStringWithShadow("G", 4, 2, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
        Client.fontManager.hud.drawStringWithShadow("enius", 4 + Client.fontManager.hud.getStringWidth("G"), 2, Colors.getColor(255, 255, 255));
        if (((Boolean) settings.get(TIME).getValue())) {
            Client.fontManager.hud.drawStringWithShadow("(", 4 + Client.fontManager.hud.getStringWidth("Genius "), 2, Colors.getColor(170, 170, 170,170));
            Client.fontManager.hud.drawStringWithShadow(time, 3.5 + Client.fontManager.hud.getStringWidth("Genius ("), 2, Colors.getColor(255, 255, 255));
            Client.fontManager.hud.drawStringWithShadow(")", 4 + Client.fontManager.hud.getStringWidth("Genius (" + time), 2, Colors.getColor(170, 170, 170, 170));
        }
        if ((((Boolean) settings.get(POTIONS).getValue()))) {
            drawPotionStatus(event.getResolution());
        }
        String coordinates = (int) mc.thePlayer.posX + " " + (int) mc.thePlayer.posY + " " + (int) mc.thePlayer.posZ;
        float pY = (mc.currentScreen != null && mc.currentScreen instanceof GuiChat) ? event.getResolution().getScaledHeight() - (11 * 2) - 2 : event.getResolution().getScaledHeight() - 11;
        float fY = (mc.currentScreen != null && mc.currentScreen instanceof GuiChat) ? event.getResolution().getScaledHeight() - (11 * 2) - 2 : event.getResolution().getScaledHeight() - (11 * 2) - 2;
        float fX = (mc.currentScreen != null && mc.currentScreen instanceof GuiChat) ? 12 + Client.fontManager.hud.getStringWidth("XYZ:" + coordinates) : 4;
        if ((((Boolean) settings.get(COORDINATES).getValue()))) {
            Client.fontManager.hud.drawStringWithShadow("XYZ", 4, pY, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
            Client.fontManager.hud.drawStringWithShadow(":", Client.fontManager.hud.getStringWidth("XYZ") + 4, pY, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
            Client.fontManager.hud.drawStringWithShadow(coordinates, Client.fontManager.hud.getStringWidth("XYZ:") + 8, pY, -1);
        }
        if ((((Boolean) settings.get(FPS).getValue()))) {
            if (!(((Boolean) settings.get(COORDINATES).getValue()))) {
                Client.fontManager.hud.drawStringWithShadow("FPS", 4, pY, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
                Client.fontManager.hud.drawStringWithShadow(":", Client.fontManager.hud.getStringWidth("FPS") + 2, pY, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
                Client.fontManager.hud.drawStringWithShadow(mc.getDebugFPS() + "", Client.fontManager.hud.getStringWidth("FPS:") + 8, pY, -1);
            } else {
                Client.fontManager.hud.drawStringWithShadow("FPS", fX, fY, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
                Client.fontManager.hud.drawStringWithShadow(":", Client.fontManager.hud.getStringWidth("FPS") + 2 + fX, fY, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
                Client.fontManager.hud.drawStringWithShadow(mc.getDebugFPS() + "", Client.fontManager.hud.getStringWidth("FPS:") + 6 + fX, fY, -1);
            }
        }
        int y = 4;
        List<Module> modules = new CopyOnWriteArrayList<>();
        for (Module module : Client.getModuleManager().getArray()) {
            if (module.isEnabled() && !module.isHidden()) {
                modules.add(module);
            }
        }
        modules.sort(Comparator.comparingDouble(m -> -Client.fontManager.hud.getStringWidth(m.getSuffix() != null ? m.getDisplayName() + " " + m.getSuffix() : m.getDisplayName())));
        for (Module module : modules) {
            String suffix = module.getSuffix() != null ? " " + module.getSuffix() : "";
            float x = event.getResolution().getScaledWidth() - Client.fontManager.hud.getStringWidth(module.getDisplayName() + suffix) - 2;
            RenderingUtil.rectangle(x - 2.5, y - 4.3, event.getResolution().getScaledWidth(), y + 5.5, Colors.getColor(0, 50));
            switch (((Options) settings.get(ARRAYLISTCOLOR).getValue()).getSelected()) {
                case "Category":
                    Client.fontManager.hud.drawStringWithShadow(module.getDisplayName(), x, y - 4, Module.getColor(module.getType()));
                    RenderingUtil.rectangle(event.getResolution().getScaledWidth() - 1, y - 4.3, event.getResolution().getScaledWidth(), y + 5.5, Module.getColor(module.getType()));
                    break;
                case "Custom":
                    Client.fontManager.hud.drawStringWithShadow(module.getDisplayName(), x, y - 4, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
                    RenderingUtil.rectangle(event.getResolution().getScaledWidth() - 1, y - 4.3, event.getResolution().getScaledWidth(), y + 5.5, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue));
                    break;
                case "Rainbow":
                    Client.fontManager.hud.drawStringWithShadow(module.getDisplayName(), x, y - 4, rainbow(1));
                    RenderingUtil.rectangle(event.getResolution().getScaledWidth() - 1, y - 4.3, event.getResolution().getScaledWidth(), y + 5.5, rainbow(1));
                    break;
            }
            if (!Objects.equals(suffix, "")) {
                Client.fontManager.hud.drawStringWithShadow(suffix, x + Client.fontManager.hud.getStringWidth(module.getDisplayName()), y - 4, Colors.getColor(Colors.getColor(150)));
            }
            y += 10;
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((double)((System.currentTimeMillis() + delay) / 4L));
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 0.1f, 3.0f).getRGB();
    }

    private static void drawPotionStatus(ScaledResolution sr)
    {
        List<PotionEffect> potions = new ArrayList<>();
        for(Object o : mc.thePlayer.getActivePotionEffects())
            potions.add((PotionEffect)o);
        potions.sort(Comparator.comparingDouble(effect -> -Client.fontManager.hud.getStringWidth(I18n.format((Potion.potionTypes[effect.getPotionID()]).getName()))));

        float pY = (mc.currentScreen != null && mc.currentScreen instanceof GuiChat) ? -15 : -2;
        for (PotionEffect effect : potions) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String name = I18n.format(potion.getName());
            String PType = "";
            if (effect.getAmplifier() == 1) {
                name = name + " II";
            } else if (effect.getAmplifier() == 2) {
                name = name + " III";
            } else if (effect.getAmplifier() == 3) {
                name = name + " IV";
            }
            if ((effect.getDuration() < 600) && (effect.getDuration() > 300)) {
                PType = PType + ":\2476 " + Potion.getDurationString(effect);
            } else if (effect.getDuration() < 300) {
                PType = PType + ":\247c " + Potion.getDurationString(effect);
            } else if (effect.getDuration() > 600) {
                PType = PType + ":\2477 " + Potion.getDurationString(effect);
            }
            Client.fontManager.hud.drawStringWithShadow(name,
                    sr.getScaledWidth() - Client.fontManager.hud.getStringWidth(name + PType) - 2,
                    sr.getScaledHeight() - 9 + pY, potion.getLiquidColor());
            Client.fontManager.hud.drawStringWithShadow(PType,
                    sr.getScaledWidth() - Client.fontManager.hud.getStringWidth(PType) - 2,
                    sr.getScaledHeight() - 9 + pY, -1);
            pY -= 9;
        }
    }
}
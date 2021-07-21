package genius.module.impl.render;

import genius.Client;
import genius.event.impl.EventKeyPress;
import genius.event.impl.EventRender2D;
import genius.event.impl.EventTick;
import genius.management.ColorManager;
import genius.management.animate.Opacity;
import genius.management.animate.Translate;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import genius.util.MathUtils;
import genius.util.RenderingUtil;
import genius.util.StringConversions;
import genius.util.Timer;
import genius.util.render.Colors;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TabGUI extends Module {
    private int selectedTypeX, moduleBoxY, currentSetting,
            settingBoxX, categoryBoxY, categoryBoxX, currentCategory, targetY, targetModY, targetSetY, moduleBoxX,
            targetX, targetSetX;
    private boolean inModules, inModSet, inSet;
    private Module selectedModule;
    private Timer timer = new Timer();
    public static int opacity = 45;
    private int targetOpacity = 45;
    private boolean isActive;

    private final String ICONS = "ICONS";
    private final String TOOLTIPS = "TOOLTIPS";

    public TabGUI(ModuleData data) {
        super(data);
        settings.put(ICONS, new Setting<>(ICONS, true, "Shows category icons on ui."));
        settings.put(TOOLTIPS, new Setting<>(TOOLTIPS, true, "Shows descriptions of features."));
        setHidden(true);
    }

    private Translate selectedType = new Translate(0, 14);
    private Translate selectedModuleT = new Translate(0, 14);
    private Translate selectedSettingT = new Translate(0, 14);
    private Opacity opacityAnimation = new Opacity(0);

    @Override
    public void onEnable() {
        targetY = 12;
        categoryBoxY = 0;
        currentCategory = 0;
        inModules = false;
        inModSet = false;
        inSet = false;
    }
    
    @Handler
    public void onRender2D(EventRender2D event) {
        opacityAnimation.interp(100, 10);
        if (timer.hasReached(4000)) {
            targetOpacity = 100;
            isActive = false;
        }
        int diff3 = (targetX) - (moduleBoxX);
        int diff5 = (targetSetX) - settingBoxX;
        int opacityDiff = (targetOpacity) - (opacity);
        opacity += opacityDiff * 0.1;

        selectedType.interpolate(selectedTypeX, targetY, (Math.abs(selectedType.getY() - targetY) > 12) ? 12 : 4);
        selectedModuleT.interpolate(categoryBoxX + 3, targetModY, (Math.abs(selectedModuleT.getY() - targetModY) > 12) ? 12 : 4);
        selectedSettingT.interpolate(0, targetSetY, (Math.abs(selectedSettingT.getY() - targetSetY) > 12) ? 12 : 4);
        moduleBoxX += MathUtils.roundToPlace(diff3 * 0.25, 0);
        if (diff3 == 1) {
            moduleBoxX++;
        } else if (diff3 == -1) {
            moduleBoxX--;
        }
        settingBoxX += MathUtils.roundToPlace(diff5 * 0.25, 0);
        if (diff5 == 1) {
            settingBoxX++;
        } else if (diff5 == -1) {
            settingBoxX--;
        }


        RenderingUtil.rectangle(2, 14, categoryBoxX + 20, categoryBoxY + 1, Colors.getColor(0, (int) opacityAnimation.getOpacity()));
        RenderingUtil.rectangle(selectedTypeX + 0.3, selectedType.getY() + 0.3, categoryBoxX + 19.5, selectedType.getY() + 11.5,
                Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, opacity + 64));
        int y = 15;
        for (ModuleData.Type type : ModuleData.Type.values()) {
            boolean isSelected = Math.abs(y - selectedType.getY()) < 6 || y - selectedType.getY() == 6;
            // Client.cf.drawString(type.name(), isSelected ? 7 : 5, y + 1,
            // Colors.getColor, 200));
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            Client.fontManager.hud.drawStringWithShadow(type.name(), isSelected ? 6 : 4, y,
                    Colors.getColor(255, 255, 255, opacity + 64));
            if((Boolean)settings.get(ICONS).getValue()) {
                if (type.name().equalsIgnoreCase("Combat")) {
                    Client.fontManager.badCacheSmall.drawCenteredString("E", categoryBoxX + 15, (float) (y + 4.7), Colors.getColor(255, 255, 255, opacity + 64));
                } else if (type.name().equalsIgnoreCase("Player")) {
                    Client.fontManager.badCacheSmall.drawCenteredString("F", categoryBoxX + 15, (float) (y + 4.7), Colors.getColor(255, 255, 255, opacity + 64));
                } else if (type.name().equalsIgnoreCase("Movement")) {
                    Client.fontManager.badCacheSmall.drawCenteredString("J", categoryBoxX + 15, (float) (y + 4.7), Colors.getColor(255, 255, 255, opacity + 64));
                } else if (type.name().equalsIgnoreCase("Render")) {
                    Client.fontManager.badCacheSmall.drawCenteredString("C", categoryBoxX + 15, (float) (y + 4.7), Colors.getColor(255, 255, 255, opacity + 64));
                } else if (type.name().equalsIgnoreCase("Misc")) {
                    Client.fontManager.badCacheSmall.drawCenteredString("I", categoryBoxX + 15, (float) (y + 4.7), Colors.getColor(255, 255, 255, opacity + 64));
                }
            }
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            y += 12;
        }
        if (inModules) {
            List<Module> xd = getModules(ModuleData.Type.values()[currentCategory]);
            y = 15;
            RenderingUtil.rectangle(categoryBoxX + 26, 14, moduleBoxX + 20, ((xd.size()) * 12) + 14, Colors.getColor(0, opacity));

            if (diff3 == 0 && moduleBoxX > categoryBoxX + 6) {
                RenderingUtil.rectangle(categoryBoxX + 26.3, selectedModuleT.getY() + 0.3, moduleBoxX + 19.3, selectedModuleT.getY() + 11.6,
                        Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, opacity + 64));
                for (Module mod : getModules(ModuleData.Type.values()[currentCategory])) {
                    if (!(getSettings(mod) == null) && !(selectedModule == mod)) {
                        RenderingUtil.rectangle(moduleBoxX + 19.5, y - 0.5, moduleBoxX + 18.3, y + 10.5,
                                Colors.getColor(255, opacity + 64));
                    }
                    boolean isSelected = Math.abs(y - selectedModuleT.getY()) < 6 || y - selectedModuleT.getY() == 6;
                    if((Boolean)settings.get(TOOLTIPS).getValue() && !inModSet) {
                        if(isSelected) {
                            float width = Client.fontManager.hud.getStringWidth(mod.getDescription());
                            RenderingUtil.rectangle(moduleBoxX + 22, selectedModuleT.getY(), moduleBoxX + width + 24, selectedModuleT.getY() + 12,
                                    Colors.getColor(0, opacity));
                            Client.fontManager.hud.drawStringWithShadow(mod.getDescription(), moduleBoxX + 24, selectedModuleT.getY() + 1.5, Colors.getColor(255, opacity + 64));
                        }
                    }
                    // Client.cf.drawString(mod.getName(), categoryBoxX +
                    // (isSelected ? 9 : 7), y + 1,Colors.getColor,
                    // 200));
                    GlStateManager.pushMatrix();
                    GlStateManager.enableAlpha();
                    Client.fontManager.hud.drawStringWithShadow(mod.getName(), categoryBoxX + (isSelected ? 30 : 28), y,
                            mod.isEnabled() ? Colors.getColor(255, opacity + 64)
                                    : Colors.getColor(175, opacity + 64));
                    GlStateManager.disableAlpha();
                    GlStateManager.popMatrix();
                    y += 12;
                }
            }
        }
        if (inModSet) {
            RenderingUtil.rectangle(moduleBoxX + 24, 14, settingBoxX, 14 + getSettings(selectedModule).size() * 12,
                    Colors.getColor(0, opacity));

            if (inSet) {
                RenderingUtil.rectangleBordered(settingBoxX + 1, selectedSettingT.getY(), settingBoxX + 3.5f, selectedSettingT.getY() + 12, 0.5f,
                        Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, opacity + 64), Colors.getColor(0, opacity));
            }
            int y1 = 15;
            try {
                RenderingUtil.rectangle(moduleBoxX + 24.3, selectedSettingT.getY() + 0.3, settingBoxX - 0.3, selectedSettingT.getY() + 11.3,
                        Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, opacity + 64));
                for (Setting setting : getSettings(selectedModule)) {
                    if (setting != null && diff5 == 0 && settingBoxX > moduleBoxX + 4) {
                        boolean isSelected = Math.abs(y1 - selectedSettingT.getY()) < 6 || y1 - selectedSettingT.getY() == 6;
                        if((Boolean)settings.get(TOOLTIPS).getValue() && !inSet) {
                            if(isSelected) {
                                float width = Client.fontManager.hud.getStringWidth(setting.getDesc());
                                RenderingUtil.rectangle(settingBoxX + 2, selectedSettingT.getY(), settingBoxX + width + 4, selectedSettingT.getY() + 12,
                                        Colors.getColor(0, opacity));
                                Client.fontManager.hud.drawStringWithShadow(setting.getDesc(), settingBoxX + 4.5, selectedSettingT.getY() + 1.5, Colors.getColor(255));
                            }
                        }
                        String xd = setting.getName().charAt(0) + setting.getName().toLowerCase().substring(1);
                        GlStateManager.pushMatrix();
                        String fagniger = setting.getValue() instanceof Options ? ((Options) setting.getValue()).getSelected() : setting.getValue().toString();
                        Client.fontManager.hud.drawStringWithShadow(xd + ":" + " " + fagniger, moduleBoxX + (isSelected ? 28 : 26), y1, Colors.getColor(255, opacity + 64));
                        GlStateManager.popMatrix();
                        // Client.cf.drawString(xd + " " +
                        // setting.getValue(), moduleBoxX + 3, y1, -1);
                        y1 += 12;
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    @Handler
    public void onTick(EventTick event) {
        if (categoryBoxY == 0) {
            int y = 13;
            int largestString = -1;
            for (ModuleData.Type type : ModuleData.Type.values()) {
                y += 12;
                if (Client.fontManager.hud.getStringWidth(type.name()) > largestString) {
                    largestString = Client.fontManager.hud.getStringWidth(type.name());
                }
            }
            categoryBoxY = y;
            categoryBoxX = 53;
            selectedTypeX = 2;
            targetY = 14;
        }
    }

    @Handler
    public void onKeyPress(EventKeyPress event) {
        if (!isActive && this.keyCheck(event.getKey())) {
            isActive = true;
            targetOpacity = 200;
            timer.reset();
        }
        if (isActive && this.keyCheck(event.getKey())) {
            timer.reset();
        }
        if (!inModules) {
            if (event.getKey() == Keyboard.KEY_DOWN) {
                targetY += 12;
                currentCategory++;
                if (currentCategory > ModuleData.Type.values().length - 1) {
                    targetY = 14;
                    currentCategory = 0;
                }
            } else if (event.getKey() == Keyboard.KEY_UP) {
                targetY -= 12;
                currentCategory--;
                if (currentCategory < 0) {
                    targetY = categoryBoxY - 11;
                    currentCategory = ModuleData.Type.values().length - 1;
                }
            } else if (event.getKey() == Keyboard.KEY_RIGHT) {
                inModules = true;
                moduleBoxY = 0;
                selectedModuleT.setY(14);
                targetModY = 14;
                int longestString = 0;
                for (Module modxd : Client.getModuleManager().getArray()) {
                    if (modxd.getType() == ModuleData.Type.values()[currentCategory]) {
                        if (longestString < Client.fontManager.hud.getStringWidth(modxd.getName())) {
                            longestString = (int) Client.fontManager.hud.getStringWidth(modxd.getName());
                        }
                    }
                }
                targetX = categoryBoxX + 5 + longestString + 15;
                moduleBoxX = categoryBoxX + 5;
            }
        } else if (!inModSet) {
            if (event.getKey() == Keyboard.KEY_LEFT) {
                targetX = categoryBoxX + 6;
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(110);
                    } catch (InterruptedException e) {
                    }
                    inModules = false;
                });
                thread.start();
            }
            if (event.getKey() == Keyboard.KEY_DOWN) {
                targetModY += 12;
                moduleBoxY++;
                if (moduleBoxY > getModules(ModuleData.Type.values()[currentCategory]).size() - 1) {
                    targetModY = 14;
                    moduleBoxY = 0;
                }
            } else if (event.getKey() == Keyboard.KEY_UP) {
                targetModY -= 12;
                moduleBoxY--;
                if (moduleBoxY < 0) {
                    targetModY = (((getModules(ModuleData.Type.values()[currentCategory]).size() - 1) * 12) + 14);
                    moduleBoxY = getModules(ModuleData.Type.values()[currentCategory]).size() - 1;
                }
            } else if (event.getKey() == Keyboard.KEY_RETURN) {
                Module mod = getModules(ModuleData.Type.values()[currentCategory]).get(moduleBoxY);
                if (!mod.getName().contains("TabGUI")) {
                    mod.toggle();
                }
            } else if (event.getKey() == Keyboard.KEY_RIGHT) {
                selectedModule = getModules(ModuleData.Type.values()[currentCategory]).get(moduleBoxY);

                if (!(getSettings(selectedModule) == null)) {
                    inModSet = true;
                    selectedSettingT.setY(14);
                    targetSetY = 14;
                    currentSetting = 0;
                    int longestString = 0;
                    for (Setting modxd : getSettings(selectedModule)) {
                        String faggotXD = modxd.getValue() instanceof Options ? ((Options) modxd.getValue()).getSelected() + "XD" : modxd.getValue().toString();
                        if (longestString < Client.fontManager.hud.getStringWidth(modxd.getName() + " " + faggotXD)) {
                            longestString = (int) Client.fontManager.hud.getStringWidth(modxd.getName() + " " + faggotXD);
                        }
                    }
                    targetSetX = moduleBoxX + longestString + 16;
                    settingBoxX = moduleBoxX + 4;
                }
            }
        } else if (!inSet) {
            if (event.getKey() == Keyboard.KEY_LEFT) {
                targetSetX = moduleBoxX + 4;
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(110);
                    } catch (InterruptedException e) {
                    }
                    inModSet = false;
                    selectedModule = null;
                });
                thread.start();
            } else if (event.getKey() == Keyboard.KEY_DOWN) {
                targetSetY += 12;
                currentSetting++;
                if (currentSetting > getSettings(selectedModule).size() - 1) {
                    currentSetting = 0;
                    targetSetY = 14;
                }
            } else if (event.getKey() == Keyboard.KEY_UP) {
                targetSetY -= 12;
                currentSetting--;
                if (currentSetting < 0) {
                    targetSetY = ((getSettings(selectedModule).size() - 1) * 12) + 14;
                    currentSetting = getSettings(selectedModule).size() - 1;
                }
            } else if (event.getKey() == Keyboard.KEY_RIGHT) {
                inSet = true;
            }
        } else if (inSet) {
            if (event.getKey() == Keyboard.KEY_LEFT) {
                inSet = !inSet;
            } else if (event.getKey() == Keyboard.KEY_UP) {
                Setting set = getSettings(selectedModule).get(currentSetting);
                if (set.getValue() instanceof Number) {
                    double increment = (set.getInc());
                    String str = MathUtils.isInteger(MathUtils.getIncremental((((Number) (set.getValue())).doubleValue() + increment), increment)) ?
                            (MathUtils.getIncremental((((Number) (set.getValue())).doubleValue() + increment), increment) + "").replace(".0", "") : MathUtils.getIncremental((((Number) (set.getValue())).doubleValue() + increment), increment) + "";
                    if (Double.parseDouble(str) > set.getMax() && set.getInc() != 0) {
                        return;
                    }
                    Object newValue = (StringConversions.castNumber(str, increment));
                    if (newValue != null) {
                        set.setValue(newValue);
                        Module.saveSettings();
                        return;
                    }
                } else if (set.getValue().getClass().equals(Boolean.class)) {
                    boolean xd = ((Boolean) set.getValue());
                    set.setValue(!xd);
                    Module.saveSettings();
                } else if (set.getValue() instanceof Options) {
                    List<String> options = new CopyOnWriteArrayList<>();
                    Collections.addAll(options, ((Options) set.getValue()).getOptions());
                    for (int i = 0; i <= options.size() - 1; i++) {
                        if (options.get(i).equalsIgnoreCase(((Options) set.getValue()).getSelected())) {
                            if (i + 1 > options.size() - 1) {
                                ((Options) set.getValue()).setSelected(options.get(0));
                            } else {
                                ((Options) set.getValue()).setSelected(options.get(i + 1));
                            }
                            break;
                        }
                    }
                }
            } else if (event.getKey() == Keyboard.KEY_DOWN) {
                Setting set = getSettings(selectedModule).get(currentSetting);
                if (set.getValue() instanceof Number) {
                    double increment = (set.getInc());

                    String str = MathUtils.isInteger(MathUtils.getIncremental((((Number) (set.getValue())).doubleValue() - increment), increment)) ?
                            (MathUtils.getIncremental((((Number) (set.getValue())).doubleValue() - increment), increment) + "").replace(".0", "") : MathUtils.getIncremental((((Number) (set.getValue())).doubleValue() - increment), increment) + "";
                    if (Double.parseDouble(str) < set.getMin() && increment != 0) {
                        return;
                    }
                    Object newValue = (StringConversions.castNumber(str, increment));
                    if (newValue != null) {
                        set.setValue(newValue);
                        Module.saveSettings();
                        return;
                    }
                } else if (set.getValue().getClass().equals(Boolean.class)) {
                    boolean xd = ((Boolean) set.getValue()).booleanValue();
                    set.setValue(!xd);
                    Module.saveSettings();
                } else if (set.getValue() instanceof Options) {
                    List<String> options = new CopyOnWriteArrayList<>();
                    Collections.addAll(options, ((Options) set.getValue()).getOptions());
                    for (int i = options.size() - 1; i >= 0; i--) {
                        if (options.get(i).equalsIgnoreCase(((Options) set.getValue()).getSelected())) {
                            if (i - 1 < 0) {
                                ((Options) set.getValue()).setSelected(options.get(options.size() - 1));
                            } else {
                                ((Options) set.getValue()).setSelected(options.get(i - 1));
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean keyCheck(int key) {
        boolean active = false;
        switch (key) {
            case Keyboard.KEY_DOWN:
                active = true;
                break;
            case Keyboard.KEY_UP:
                active = true;
                break;
            case Keyboard.KEY_RETURN:
                active = true;
                break;
            case Keyboard.KEY_LEFT:
                active = true;
                break;
            case Keyboard.KEY_RIGHT:
                active = true;
                break;
            default:
                break;
        }
        return active;
    }

    private List<Setting> getSettings(Module mod) {
        List<Setting> settings = new CopyOnWriteArrayList<>();
        settings.addAll(mod.getSettings().values());
        for (Setting setting : settings) {
            if (setting.getValue().getClass().equals(String.class)) {
                settings.remove(setting);
            }
        }
        if (settings.isEmpty()) {
            return null;
        }
        settings.sort(Comparator.comparing(Setting::getName));
        return settings;
    }

    private List<Module> getModules(ModuleData.Type type) {
        List<Module> modulesInType = new ArrayList<>();
        for (Module mod : Client.getModuleManager().getArray()) {
            if (mod.getType() == type) {
                modulesInType.add(mod);
            }
        }
        if (modulesInType.isEmpty()) {
            return null;
        }
        modulesInType.sort(Comparator.comparing(Module::getName));
        return modulesInType;
    }
}
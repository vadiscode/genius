package genius.ui.click.ui;

import genius.Client;
import genius.management.ColorManager;
import genius.management.ColorObject;
import genius.management.animate.Opacity;
import genius.management.command.impl.Color;
import genius.management.keybinding.Keybind;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import genius.ui.click.ClickGui;
import genius.ui.click.components.*;
import genius.util.MathUtils;
import genius.util.RenderingUtil;
import genius.util.StringConversions;
import genius.util.misc.ChatUtil;
import genius.util.render.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Zeus extends UI {
    private Minecraft mc = Minecraft.getMinecraft();
    public Opacity opacity = new Opacity(0);
    private float hue;

    @Override
    public void mainConstructor(ClickGui p0, MainPanel panel) {
    }

    @Override
    public void onClose() {
        opacity.setOpacity(0);
    }

    @Override
    public void mainPanelDraw(MainPanel panel, int p0, int p1) {
        opacity.interp(255, 20);
        //.rectangleBordered((double)(panel.x + panel.dragX) - 0.3, (double)(panel.y + panel.dragY) - 0.3, (double)(panel.x + 380.0f + panel.dragX) + 0.5, (double)(panel.y + 310.0f + panel.dragY) + 0.3, 0.5, Colors.getColor(60, (int) opacity.getOpacity()), Colors.getColor(10, (int) opacity.getOpacity()));
        //RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) + 0.6, (double)(panel.y + panel.dragY) + 0.6, (double)(panel.x + 380.0f + panel.dragX) - 0.5, (double)(panel.y + 310.0f + panel.dragY) - 0.6, 1.3, Colors.getColor(60, (int) opacity.getOpacity()), Colors.getColor(40, (int) opacity.getOpacity()));
        RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) + 2.5, (double)(panel.y + panel.dragY) + 3.5, (double)(panel.x + 380.0f + panel.dragX) - 2.5, (double)(panel.y + 310.0f + panel.dragY) - 2.5, 0.5, Colors.getColor(47,49,54, (int) opacity.getOpacity()), Colors.getColor(12, (int) opacity.getOpacity()));
        RenderingUtil.rectangle(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + 4.0f, panel.x + panel.dragX + 377.0f, panel.y + panel.dragY + 15 - 1.0f, Colors.getColor(32,34,37, (int) opacity.getOpacity()));
        float y = 15.0f;
        for (int i = 0; i <= panel.typeButton.size(); ++i) {
            if (i > panel.typeButton.size() - 1 || !panel.typeButton.get((int)i).categoryPanel.visible || i <= 0) continue;
            y = 15 + i * 40;
        }
        GlStateManager.pushMatrix();
        this.prepareScissorBox(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + 4f, panel.x + panel.dragX + 40.0f, panel.y + panel.dragY + y + 1.0f);
        GL11.glEnable((int)3089);
        //RenderingUtil.rectangleBordered(panel.x + panel.dragX + 2.0f, panel.y + panel.dragY + 3.0f, panel.x + panel.dragX + 40.0f, panel.y + panel.dragY + y, 0.5, Colors.getColor(0, (int) opacity.getOpacity()), Colors.getColor(48, (int) opacity.getOpacity()));
        RenderingUtil.rectangle(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + 4.0f, panel.x + panel.dragX + 39.0f, panel.y + panel.dragY + y - 1.0f, Colors.getColor(32,34,37, (int) opacity.getOpacity()));
        GL11.glDisable((int)3089);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Client.fontManager.discordFont.drawStringWithShadow("GENIUS", panel.x + panel.dragX + 9.0f, panel.y + panel.dragY + 6.0f, Colors.getColor(114,118,125));
        this.prepareScissorBox(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + y + 40.0f, panel.x + panel.dragX + 40.0f, panel.y + panel.dragY + 308.0f);
        GL11.glEnable((int)3089);
        //RenderingUtil.rectangleBordered(panel.x + panel.dragX + 2.0f, panel.y + panel.dragY + y + 40.0f, panel.x + panel.dragX + 40.0f, panel.y + panel.dragY + 308.0f, 0.5, Colors.getColor(0, (int) opacity.getOpacity()), Colors.getColor(48, (int) opacity.getOpacity()));
        RenderingUtil.rectangle(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + y + 41.0f, panel.x + panel.dragX + 39.0f, (double)(panel.y + panel.dragY) + 307.5, Colors.getColor(32,34,37, (int) opacity.getOpacity()));
        GL11.glDisable((int)3089);
        GlStateManager.popMatrix();
        for (SLButton sLButton : panel.slButtons) {
            sLButton.draw(p0, p1);
        }
        for (CategoryButton categoryButton : panel.typeButton) {
            categoryButton.draw(p0, p1);
        }
        ScaledResolution rs = new ScaledResolution(this.mc);
        if (panel.dragging) {
            panel.dragX = (float)p0 - panel.lastDragX;
            panel.dragY = (float)p1 - panel.lastDragY;
        }
        if (panel.dragX > (float)(rs.getScaledWidth() - 430.5f)) {
            panel.dragX = rs.getScaledWidth() - 430.5f;
        }
        if (panel.dragX < -49.5f) {
            panel.dragX = -49.5f;
        }
        if (panel.dragY > (float)(rs.getScaledHeight() - 360.5f)) {
            panel.dragY = rs.getScaledHeight() - 360.5f;
        }
        if (panel.dragY < -50.0f) {
            panel.dragY = -50.0f;
        }
    }

    private void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(this.mc);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)factor)), (int)((int)(((float)scale.getScaledHeight() - y2) * (float)factor)), (int)((int)((x2 - x) * (float)factor)), (int)((int)((y2 - y) * (float)factor)));
    }

    @Override
    public void mainPanelKeyPress(MainPanel panel, int key) {
        for (CategoryButton cbutton : panel.typeButton) {
            for (Button button : cbutton.categoryPanel.buttons) {
                button.keyPressed(key);
            }
        }
    }

    @Override
    public void panelConstructor(MainPanel mainPanel, float x, float y) {
        int y1 = 15;
        for (ModuleData.Type types : ModuleData.Type.values()) {
            mainPanel.typeButton.add(new CategoryButton(mainPanel, types.name(), x + 3.0f, y + (float)y1));
            y += 40.0f;
        }
        mainPanel.typeButton.add(new CategoryButton(mainPanel, "Colors", x + 3.0f, y + (float)y1));
        mainPanel.typeButton.get((int)0).enabled = true;
        mainPanel.typeButton.get((int)0).categoryPanel.visible = true;
    }

    @Override
    public void panelMouseClicked(MainPanel mainPanel, int x, int y, int z) {
        if ((float)x >= mainPanel.x + mainPanel.dragX && (float)y >= mainPanel.dragY + mainPanel.y && (float)x <= mainPanel.dragX + mainPanel.x + 400.0f && (float)y <= mainPanel.dragY + mainPanel.y + 12.0f && z == 0) {
            mainPanel.dragging = true;
            mainPanel.lastDragX = (float)x - mainPanel.dragX;
            mainPanel.lastDragY = (float)y - mainPanel.dragY;
        }
        for (CategoryButton c : mainPanel.typeButton) {
            c.mouseClicked(x, y, z);
            c.categoryPanel.mouseClicked(x, y, z);
        }
        for (SLButton button : mainPanel.slButtons) {
            button.mouseClicked(x, y, z);
        }
    }

    @Override
    public void panelMouseMovedOrUp(MainPanel mainPanel, int x, int y, int z) {
        if (z == 0) {
            mainPanel.dragging = false;
        }
        for (CategoryButton button : mainPanel.typeButton) {
            button.mouseReleased(x, y, z);
        }
    }

    @Override
    public void categoryButtonConstructor(CategoryButton p0, MainPanel p1) {
        p0.categoryPanel = new CategoryPanel(p0.name, p0, 0.0f, 0.0f);
    }

    @Override
    public void categoryButtonMouseClicked(CategoryButton p0, MainPanel p1, int p2, int p3, int p4) {
        if ((float)p2 >= p0.x + p1.dragX && (float)p3 >= p1.dragY + p0.y && (float)p2 <= p1.dragX + p0.x + 40.0f && (float)p3 <= p1.dragY + p0.y + 40.0f && p4 == 0) {
            for (CategoryButton button : p1.typeButton) {
                if (button == p0) {
                    p0.enabled = true;
                    p0.categoryPanel.visible = true;
                    continue;
                }
                button.enabled = false;
                button.categoryPanel.visible = false;
            }
        }
    }

    @Override
    public void categoryButtonDraw(CategoryButton p0, float p2, float p3) {
        int color;
        int n = color = p0.enabled ? Colors.getColor(210, (int) opacity.getOpacity()) : Colors.getColor(91, (int) opacity.getOpacity());
        if (p2 >= p0.x + p0.panel.dragX && p3 >= p0.y + p0.panel.dragY && p2 <= p0.x + p0.panel.dragX + 40.0f && p3 <= p0.y + p0.panel.dragY + 40.0f && !p0.enabled) {
            color = Colors.getColor(165, (int) opacity.getOpacity());
        }
        if (p0.name.equalsIgnoreCase("Combat")) {
            Client.fontManager.badCache.drawCenteredString("E", p0.x + 19.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Player")) {
            Client.fontManager.badCache.drawCenteredString("F", p0.x + 18.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Movement")) {
            Client.fontManager.badCache.drawCenteredString("J", p0.x + 20.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Render")) {
            Client.fontManager.badCache.drawCenteredString("C", p0.x + 18.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Colors")) {
            Client.fontManager.badCache.drawCenteredString("H", p0.x + 18.5f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Misc")) {
            Client.fontManager.badCache.drawCenteredString("I", p0.x + 19.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else {
            Client.fontManager.f.drawStringWithShadow(Character.toString(p0.name.charAt(0)) + Character.toString(p0.name.charAt(1)), p0.x + 12.0f + p0.panel.dragX, p0.y + 13.0f + p0.panel.dragY, color);
        }
        if (p0.enabled) {
            RenderingUtil.drawRoundedRect(p0.x + p0.panel.dragX, p0.y + p0.panel.dragY + 10, p0.x + p0.panel.dragX + 2f, p0.y + p0.panel.dragY + 30.0f, -1, -1);
            p0.categoryPanel.draw(p2, p3);
        }
    }

    private List<Setting> getSettings(Module mod) {
        ArrayList<Setting> settings = new ArrayList<Setting>();
        for (Setting set : mod.getSettings().values()) {
            settings.add(set);
        }
        if (settings.isEmpty()) {
            return null;
        }
        return settings;
    }

    @Override
    public void categoryPanelConstructor(CategoryPanel categoryPanel, CategoryButton categoryButton, float x, float y) {
        Options option;
        int tY;
        float x1;
        Module module;
        int n;
        int n2;
        Module[] arrmodule;
        float noSets;
        float biggestY;
        float xOff = 55.0f + categoryButton.panel.x;
        float yOff = 15.0f + categoryButton.panel.y;
        if (categoryButton.name.equalsIgnoreCase("Combat")) {
            biggestY = 34.0f;
            noSets = 0.0f;
            arrmodule = (Module[]) Client.getModuleManager().getArray();
            n2 = arrmodule.length;
            for (n = 0; n < n2; ++n) {
                module = arrmodule[n];
                if (module.getType() != ModuleData.Type.Combat) continue;
                y = 20.0f;
                if (this.getSettings(module) != null) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff + 10.0f, module));
                    x1 = 0.0f;
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Boolean)) continue;
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        if ((x1 += 45.0f) != 90.0f) continue;
                        x1 = 0.0f;
                        y += 10.0f;
                    }
                    if (x1 == 45.0f) {
                        y += 10.0f;
                    }
                    x1 = 0.0f;
                    tY = 0;
                    ArrayList<Setting> arrayList = new ArrayList<Setting>();
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Number)) continue;
                        arrayList.add(setting);
                    }
                    arrayList.sort(Comparator.comparing(Setting::getName));
                    for (Setting setting : arrayList) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        tY = 0;
                        x1 = 0.0f;
                        y += 12.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        if (x1 == 45.0f) {
                            y += 14.0f;
                        }
                        x1 = 0.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        option = (Options)setting.getValue();
                        categoryPanel.dropdownBoxes.add(new DropdownBox(option, xOff + x1, yOff + y + 4.0f, categoryPanel));
                        tY = 17;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        y += 17.0f;
                        tY = 0;
                        x1 = 0.0f;
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 110.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    noSets += 45.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 315.0f)) continue;
                xOff = 55.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "Player") {
            biggestY = 34.0f;
            noSets = 0.0f;
            arrmodule = (Module[])Client.getModuleManager().getArray();
            n2 = arrmodule.length;
            for (n = 0; n < n2; ++n) {
                module = arrmodule[n];
                if (module.getType() != ModuleData.Type.Player) continue;
                y = 20.0f;
                if (this.getSettings(module) != null) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff + 10.0f, module));
                    x1 = 0.0f;
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Boolean)) continue;
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        if ((x1 += 45.0f) != 90.0f) continue;
                        x1 = 0.0f;
                        y += 10.0f;
                    }
                    if (x1 == 45.0f) {
                        y += 10.0f;
                    }
                    x1 = 0.0f;
                    tY = 0;
                    ArrayList<Setting> arrayList = new ArrayList<Setting>();
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Number)) continue;
                        arrayList.add(setting);
                    }
                    arrayList.sort(Comparator.comparing(Setting::getName));
                    for (Setting setting : arrayList) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        tY = 0;
                        x1 = 0.0f;
                        y += 12.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        if (x1 == 45.0f) {
                            y += 14.0f;
                        }
                        x1 = 0.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        option = (Options)setting.getValue();
                        categoryPanel.dropdownBoxes.add(new DropdownBox(option, xOff + x1, yOff + y + 4.0f, categoryPanel));
                        tY = 17;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        y += 17.0f;
                        tY = 0;
                        x1 = 0.0f;
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 110.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    if (noSets >= 315.0f) {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets - 315.0f, 345.0f, module));
                    } else {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    }
                    noSets += 45.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 315.0f)) continue;
                xOff = 55.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "Movement") {
            biggestY = 34.0f;
            noSets = 0.0f;
            arrmodule = (Module[])Client.getModuleManager().getArray();
            n2 = arrmodule.length;
            for (n = 0; n < n2; ++n) {
                module = arrmodule[n];
                if (module.getType() != ModuleData.Type.Movement) continue;
                y = 20.0f;
                if (this.getSettings(module) != null) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff + 10.0f, module));
                    x1 = 0.0f;
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Boolean)) continue;
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        if ((x1 += 45.0f) != 90.0f) continue;
                        x1 = 0.0f;
                        y += 10.0f;
                    }
                    if (x1 == 45.0f) {
                        y += 10.0f;
                    }
                    x1 = 0.0f;
                    tY = 0;
                    ArrayList<Setting> arrayList = new ArrayList<Setting>();
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Number)) continue;
                        arrayList.add(setting);
                    }
                    arrayList.sort(Comparator.comparing(Setting::getName));
                    for (Setting setting : arrayList) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        tY = 0;
                        x1 = 0.0f;
                        y += 12.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        if (x1 == 45.0f) {
                            y += 14.0f;
                        }
                        x1 = 0.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        option = (Options)setting.getValue();
                        categoryPanel.dropdownBoxes.add(new DropdownBox(option, xOff + x1, yOff + y + 4.0f, categoryPanel));
                        tY = 17;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        y += 17.0f;
                        tY = 0;
                        x1 = 0.0f;
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 110.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    noSets += 45.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 315.0f)) continue;
                xOff = 55.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "Render") {
            biggestY = 34.0f;
            noSets = 0.0f;
            arrmodule = (Module[])Client.getModuleManager().getArray();
            n2 = arrmodule.length;
            for (n = 0; n < n2; ++n) {
                module = arrmodule[n];
                if (module.getType() != ModuleData.Type.Render) continue;
                y = 20.0f;
                if (this.getSettings(module) != null) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff + 10.0f, module));
                    x1 = 0.0f;
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Boolean)) continue;
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        if ((x1 += 45.0f) != 90.0f) continue;
                        x1 = 0.0f;
                        y += 10.0f;
                    }
                    if (x1 == 45.0f) {
                        y += 10.0f;
                    }
                    x1 = 0.0f;
                    tY = 0;
                    ArrayList<Setting> arrayList = new ArrayList<Setting>();
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Number)) continue;
                        arrayList.add(setting);
                    }
                    arrayList.sort(Comparator.comparing(Setting::getName));
                    for (Setting setting : arrayList) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        tY = 0;
                        x1 = 0.0f;
                        y += 12.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        if (x1 == 45.0f) {
                            y += 14.0f;
                        }
                        x1 = 0.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        option = (Options)setting.getValue();
                        categoryPanel.dropdownBoxes.add(new DropdownBox(option, xOff + x1, yOff + y + 4.0f, categoryPanel));
                        tY = 17;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        y += 17.0f;
                        tY = 0;
                        x1 = 0.0f;
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 110.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    noSets += 45.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 315.0f)) continue;
                xOff = 55.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "Misc") {
            biggestY = 34.0f;
            noSets = 0.0f;
            arrmodule = (Module[])Client.getModuleManager().getArray();
            n2 = arrmodule.length;
            for (n = 0; n < n2; ++n) {
                module = arrmodule[n];
                if (module.getType() != ModuleData.Type.Misc) continue;
                y = 20.0f;
                if (this.getSettings(module) != null) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff + 10.0f, module));
                    x1 = 0.0f;
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Boolean)) continue;
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        if ((x1 += 45.0f) != 90.0f) continue;
                        x1 = 0.0f;
                        y += 10.0f;
                    }
                    if (x1 == 45.0f) {
                        y += 10.0f;
                    }
                    x1 = 0.0f;
                    tY = 0;
                    ArrayList<Setting> arrayList = new ArrayList<Setting>();
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Number)) continue;
                        arrayList.add(setting);
                    }
                    arrayList.sort(Comparator.comparing(Setting::getName));
                    for (Setting setting : arrayList) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        tY = 0;
                        x1 = 0.0f;
                        y += 12.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        if (x1 == 45.0f) {
                            y += 14.0f;
                        }
                        x1 = 0.0f;
                    }
                    for (Setting setting : this.getSettings(module)) {
                        if (!(setting.getValue() instanceof Options)) continue;
                        option = (Options)setting.getValue();
                        categoryPanel.dropdownBoxes.add(new DropdownBox(option, xOff + x1, yOff + y + 4.0f, categoryPanel));
                        tY = 17;
                        if ((x1 += 45.0f) != 90.0f) continue;
                        y += 17.0f;
                        tY = 0;
                        x1 = 0.0f;
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 110.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    noSets += 45.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 315.0f)) continue;
                xOff = 55.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "Colors") {
            categoryPanel.colorPreviews.add(new ColorPreview(Client.colorManager.getHudColor(), "Hud Color", xOff + 300.0f, y + 5.0f, categoryButton));
            categoryPanel.colorPreviews.add(new ColorPreview(Client.colorManager.getEspColor(), "ESP Color", xOff + 80.0f, y + 5.0f, categoryButton));
            categoryPanel.colorPreviews.add(new ColorPreview(Client.colorManager.getCrosshairColor(), "Crosshair Color", xOff + 190.0f, y + 5.0f, categoryButton));
        }
    }

    @Override
    public void categoryPanelMouseClicked(CategoryPanel categoryPanel, int p1, int p2, int p3) {
        boolean active = false;
        for (DropdownBox db : categoryPanel.dropdownBoxes) {
            if (!db.active) continue;
            db.mouseClicked(p1, p2, p3);
            active = true;
            break;
        }
        if (!active) {
            for (DropdownBox db : categoryPanel.dropdownBoxes) {
                db.mouseClicked(p1, p2, p3);
            }
            for (Button button : categoryPanel.buttons) {
                button.mouseClicked(p1, p2, p3);
            }
            for (Checkbox checkbox : categoryPanel.checkboxes) {
                checkbox.mouseClicked(p1, p2, p3);
            }
            for (Slider slider : categoryPanel.sliders) {
                slider.mouseClicked(p1, p2, p3);
            }
            for (ColorPreview cp : categoryPanel.colorPreviews) {
                for (RGBSlider slider : cp.sliders) {
                    slider.mouseClicked(p1, p2, p3);
                }
            }
        }
    }

    @Override
    public void categoryPanelDraw(CategoryPanel categoryPanel, float x, float y) {
        for (ColorPreview cp : categoryPanel.colorPreviews) {
            cp.draw(x, y);
        }
        for (GroupBox groupBox : categoryPanel.groupBoxes) {
            groupBox.draw(x, y);
        }
        for (Button button : categoryPanel.buttons) {
            button.draw(x, y);
        }
        for (Checkbox checkbox : categoryPanel.checkboxes) {
            checkbox.draw(x, y);
        }
        for (Slider slider : categoryPanel.sliders) {
            slider.draw(x, y);
        }
        for (DropdownBox db : categoryPanel.dropdownBoxes) {
            db.draw(x, y);
        }
        for (DropdownBox db : categoryPanel.dropdownBoxes) {
            if (!db.active) continue;
            for (DropdownButton b : db.buttons) {
                b.draw(x, y);
            }
        }
    }

    @Override
    public void categoryPanelMouseMovedOrUp(CategoryPanel categoryPanel, int x, int y, int button) {
        for (Slider slider : categoryPanel.sliders) {
            slider.mouseReleased(x, y, button);
        }
        for (ColorPreview cp : categoryPanel.colorPreviews) {
            for (RGBSlider slider : cp.sliders) {
                slider.mouseReleased(x, y, button);
            }
        }
    }

    @Override
    public void groupBoxConstructor(GroupBox groupBox, float x, float y) {
    }

    @Override
    public void groupBoxMouseClicked(GroupBox groupBox, int p1, int p2, int p3) {
    }

    @Override
    public void groupBoxDraw(GroupBox groupBox, float x, float y) {
        float xOff = groupBox.x + groupBox.categoryPanel.categoryButton.panel.dragX - 2.5f;
        float yOff = groupBox.y + groupBox.categoryPanel.categoryButton.panel.dragY + 10.0f;
        RenderingUtil.rectangleBordered(xOff, yOff - 6.0f, xOff + 90.0f, yOff + groupBox.ySize, 0.3, Colors.getColor(48, (int) opacity.getOpacity()), Colors.getColor(32, 34, 37, (int) opacity.getOpacity()));
        RenderingUtil.rectangle(xOff + 1.0f, yOff - 5.0f, xOff + 89.0f, yOff + groupBox.ySize - 1.0f, Colors.getColor(54, 57, 63, (int) opacity.getOpacity()));
        RenderingUtil.rectangle(xOff + 5.0f, yOff - 6.0f, xOff + Client.fontManager.fs.getStringWidth(groupBox.module.getName()) + 5.0f, yOff - 4.0f, Colors.getColor(54, 57, 63, (int) opacity.getOpacity()));
    }

    @Override
    public void groupBoxMouseMovedOrUp(GroupBox groupBox, int x, int y, int button) {
    }

    @Override
    public void buttonContructor(Button p0, CategoryPanel panel) {
    }

    @Override
    public void buttonMouseClicked(Button p0, int p2, int p3, int p4, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            boolean hovering;
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            boolean bl = hovering = (float)p2 >= p0.x + xOff && (float)p3 >= p0.y + yOff && (float)p2 <= p0.x + 35.0f + xOff && (float)p3 <= p0.y + 6.0f + yOff;
            if (hovering) {
                if (p4 == 0) {
                    if (!p0.isBinding) {
                        p0.module.toggle();
                        p0.enabled = p0.module.isEnabled();
                    } else {
                        p0.isBinding = false;
                    }
                } else if (p4 == 1) {
                    if (p0.isBinding) {
                        p0.module.setKeybind(new Keybind(p0.module, Keyboard.getKeyIndex((String)"NONE")));
                        p0.isBinding = false;
                    } else {
                        p0.isBinding = true;
                    }
                }
            } else if (p0.isBinding) {
                p0.isBinding = false;
            }
        }
    }

    @Override
    public void buttonDraw(Button p0, float p2, float p3, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            GlStateManager.pushMatrix();
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            RenderingUtil.rectangle((double)(p0.x + xOff) + 0.6, (double)(p0.y + yOff) + 0.6, (double)(p0.x + 6.0f + xOff) + -0.6, (double)(p0.y + 6.0f + yOff) + -0.6, Colors.getColor(114,118,125));
            RenderingUtil.drawGradient(p0.x + xOff + 1.0f, p0.y + yOff + 1.0f, p0.x + 6.0f + xOff + -1.0f, p0.y + 6.0f + yOff + -1.0f, Colors.getColor(54, 57, 63), Colors.getColor(54, 57, 63));
            p0.enabled = p0.module.isEnabled();
            boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff && p2 <= p0.x + 35.0f + xOff && p3 <= p0.y + 6.0f + yOff;
            GlStateManager.pushMatrix();
            Client.fontManager.fs.drawStringWithShadow(p0.module.getName(), p0.x + xOff + 3.5f, p0.y + 0.5f + yOff - 7.0f, Colors.getColor(220, (int) opacity.getOpacity()));
            Client.fontManager.fss.drawStringWithShadow("Enable", p0.x + 8.5f + xOff, p0.y + 1.0f + yOff, Colors.getColor(220, (int) opacity.getOpacity()));
            String meme = !p0.module.getKeybind().getKeyStr().equalsIgnoreCase("None") ? "[" + p0.module.getKeybind().getKeyStr() + "]" : "[-]";
            GlStateManager.pushMatrix();
            GlStateManager.translate(p0.x + xOff + 29.0f, p0.y + 1.0f + yOff, 0.0f);
            GlStateManager.scale(0.5, 0.5, 0.5);
            this.mc.fontRendererObj.drawStringWithShadow(meme, 0.0f, 0.0f, p0.isBinding ? Colors.getColor(216, 56, 56) : Colors.getColor(75, (int) opacity.getOpacity()));
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            if (p0.enabled) {
                RenderingUtil.drawGradient(p0.x + xOff + 1.0f, p0.y + yOff + 1.0f, p0.x + xOff + 5.0f, p0.y + yOff + 5.0f, Colors.getColor(255, 255, 255), Colors.getColor(255, 255, 255));
            }
            if (hovering && !p0.enabled) {
                RenderingUtil.rectangle(p0.x + xOff + 1.0f, p0.y + yOff + 1.0f, p0.x + xOff + 5.0f, p0.y + yOff + 5.0f, Colors.getColor(255, 40));
            }
            if (hovering) {
                Client.fontManager.fss.drawStringWithShadow(p0.module.getDescription() != null && !p0.module.getDescription().equalsIgnoreCase("") ? p0.module.getDescription() : "ERROR: No Description Found.", panel.categoryButton.panel.x + 2.0f + panel.categoryButton.panel.dragX + 55.0f, panel.categoryButton.panel.y + 9.0f + panel.categoryButton.panel.dragY, Colors.getColor(220, (int) opacity.getOpacity()));
            }
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void buttonKeyPressed(Button button, int key) {
        if (button.isBinding && key != 0) {
            int keyToBind = key;
            if (key == 1 || key == 14) {
                keyToBind = Keyboard.getKeyIndex((String)"NONE");
            }
            Keybind keybind = new Keybind(button.module, keyToBind);
            button.module.setKeybind(keybind);
            Module.saveStatus();
            button.isBinding = false;
        }
    }

    @Override
    public void checkBoxMouseClicked(Checkbox p0, int p2, int p3, int p4, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            boolean hovering;
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            boolean bl = hovering = (float)p2 >= p0.x + xOff && (float)p3 >= p0.y + yOff && (float)p2 <= p0.x + 35.0f + xOff && (float)p3 <= p0.y + 6.0f + yOff;
            if (hovering && p4 == 0) {
                boolean xd = (Boolean)p0.setting.getValue();
                p0.setting.setValue(!xd);
                Module.saveSettings();
            }
        }
    }

    @Override
    public void checkBoxDraw(Checkbox p0, float p2, float p3, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            GlStateManager.pushMatrix();
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            RenderingUtil.rectangle((double)(p0.x + xOff) + 0.6, (double)(p0.y + yOff) + 0.6, (double)(p0.x + 6.0f + xOff) + -0.6, (double)(p0.y + 6.0f + yOff) + -0.6, Colors.getColor(114,118,125));
            RenderingUtil.drawGradient(p0.x + xOff + 1.0f, p0.y + yOff + 1.0f, p0.x + 6.0f + xOff + -1.0f, p0.y + 6.0f + yOff + -1.0f, Colors.getColor(54, 57, 63), Colors.getColor(54, 57, 63));
            p0.enabled = (Boolean)p0.setting.getValue();
            boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff && p2 <= p0.x + 35.0f + xOff && p3 <= p0.y + 6.0f + yOff;
            GlStateManager.pushMatrix();
            String xd = p0.setting.getName().charAt(0) + p0.setting.getName().toLowerCase().substring(1);
            Client.fontManager.fss.drawStringWithShadow(xd, p0.x + 8.5f + xOff, p0.y + 1.0f + yOff, Colors.getColor(220));
            GlStateManager.popMatrix();
            if (p0.enabled) {
                RenderingUtil.drawGradient(p0.x + xOff + 1.0f, p0.y + yOff + 1.0f, p0.x + xOff + 5.0f, p0.y + yOff + 5.0f, Colors.getColor(255, 255, 255), Colors.getColor(255, 255, 255));
            }
            if (hovering && !p0.enabled) {
                RenderingUtil.rectangle(p0.x + xOff + 1.0f, p0.y + yOff + 1.0f, p0.x + xOff + 5.0f, p0.y + yOff + 5.0f, Colors.getColor(255, 40));
            }
            if (hovering) {
                Client.fontManager.fss.drawStringWithShadow(this.getDescription(p0.setting), panel.categoryButton.panel.x + 2.0f + panel.categoryButton.panel.dragX + 55.0f, panel.categoryButton.panel.y + 9.0f + panel.categoryButton.panel.dragY, -1);
            }
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void dropDownContructor(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
        int y = 10;
        for (String value : p0.option.getOptions()) {
            p0.buttons.add(new DropdownButton(value, p2, p3 + (float)y, p0));
            y += 9;
        }
    }

    @Override
    public void dropDownMouseClicked(DropdownBox dropDown, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
        for (DropdownButton db : dropDown.buttons) {
            if (!dropDown.active || !dropDown.panel.visible) continue;
            db.mouseClicked(mouseX, mouseY, mouse);
        }
        dropDown.active = (float)mouseX >= panel.categoryButton.panel.dragX + dropDown.x && (float)mouseY >= panel.categoryButton.panel.dragY + dropDown.y && (float)mouseX <= panel.categoryButton.panel.dragX + dropDown.x + 40.0f && (float)mouseY <= panel.categoryButton.panel.dragY + dropDown.y + 8.0f && mouse == 0 && dropDown.panel.visible ? !dropDown.active : false;
    }

    @Override
    public void dropDownDraw(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
        float xOff = panel.categoryButton.panel.dragX;
        float yOff = panel.categoryButton.panel.dragY;
        boolean hovering = p2 >= panel.categoryButton.panel.dragX + p0.x && p3 >= panel.categoryButton.panel.dragY + p0.y && p2 <= panel.categoryButton.panel.dragX + p0.x + 40.0f && p3 <= panel.categoryButton.panel.dragY + p0.y + 9.0f;
        RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3, (double)(p0.y + yOff) - 0.3, (double)(p0.x + xOff + 40.0f) + 0.3, (double)(p0.y + yOff + 9.0f) + 0.3, Colors.getColor(33,35,39));
        RenderingUtil.drawGradient(p0.x + xOff, p0.y + yOff, p0.x + xOff + 40.0f, p0.y + yOff + 9.0f, Colors.getColor(48,51,56), Colors.getColor(48,51,56));
        if (hovering) {
            RenderingUtil.rectangleBordered(p0.x + xOff - 0.5, p0.y + yOff - 0.5, p0.x + xOff + 40.5f, p0.y + yOff + 9.5f, 0.3, Colors.getColor(0, 0), Colors.getColor(19,20,22));
        }
        Client.fontManager.fss.drawStringWithShadow(p0.option.getName(), p0.x + xOff + 1.0f, p0.y - 6.0f + yOff, Colors.getColor(220));
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)(p0.x + xOff + 38.0f) - (p0.active ? 2.5 : 0.0), (double)(p0.y + 4.0f + yOff), 0.0);
        GlStateManager.rotate(p0.active ? 270.0f : 90.0f, 0.0f, 0.0f, 90.0f);
        RenderingUtil.rectangle(-1.0, 0.0, -0.5, 2.5, Colors.getColor(0));
        RenderingUtil.rectangle(-0.5, 0.0, 0.0, 2.5, Colors.getColor(151));
        RenderingUtil.rectangle(0.0, 0.5, 0.5, 2.0, Colors.getColor(151));
        RenderingUtil.rectangle(0.5, 1.0, 1.0, 1.5, Colors.getColor(151));
        GlStateManager.popMatrix();
        Client.fontManager.fss.drawString(p0.option.getSelected(), p0.x + 4.0f + xOff - 1.0f, p0.y + 3.0f + yOff, Colors.getColor(151));
        if (p0.active) {
            int i = p0.buttons.size();
            RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3, (double)(p0.y + 10.0f + yOff) - 0.3, (double)(p0.x + xOff + 40.0f) + 0.3, (double)(p0.y + yOff + 9.0f + (float)(9 * i)) + 0.3, Colors.getColor(32,34,37));
            RenderingUtil.drawGradient(p0.x + xOff, p0.y + yOff + 10.0f, p0.x + xOff + 40.0f, p0.y + yOff + 9.0f + (float)(9 * i), Colors.getColor(47, 49, 54), Colors.getColor(47, 49, 54));
        }
        if (hovering) {
            Client.fontManager.fss.drawStringWithShadow("ERROR: No Description Found.", panel.categoryButton.panel.x + 2.0f + panel.categoryButton.panel.dragX + 55.0f, panel.categoryButton.panel.y + 9.0f + panel.categoryButton.panel.dragY, -1);
        }
    }

    @Override
    public void dropDownButtonMouseClicked(DropdownButton p0, DropdownBox p1, int x, int y, int mouse) {
        if ((float)x >= p1.panel.categoryButton.panel.dragX + p0.x && (float)y >= p1.panel.categoryButton.panel.dragY + p0.y && (float)x <= p1.panel.categoryButton.panel.dragX + p0.x + 40.0f && (double)y <= (double)(p1.panel.categoryButton.panel.dragY + p0.y) + 8.5 && mouse == 0) {
            p1.option.setSelected(p0.name);
            p1.active = false;
        }
    }

    @Override
    public void dropDownButtonDraw(DropdownButton p0, DropdownBox p1, float x, float y) {
        float xOff = p1.panel.categoryButton.panel.dragX;
        float yOff = p1.panel.categoryButton.panel.dragY;
        boolean hovering = x >= xOff + p0.x && y >= yOff + p0.y && x <= xOff + p0.x + 40.0f && (double)y <= (double)(yOff + p0.y) + 8.5;
        GlStateManager.pushMatrix();
        RenderingUtil.rectangle((double)(p0.x + xOff + 0.5f) - 0.3, (double)(p0.y + yOff) - 0.3, (double)(p0.x + xOff + 39.5f) + 0.3, (double)(p0.y + yOff + 7.5f) + 0.3, hovering ? Colors.getColor(38,39,43) : Colors.getColor(47,49,54));
        Client.fontManager.fss.drawStringWithShadow(p0.name, p0.x + 3.0f + xOff, p0.y + 2.0f + yOff, hovering ? Colors.getColor(255) : -1);
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

    @Override
    public void SliderContructor(Slider p0, CategoryPanel panel) {
        p0.dragX = ((Number)p0.setting.getValue()).doubleValue() * 40.0 / p0.setting.getMax();
    }

    @Override
    public void categoryButtonMouseReleased(CategoryButton categoryButton, int x, int y, int button) {
        categoryButton.categoryPanel.mouseReleased(x, y, button);
    }

    @Override
    public void slButtonDraw(SLButton slButton, float x, float y, MainPanel panel) {
        float xOff = panel.dragX;
        float yOff = panel.dragY + 75.0f;
        boolean hovering = x >= 55.0f + slButton.x + xOff && y >= slButton.y + yOff - 2.0f && x <= 55.0f + slButton.x + xOff + 40.0f && y <= slButton.y + 8.0f + yOff + 2.0f;
        RenderingUtil.rectangleBordered((double)(slButton.x + xOff + 55.0f) - 0.3, (double)(slButton.y + yOff) - 0.3 - 2.0, (double)(slButton.x + xOff + 40.0f + 55.0f) + 0.3, (double)(slButton.y + 8.0f + yOff) + 0.3 + 2.0, 0.3, Colors.getColor(10), Colors.getColor(10));
        RenderingUtil.drawGradient(slButton.x + xOff + 55.0f, slButton.y + yOff - 2.0f, slButton.x + xOff + 40.0f + 55.0f, slButton.y + 8.0f + yOff + 2.0f, Colors.getColor(46), Colors.getColor(27));
        if (hovering) {
            RenderingUtil.rectangleBordered(slButton.x + xOff + 55.0f, slButton.y + yOff - 2.0f, slButton.x + xOff + 40.0f + 55.0f, slButton.y + 8.0f + yOff + 2.0f, 0.6, Colors.getColor(0, 0), Colors.getColor(90));
        }
        float xOffset = Client.fontManager.fs.getStringWidth(slButton.name) / 2.0f;
        Client.fontManager.fs.drawStringWithShadow(slButton.name, xOff + 25.0f + 55.0f - xOffset, slButton.y + yOff + 1.5f, -1);
    }

    @Override
    public void slButtonMouseClicked(SLButton slButton, float x, float y, int button, MainPanel panel) {
        block6: {
            float xOff = panel.dragX;
            float yOff = panel.dragY + 75.0f;
            if (button != 0 || !(x >= 55.0f + slButton.x + xOff) || !(y >= slButton.y + yOff - 2.0f) || !(x <= 55.0f + slButton.x + xOff + 40.0f) || !(y <= slButton.y + 8.0f + yOff + 2.0f)) break block6;
            if (slButton.load) {
                ChatUtil.printChat("Settings have been loaded.");
                Module.loadSettings();
                Color.loadStatus();
                for (CategoryPanel xd : panel.typePanel) {
                    for (Slider slider : xd.sliders) {
                        slider.dragX = slider.lastDragX = ((Number)slider.setting.getValue()).doubleValue() * 40.0 / slider.setting.getMax();
                    }
                }
            } else {
                ChatUtil.printChat("Settings have been saved.");
                Color.saveStatus();
                Module.saveSettings();
                for (CategoryPanel xd : panel.typePanel) {
                    for (Slider slider : xd.sliders) {
                        slider.dragX = slider.lastDragX = ((Number)slider.setting.getValue()).doubleValue() * 40.0 / slider.setting.getMax();
                    }
                }
            }
        }
    }

    @Override
    public void colorConstructor(ColorPreview colorPreview, float x, float y) {
        int i = 0;
        for (RGBSlider.Colors xd : RGBSlider.Colors.values()) {
            colorPreview.sliders.add(new RGBSlider(x + 10.0f, y + (float)i, colorPreview, xd));
            i += 12;
        }
    }

    @Override
    public void colorPrewviewDraw(ColorPreview colorPreview, float x, float y) {
        float xOff = colorPreview.x + colorPreview.categoryPanel.panel.dragX;
        float yOff = colorPreview.y + colorPreview.categoryPanel.panel.dragY + 75.0f;
        RenderingUtil.rectangleBordered(xOff - 80.0f, yOff - 6.0f, xOff + 1.0f, yOff + 46.0f, 0.3, Colors.getColor(48, (int) opacity.getOpacity()), Colors.getColor(32, 34, 37, (int) opacity.getOpacity()));
        RenderingUtil.rectangle(xOff - 79.0f, yOff - 5.0f, xOff, yOff + 45.0f, Colors.getColor(54, 57, 63, (int) opacity.getOpacity()));
        RenderingUtil.rectangle(xOff - 74.0f, yOff - 6.0f, xOff - 73.0f + Client.fontManager.fs.getStringWidth(colorPreview.colorName) + 1.0f, yOff - 4.0f, Colors.getColor(54, 57, 63, (int) opacity.getOpacity()));
        Client.fontManager.fs.drawStringWithShadow(colorPreview.colorName, xOff - 73.0f, yOff - 8.0f, -1);
        for (RGBSlider slider : colorPreview.sliders) {
            slider.draw(x, y);
        }
    }

    @Override
    public void rgbSliderDraw(RGBSlider slider, float x, float y) {
        float xOff = slider.x + slider.colorPreview.categoryPanel.panel.dragX - 75.0f;
        float yOff = slider.y + slider.colorPreview.categoryPanel.panel.dragY + 74.0f;
        double fraction = slider.dragX / 60.0;
        double value = MathUtils.getIncremental(fraction * 255.0, 1.0);
        ColorObject cO = slider.colorPreview.colorObject;
        int faggotNiggerColor = Colors.getColor(cO.red, cO.green, cO.blue, 255);
        int faggotNiggerColor2 = Colors.getColor(cO.red, cO.green, cO.blue, 120);
        RenderingUtil.rectangle(xOff, yOff, xOff + 60.0f, yOff + 6.0f, Colors.getColor(32));
        switch (slider.rgba) {
            case ALPHA: {
                faggotNiggerColor = Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 255);
                faggotNiggerColor2 = Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120);
            }
        }
        RenderingUtil.rectangle(xOff, yOff, (double)xOff + 60.0 * fraction, yOff + 6.0f, Colors.getColor(0));
        RenderingUtil.drawGradient(xOff, yOff, (double)xOff + 60.0 * fraction, yOff + 6.0f, faggotNiggerColor, faggotNiggerColor2);
        String current = "R";
        switch (slider.rgba) {
            case BLUE: {
                current = "B";
                break;
            }
            case GREEN: {
                current = "G";
                break;
            }
            case ALPHA: {
                current = "A";
            }
        }
        Client.fontManager.fs.drawStringWithShadow(current, xOff - 7.0f, yOff + 0.5f, Colors.getColor(220));
        float textX = xOff + 30.0f - Client.fontManager.fs.getStringWidth(Integer.toString((int)value)) / 2.0f;
        Client.fontManager.fsmallbold.drawBorderedString(Integer.toString((int)value), textX, yOff + 5.0f, Colors.getColor(220));
        double newValue = 0.0;
        if (slider.dragging) {
            slider.dragX = (double)x - slider.lastDragX;
            if (value <= 255.0 && value >= 0.0) {
                newValue = value;
            }
            switch (slider.rgba) {
                case RED: {
                    slider.colorPreview.colorObject.setRed((int)newValue);
                    break;
                }
                case GREEN: {
                    slider.colorPreview.colorObject.setGreen((int)newValue);
                    break;
                }
                case BLUE: {
                    slider.colorPreview.colorObject.setBlue((int)newValue);
                    break;
                }
                case ALPHA: {
                    slider.colorPreview.colorObject.setAlpha((int)newValue);
                }
            }
        }
        if (slider.dragX <= 0.0) {
            slider.dragX = 0.0;
        }
        if (slider.dragX >= 60.0) {
            slider.dragX = 60.0;
        }
    }

    @Override
    public void rgbSliderClick(RGBSlider slider, float x, float y, int mouse) {
        float xOff = slider.x + slider.colorPreview.categoryPanel.panel.dragX - 75.0f;
        float yOff = slider.y + slider.colorPreview.categoryPanel.panel.dragY + 74.0f;
        if (slider.colorPreview.categoryPanel.enabled && x >= xOff && y >= yOff && x <= xOff + 60.0f && y <= yOff + 6.0f && mouse == 0) {
            slider.dragging = true;
            slider.lastDragX = (double)x - slider.dragX;
        }
    }

    @Override
    public void rgbSliderMovedOrUp(RGBSlider slider, float x, float y, int mouse) {
        if (mouse == 0) {
            Color.saveStatus();
            slider.dragging = false;
        }
    }

    @Override
    public void SliderMouseClicked(Slider slider, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
        float xOff = panel.categoryButton.panel.dragX;
        float yOff = panel.categoryButton.panel.dragY;
        if (panel.visible && (float)mouseX >= panel.x + xOff + slider.x && (float)mouseY >= yOff + panel.y + slider.y - 6.0f && (float)mouseX <= xOff + panel.x + slider.x + 40.0f && (float)mouseY <= yOff + panel.y + slider.y + 4.0f && mouse == 0) {
            slider.dragging = true;
            slider.lastDragX = (double)mouseX - slider.dragX;
        }
    }

    @Override
    public void SliderMouseMovedOrUp(Slider slider, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
        if (mouse == 0) {
            slider.dragging = false;
        }
    }

    @Override
    public void SliderDraw(Slider slider, float x, float y, CategoryPanel panel) {
        if (panel.visible) {
            Object newValue;
            GlStateManager.pushMatrix();
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            double percent = slider.dragX / 40.0;
            double value = MathUtils.getIncremental(percent * 100.0 * (slider.setting.getMax() - slider.setting.getMin()) / 100.0 + slider.setting.getMin(), slider.setting.getInc());
            float sliderX = (float)(percent * 38.0);
            RenderingUtil.rectangle((double)(slider.x + xOff) - 0.3, (double)(slider.y + yOff) - 0.3, (double)(slider.x + xOff + 38.0f) + 0.3, (double)(slider.y + yOff + 3.0f) + 0.3, Colors.getColor(33,35,39));
            RenderingUtil.drawGradient(slider.x + xOff, slider.y + yOff, slider.x + xOff + 38.0f, slider.y + yOff + 3.0f, Colors.getColor(48,51, 56), Colors.getColor(48,51,56));
            RenderingUtil.drawGradient(slider.x + xOff, slider.y + yOff, slider.x + xOff + sliderX, slider.y + yOff + 3.0f, Colors.getColor(114, 137, 218, 255), Colors.getColor(114, 137, 218, 120));
            String xd = slider.setting.getName().charAt(0) + slider.setting.getName().toLowerCase().substring(1);
            double setting = ((Number)slider.setting.getValue()).doubleValue();
            GlStateManager.pushMatrix();
            String valu2e = MathUtils.isInteger(setting) ? (int)setting + "" : setting + "";
            String a = slider.setting.getName().toLowerCase();
            if (a.contains("fov")) {
                valu2e = valu2e + "\u00b0";
            } else if (a.contains("delay")) {
                valu2e = valu2e + "ms";
            }
            float strWidth = Client.fontManager.fs.getStringWidth(valu2e);
            float textX = sliderX + strWidth > 42.0f ? sliderX - strWidth : sliderX - strWidth / 2.0f;
            Client.fontManager.fsmallbold.drawBorderedString(valu2e, slider.x + xOff + 36.0F, slider.y - 6.0f + yOff, Colors.getColor(220));
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
            Client.fontManager.fss.drawStringWithShadow(xd, slider.x + xOff + 1.5f, slider.y - 6.0f + yOff, Colors.getColor(220));
            if (slider.dragging) {
                slider.dragX = (double)x - slider.lastDragX;
                newValue = StringConversions.castNumber(Double.toString(value), slider.setting.getInc());
                slider.setting.setValue(newValue);
            }
            if (((Number)slider.setting.getValue()).doubleValue() <= slider.setting.getMin()) {
                newValue = StringConversions.castNumber(Double.toString(slider.setting.getMin()), slider.setting.getInc());
                slider.setting.setValue(newValue);
            }
            if (((Number)slider.setting.getValue()).doubleValue() >= slider.setting.getMax()) {
                newValue = StringConversions.castNumber(Double.toString(slider.setting.getMax()), slider.setting.getInc());
                slider.setting.setValue(newValue);
            }
            if (slider.dragX <= 0.0) {
                slider.dragX = 0.0;
            }
            if (slider.dragX >= 40.0) {
                slider.dragX = 40.0;
            }
            if (x >= xOff + slider.x && y >= yOff + slider.y - 6.0f && x <= xOff + slider.x + 38.0f && y <= yOff + slider.y + 3.0f || slider.dragging) {
                Client.fontManager.fss.drawStringWithShadow(this.getDescription(slider.setting) + " Min: " + slider.setting.getMin() + " Max: " + slider.setting.getMax(), panel.categoryButton.panel.x + 2.0f + panel.categoryButton.panel.dragX + 55.0f, panel.categoryButton.panel.y + 9.0f + panel.categoryButton.panel.dragY, -1);
            }
            GlStateManager.popMatrix();
        }
    }

    private String getDescription(Setting setting) {
        if (setting.getDesc() != null && !setting.getDesc().equalsIgnoreCase("")) {
            return setting.getDesc();
        }
        return "ERROR: No Description Found.";
    }
}
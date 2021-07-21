package genius.ui.click.components;

import genius.Client;
import genius.module.Module;
import genius.module.data.Setting;
import genius.ui.click.ui.UI;

public class Checkbox {
    public CategoryPanel panel;
    public Module module;
    public boolean enabled;
    public float x;
    public float y;
    public String name;
    public Setting setting;

    public Checkbox(CategoryPanel panel, String name, float x, float y, Setting setting) {
        this.panel = panel;
        this.name = name;
        this.x = x;
        this.y = y;
        this.setting = setting;
        this.enabled = ((Boolean)setting.getValue());
    }

    public Checkbox(CategoryPanel panel, String name, float x, float y, Setting setting, Module module) {
        this.panel = panel;
        this.name = name;
        this.x = x;
        this.y = y;
        this.setting = setting;
        this.enabled = ((Boolean)setting.getValue());
        this.module = module;
    }

    public void draw(final float x, final float y) {
        for (final UI theme : Client.getClickGui().getThemes()) {
            if(panel.visible) {
                theme.checkBoxDraw(this, x, y, this.panel);
            }
        }
    }

    public void mouseClicked(final int x, final int y, final int button) {
        for (final UI theme : Client.getClickGui().getThemes()) {
            theme.checkBoxMouseClicked(this, x, y, button, this.panel);
        }
    }
}
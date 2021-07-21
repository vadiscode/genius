package genius.ui.click.components;

import genius.Client;
import genius.module.Module;
import genius.module.data.Options;
import genius.ui.click.ui.UI;

import java.util.ArrayList;

public class DropdownBox {
    public Options option;
    public float x;
    public float y;
    public ArrayList<DropdownButton> buttons = new ArrayList();
    public CategoryPanel panel;
    public boolean active;

    public DropdownBox(Options option, float x, float y, CategoryPanel panel) {
        this.option = option;
        this.panel = panel;
        this.x = x;
        this.y = y;
        panel.categoryButton.panel.theme.dropDownContructor(this, x, y, this.panel);
    }

    public void draw(final float x, final float y) {
        for (final UI theme : Client.getClickGui().getThemes()) {
            if(panel.visible) {
                theme.dropDownDraw(this, x, y, this.panel);
            }
        }
    }

    public void mouseClicked(final int x, final int y, final int button) {
        for (final UI theme : Client.getClickGui().getThemes()) {
            theme.dropDownMouseClicked(this, x, y, button, this.panel);
        }
    }
}
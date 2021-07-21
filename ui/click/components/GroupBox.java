package genius.ui.click.components;

import genius.Client;
import genius.module.Module;
import genius.ui.click.ui.UI;

public class GroupBox {
    public float x;
    public float y;
    public float ySize;
    public CategoryPanel categoryPanel;
    public Module module;

    public GroupBox(Module module, CategoryPanel categoryPanel, float x, float y, float ySize) {
        this.x = x;
        this.y = y;
        this.categoryPanel = categoryPanel;
        this.module = module;
        this.ySize = ySize;
        categoryPanel.categoryButton.panel.theme.groupBoxConstructor(this, x, y);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getClickGui().getThemes()) {
            theme.groupBoxDraw(this, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getClickGui().getThemes()) {
            theme.groupBoxMouseClicked(this, x, y, button);
        }
    }

    public void mouseReleased(int x, int y, int button) {
        for (UI theme : Client.getClickGui().getThemes()) {
            theme.groupBoxMouseMovedOrUp(this, x, y, button);
        }
    }
}
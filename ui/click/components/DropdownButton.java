package genius.ui.click.components;

import genius.Client;
import genius.ui.click.ui.UI;

public class DropdownButton {
    public String name;
    public float x;
    public float y;
    public DropdownBox box;

    public DropdownButton(String name, float x, float y, DropdownBox box) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.box = box;
    }


    public void draw(final float x, final float y) {
        for(UI theme : Client.getClickGui().getThemes()) {
            theme.dropDownButtonDraw(this, box, x, y);
        }
    }

    public void mouseClicked(final int x, final int y, final int button) {
        for(UI theme : Client.getClickGui().getThemes()) {
            theme.dropDownButtonMouseClicked(this, box, x, y, button);
        }
    }
}
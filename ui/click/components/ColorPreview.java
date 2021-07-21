package genius.ui.click.components;

import genius.Client;
import genius.management.ColorObject;
import genius.ui.click.ui.UI;

import java.util.ArrayList;

public class ColorPreview {
    public String colorName;
    public float x;
    public float y;
    public CategoryButton categoryPanel;
    public ColorObject colorObject;
    public ArrayList<RGBSlider> sliders;

    public ColorPreview(ColorObject colorObject, String colorName, float x, float y, CategoryButton categoryPanel) {
        sliders = new ArrayList<>();
        this.colorObject = colorObject;
        this.categoryPanel = categoryPanel;
        this.colorName = colorName;
        this.x = x;
        this.y = y;
        categoryPanel.panel.theme.colorConstructor(this,x,y);
    }

    public void draw(final float x, final float y) {
        for (final UI theme : Client.getClickGui().getThemes()) {
                theme.colorPrewviewDraw(this, x, y);
        }
    }
}
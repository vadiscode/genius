package genius.ui.click.components;

import genius.Client;
import genius.module.Module;
import genius.ui.click.ui.UI;

import java.util.ArrayList;

public class CategoryPanel {
    public float x;
    public float y;
    public boolean visible;
    public CategoryButton categoryButton;
    public String headerString;
    public ArrayList<Button> buttons;
    public ArrayList<Slider> sliders;
    public ArrayList<DropdownBox> dropdownBoxes;
    public ArrayList<DropdownButton> dropdownButtons;
    public ArrayList<Checkbox> checkboxes;
    public ArrayList<ColorPreview> colorPreviews;
    public ArrayList<GroupBox> groupBoxes;
    public ArrayList<RGBSlider> rgbSliders;
    public Module settingModule;

    public CategoryPanel(String name, CategoryButton categoryButton, float x, float y) {
        this.headerString = name;
        this.x = x;
        this.y = y;
        this.categoryButton = categoryButton;
        colorPreviews = new ArrayList<>();
        buttons = new ArrayList<>();
        sliders = new ArrayList<>();
        dropdownBoxes = new ArrayList<>();
        dropdownButtons = new ArrayList<>();
        checkboxes = new ArrayList<>();
        groupBoxes = new ArrayList();
        rgbSliders = new ArrayList<>();
        this.visible = false;
        categoryButton.panel.theme.categoryPanelConstructor(this, categoryButton, x, y);
    }

    public void draw(final float x, final float y) {
        for(UI theme : Client.getClickGui().getThemes()) {
            theme.categoryPanelDraw(this, x, y);
        }
    }

    public void mouseClicked(final int x, final int y, final int button) {
        for(UI theme : Client.getClickGui().getThemes()) {
            theme.categoryPanelMouseClicked(this, x, y, button);
        }
    }

    public void mouseReleased(final int x, final int y, final int button) {
        for(UI theme : Client.getClickGui().getThemes()) {
                theme.categoryPanelMouseMovedOrUp(this, x, y, button);
        }
    }
}
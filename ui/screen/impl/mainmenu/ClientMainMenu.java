package genius.ui.screen.impl.mainmenu;

import genius.ui.screen.PanoramaScreen;
import net.minecraft.client.Minecraft;

public class ClientMainMenu extends PanoramaScreen {
    private static final GuiModdedMainMenu menuModded = new GuiModdedMainMenu();

    public void initGui() {
        if (getClass().equals(ClientMainMenu.class)) {
            display();
        }
    }

    private void display() {
        Minecraft.getMinecraft().displayGuiScreen(menuModded);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
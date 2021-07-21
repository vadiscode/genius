package genius.ui.click;

import genius.Client;
import genius.management.animate.Opacity;
import genius.ui.click.components.MainPanel;
import genius.ui.click.ui.Menu;
import genius.ui.click.ui.UI;
import genius.ui.click.ui.Zeus;
import genius.util.RenderingUtil;
import genius.util.render.Colors;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClickGui extends GuiScreen {
    private MainPanel mainPanel;
    public static Menu menu;

    public List<UI> getThemes() {
        return themes;
    }

    private List<UI> themes;

    public ClickGui() {
        (themes = new CopyOnWriteArrayList<>()).add(new Zeus());
        mainPanel = new MainPanel(Client.clientName, 50, 50, themes.get(0));
        themes.get(0).mainConstructor(this, mainPanel);
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        this.mainPanel.draw(mouseX, mouseY);
        GlStateManager.popMatrix();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        mainPanel.mouseMovedOrUp(mouseX, mouseY, mouseButton);
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
        try {
            mainPanel.mouseClicked(mouseX, mouseY, clickedButton);
            super.mouseClicked(mouseX, mouseY, clickedButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
        if (Keyboard.getEventKeyState()) {
            mainPanel.keyPressed(Keyboard.getEventKey());
        }
    }

    @Override
    public void onGuiClosed() {
        themes.get(0).onClose();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
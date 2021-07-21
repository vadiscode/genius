package genius.ui.screen.impl.mainmenu;

import genius.Client;
import genius.ui.altmanager.GuiAltManager;
import genius.ui.screen.components.GuiMenuButton;
import genius.util.RenderingUtil;
import genius.util.render.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiModdedMainMenu extends ClientMainMenu {
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        String strSSP = I18n.format("Singleplayer", new Object[0]);
        String strSMP = I18n.format("Multiplayer", new Object[0]);
        String strAccounts = I18n.format("Accounts", new Object[0]);
        String strLang = I18n.format("Language", new Object[0]);
        String strOptions = I18n.format("Options", new Object[0]);
        String strQuit = I18n.format("Exit Game", new Object[0]);
        int initHeight = this.height / 4 + 50;
        int objHeight = 17;
        int objWidth = 63;
        int objPadding = 4;
        int xMid = this.width / 2 - objWidth / 2;
        this.buttonList.add(new GuiMenuButton(0, xMid, initHeight, objWidth, objHeight, strSSP));
        this.buttonList.add(new GuiMenuButton(1, xMid, initHeight + 20, objWidth, objHeight, strSMP));
        this.buttonList.add(new GuiMenuButton(2, xMid, initHeight + 40, objWidth, objHeight, strAccounts));
        this.buttonList.add(new GuiMenuButton(3, xMid, initHeight + 60, objWidth, objHeight, strLang));
        this.buttonList.add(new GuiMenuButton(4, xMid, initHeight + 80, objWidth, objHeight, strOptions));
        this.buttonList.add(new GuiMenuButton(5, xMid, initHeight + 100, objWidth, objHeight, strQuit));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiSelectWorld(this));
        } else if (button.id == 1) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        } else if (button.id == 2) {
            mc.displayGuiScreen(new GuiAltManager());
        } else if (button.id == 3) {
            mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
        } else if (button.id == 4) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        } else if (button.id == 5) {
            mc.shutdown();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        ScaledResolution sr2 = new ScaledResolution(Minecraft.getMinecraft());
        RenderingUtil.rectangleBordered((double)(sr2.getScaledWidth() / 2 - 62) - 0.5, (double)(this.height / 4 + 10) - 0.3, (double)(sr2.getScaledWidth() / 2 + 62) + 0.5, (double)(this.height / 4 + 175) + 0.3, 0.5, Colors.getColor(60), Colors.getColor(10));
        RenderingUtil.rectangleBordered((double)(sr2.getScaledWidth() / 2 - 62) + 0.5, (double)(this.height / 4 + 10) + 0.6, (double)(sr2.getScaledWidth() / 2 + 62) - 0.5, (double)(this.height / 4 + 175) - 0.6, 1.3, Colors.getColor(60), Colors.getColor(40));
        RenderingUtil.rectangleBordered((double)(sr2.getScaledWidth() / 2 - 62) + 2.5, (double)(this.height / 4 + 10) + 2.5, (double)(sr2.getScaledWidth() / 2 + 62) - 2.5, (double)(this.height / 4 + 175) - 2.5, 0.5, Colors.getColor(22), Colors.getColor(12));
        RenderingUtil.rectangle(sr2.getScaledWidth() / 2 - 62 + 3, (double)(this.height / 4 + 30) + 3.5, sr2.getScaledWidth() / 2 + 62 - 3, this.height / 4 + 30 + 4, Colors.getColor(0, 110));
        RenderingUtil.rectangleBordered(sr2.getScaledWidth() / 2 - 66 + 6, this.height / 4 + 25 + 8, (double)(sr2.getScaledWidth() / 2 + 66) - 6, this.height / 4 + 36, 0.3, Colors.getColor(48), Colors.getColor(60));
        RenderingUtil.rectangle(sr2.getScaledWidth() / 2 - 66 + 6 + 1, this.height / 4 + 25 + 9, (double)(sr2.getScaledWidth() / 2 + 66) - 7, this.height / 4 + 36 - 1, Colors.getColor(40));
        Client.fontManager.mainMenuLogo.drawStringWithShadow("Genius", (double)(sr2.getScaledWidth() / 2 - 22) - 0.5, (double)(this.height / 4 + 16) - 0.3, -1);
        //RenderingUtil.rectangle(sr2.getScaledWidth() / 2 - 62 + 6 + 4, this.height / 4 + 30 + 8, sr2.getScaledWidth() / 2 - 62 + 36, this.height / 4 + 30 + 9, Colors.getColor(17));
//        String s = "GeniusWare";
//        Client.fontManager.mainMenu.drawStringWithShadow(s, 2, this.height - 10, -1);
//        String s1 = "vadis#5066";
//        Client.fontManager.mainMenu.drawStringWithShadow(s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
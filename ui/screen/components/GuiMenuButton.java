package genius.ui.screen.components;

import genius.Client;
import genius.util.RenderingUtil;
import genius.util.render.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiMenuButton extends GuiButton {
    float scale = 1.0f;
    private double yText = 2;

    public GuiMenuButton(int buttonId, int x2, int y2, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x2, y2, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc2, int mouseX, int mouseY) {
        float offset;
        int text;
        if (this.visible) {
            this.scale = 1.0f;
            GlStateManager.pushMatrix();
            GlStateManager.scale(this.scale, this.scale, this.scale);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            this.mouseDragged(mc2, mouseX, mouseY);
            text = -1;
            GlStateManager.pushMatrix();
            offset = (float)(this.xPosition + this.width / 2) / this.scale;
            GlStateManager.translate(offset, (float)this.yPosition / this.scale, 1.0f);
            if (this.hovered) {
                if (this.displayString.contains("Exit Game")) {
                    RenderingUtil.rectangleBordered(-36.0, -2.0f / this.scale, 36.0, 12.0, 0.5, Colors.getColor(255, 85, 85), Colors.getColor(0));
                } else {
                    RenderingUtil.rectangleBordered(-36.0, -2.0f / this.scale, 36.0, 12.0, 0.5, Colors.getColor(120), Colors.getColor(0));
                }
            } else {
                RenderingUtil.rectangleBordered(-36.0, -2.0f / this.scale, 36.0, 12.0, 0.5, Colors.getColor(50), Colors.getColor(0));
            }
            RenderingUtil.rectangleBordered(-35.0, -1.0f / this.scale, 35.0, 11.0, 0.5, Colors.getColor(60), Colors.getColor(0));
            RenderingUtil.drawGradient(-35.0, -1.0f / this.scale, 35.0, 11.0, Colors.getColor(60), Colors.getColor(30));
            if (this.displayString.contains("Exit Game")) {
                Client.fontManager.mainMenu.drawStringWithShadow(this.displayString, -((float)Client.fontManager.mainMenu.getStringWidth(this.displayString) / 2.0f), yText, Colors.getColor(255, 85, 85));
            } else {
                Client.fontManager.mainMenu.drawStringWithShadow(this.displayString, -((float)Client.fontManager.mainMenu.getStringWidth(this.displayString) / 2.0f), yText, text);
            }
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        }
        if (!this.enabled) {
            this.scale = 1.0f;
            GlStateManager.pushMatrix();
            GlStateManager.scale(this.scale, this.scale, this.scale);
            this.mouseDragged(mc2, mouseX, mouseY);
            text = -1;
            GlStateManager.pushMatrix();
            offset = (float)(this.xPosition + this.width / 2) / this.scale;
            GlStateManager.translate(offset, (float)this.yPosition / this.scale, 1.0f);
            RenderingUtil.rectangleBordered(-36.0, -2.0f / this.scale, 36.0, 12.0, 0.5, Colors.getColor(50), Colors.getColor(0));
            RenderingUtil.rectangleBordered(-35.0, -1.0f / this.scale, 35.0, 11.0, 0.5, Colors.getColor(60), Colors.getColor(0));
            RenderingUtil.drawGradient(-35.0, -1.0f / this.scale, 35.0, 11.0, Colors.getColor(25), Colors.getColor(15));
            Client.fontManager.mainMenu.drawStringWithShadow(this.displayString, -((float)Client.fontManager.mainMenu.getStringWidth(this.displayString) / 2.0f), yText, text);
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        }
    }
}
package genius.ui.screen;

import genius.util.RenderingUtil;
import genius.util.render.ColorContainer;
import genius.util.render.Colors;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class PanoramaScreen extends GuiScreen {
	private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
	public static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
	private ResourceLocation panoramaView;
	protected static int panoramaTimer;

	public void updateScreen() {
		++this.panoramaTimer;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		panoramaTimer++;
		drawRect(-1, -1, this.width + 3, this.height + 3, Colors.getColor(0, 40));
		GL11.glColor3f(1F, 1F, 1F);
		drawButtons(mouseX, mouseY);
		GL11.glColor3f(1F, 1F, 1F);
	}

	protected void drawButtons(int mouseX, int mouseY) {
		int width = 150;
		int hei = 26;
		boolean override = false;
		for (int i = 0; i < this.buttonList.size(); ++i) {
			GuiButton g = ((GuiButton) this.buttonList.get(i));
			if (override) {
				int x = g.xPosition;
				int y = g.yPosition;
				boolean over = mouseX >= x && mouseY >= y && mouseX < x + g.getButtonWidth() && mouseY < y + hei;
				if (over) fillHorizontalGrad(x, y, width, hei, new ColorContainer(5, 40, 85, 255), new ColorContainer(0, 0, 0, 0));
				else fillHorizontalGrad(x, y, width, hei, new ColorContainer(0, 0, 0, 255), new ColorContainer(0, 0, 0, 0));
				fontRendererObj.drawString(g.displayString, g.xPosition + 10, g.yPosition + (hei / 2) - 3, -1);
			}else{
				g.drawButton(mc, mouseX, mouseY);
			}
		}
	}

	protected void renderSkybox(int x, int y, float f) {
		ScaledResolution sr = new ScaledResolution(mc);
		RenderingUtil.rectangle(0,0,sr.getScaledWidth(), sr.getScaledHeight(), Colors.getColor(23,23,23));
	}

	protected void drawPanorama(int x, int y, float partial) {
		Tessellator tess = Tessellator.getInstance();
		WorldRenderer wr = tess.getWorldRenderer();
		GlStateManager.matrixMode(5889);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableCull();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		byte byt = 8;
		for (int j = 0; j < byt * byt; ++j) {
			GlStateManager.pushMatrix();
			float var8 = ((float) (j % byt) / (float) byt - 0.5F) / 64.0F;
			float var9 = ((float) (j / byt) / (float) byt - 0.5F) / 64.0F;
			float var10 = 0.0F;
			GlStateManager.translate(var8, var9, var10);
			GlStateManager.rotate(MathHelper.sin(((float) this.panoramaTimer + partial) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-((float) this.panoramaTimer + partial) * 0.1F, 0.0F, 1.0F, 0.0F);
			for (int i = 0; i < 6; ++i) {
				GlStateManager.pushMatrix();
				if (i == 1) GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				if (i == 2) GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				if (i == 3) GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				if (i == 4) GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				if (i == 5) GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				this.mc.getTextureManager().bindTexture(titlePanoramaPaths[i]);
				wr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				int l = 255 / (j + 1);
				float f3 = 0.0F;
				wr.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
				wr.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
				wr.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
				wr.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
				tess.draw();
				GlStateManager.popMatrix();
			}

			GlStateManager.popMatrix();
			GlStateManager.colorMask(true, true, true, false);
		}

		wr.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.matrixMode(5889);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.enableDepth();
	}

	protected void rotateAndBlurSkybox(float p_73968_1_) {
		this.mc.getTextureManager().bindTexture(this.panoramaView);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.colorMask(true, true, true, false);
		Tessellator tess = Tessellator.getInstance();
		WorldRenderer wr = tess.getWorldRenderer();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		GlStateManager.disableAlpha();
		byte byt = 3;
		for (int i = 0; i < byt; ++i) {
			//wr.setColorRGBA(1.0F, 1.0F, 1.0F, 1.0F / (float) (i + 1));
			int width = this.width;
			int height = this.height;
			float var8 = (float) (i - byt / 2) / 256.0F;
			wr.pos(0.0D, (double)width, (double)this.zLevel).tex((double)(0.5F - height), (double)(0.5F + height)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
			wr.pos((double)i, (double)width, (double)this.zLevel).tex((double)(0.5F - height), (double)(0.5F - height)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
			wr.pos((double)i, 0.0D, (double)this.zLevel).tex((double)(0.5F + height), (double)(0.5F - height)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
			wr.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + height), (double)(0.5F + height)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		}
		tess.draw();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);
	}
}
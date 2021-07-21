package genius;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.renderer.texture.TextureManager;
import org.apache.logging.log4j.LogManager;

public class MCHook extends Minecraft {
    public MCHook(GameConfiguration gc) {
        super(gc);
    }

    @Override
    protected void drawSplashScreen(TextureManager texMan) {
        try {
            new Client().setup();
        } catch (Exception e) {
            LogManager.getLogger().error("[" + Client.clientName + "] Error setting up the client.");
        }
        super.drawSplashScreen(texMan);
    }
}
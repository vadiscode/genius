package genius.management;

import genius.util.render.font.TTFFontRenderer;

import java.awt.*;
import java.io.InputStream;

public class FontManager {
    public TTFFontRenderer hud = null;
    public TTFFontRenderer mainMenu = null;
    public TTFFontRenderer mainMenuLogo = null;
    public TTFFontRenderer discordFont = null;
    public TTFFontRenderer v12 = null;
    public TTFFontRenderer v16b = null;
    public TTFFontRenderer f = null;
    public TTFFontRenderer fs = null;
    public TTFFontRenderer fss = null;
    public TTFFontRenderer fsmallbold = null;
    public TTFFontRenderer tsmallbold = null;
    public TTFFontRenderer header = null;
    public TTFFontRenderer subHeader = null;
    public TTFFontRenderer badCache = null;
    public TTFFontRenderer badCacheSmall = null;
    public TTFFontRenderer bindText = null;

    public void loadFonts() {
        hud = new TTFFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
        mainMenu = new TTFFontRenderer(new Font("Verdana", Font.PLAIN, 12), true, true);
        mainMenuLogo = new TTFFontRenderer(new Font("Verdana", Font.PLAIN, 24), true, true);
        v12 = new TTFFontRenderer(new Font("Verdana", Font.PLAIN, 12), true, true);
        v16b = new TTFFontRenderer(new Font("Verdana", Font.BOLD, 16), true, true);
        f = new TTFFontRenderer(new Font("Verdana", 0, 24), true, true);
        fs = new TTFFontRenderer(new Font("Verdana", 1, 11), true, true);
        fss = new TTFFontRenderer(new Font("Verdana", 0, 10), true,true);
        bindText = new TTFFontRenderer(new Font("Verdana", 0, 8), true,true);
        fsmallbold = new TTFFontRenderer(new Font("Verdana", 1, 10), true,true);
        tsmallbold = new TTFFontRenderer(new Font("Tahoma", 1, 10), true,true);
        header = new TTFFontRenderer(new Font("Myriad Pro", 0, 24), true,true);
        subHeader = new TTFFontRenderer(new Font("Myriad Pro", 0, 18), true,true);
        InputStream istream = this.getClass().getResourceAsStream("/assets/minecraft/font.ttf");
        Font myFont= null;
        try {
            myFont = Font.createFont(0, istream);
            myFont = myFont.deriveFont(0, 36.0f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        badCache = new TTFFontRenderer(myFont, true, true);
        InputStream istream2 = this.getClass().getResourceAsStream("/assets/minecraft/font.ttf");
        Font myFont2 = null;
        try {
            myFont2 = Font.createFont(0, istream2);
            myFont2 = myFont2.deriveFont(0, 18.0f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        badCacheSmall = new TTFFontRenderer(myFont2, true, true);
        InputStream istream3 = this.getClass().getResourceAsStream("/assets/minecraft/discordFont.ttf");
        Font myFont3 = null;
        try {
            myFont3 = Font.createFont(0, istream3);
            myFont3 = myFont3.deriveFont(0, 14.0f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        discordFont = new TTFFontRenderer(myFont3, true, true);
    }
}
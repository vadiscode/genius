package genius.module.impl.render;

import genius.Client;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class Chams extends Module {
    public static String DRAWMODE = "DRAWMODE";
    private static String INVISIBLES = "INVISIBLES";
    public static String COLORMODE = "COLORMODE";
    public static String PLAYERS = "PLAYERS";
    public static String ANIMALS = "OTHERS";

    public Chams(ModuleData data) {
        super(data);
        settings.put(PLAYERS, new Setting<>(PLAYERS, true, "Draw on players."));
        settings.put(ANIMALS, new Setting<>(ANIMALS, false, "Draw on mobs & animals."));
        settings.put(INVISIBLES, new Setting<>(INVISIBLES, false, "Draw on invisibles."));
        settings.put(DRAWMODE, new Setting<>(DRAWMODE, new Options("Mode", "Fill", new String[]{"Texture", "Fill"}), "Drawing mode."));
        settings.put(COLORMODE, new Setting<>(COLORMODE, new Options("Color mode", "Rainbow", new String[]{"Rainbow", "Custom"}), "Color mode."));
    }

    public static boolean isValid(Entity entity){
        Module chamsMod =  Client.getModuleManager().get(Chams.class);
        boolean players = (Boolean) chamsMod.getSetting(Chams.PLAYERS).getValue();
        boolean invis = (Boolean) chamsMod.getSetting(Chams.INVISIBLES).getValue();
        boolean others = (Boolean) chamsMod.getSetting(Chams.ANIMALS).getValue();

        if(entity.isInvisible() && !invis){

            return false;
        }
        if((players && entity instanceof EntityPlayer) || (others && (entity instanceof EntityMob || entity instanceof EntityAnimal || entity instanceof EntityVillager || entity instanceof EntityIronGolem))){
            if(entity instanceof EntityPlayerSP){

                return  mc.gameSettings.thirdPersonView != 0;
            }else{

                return true;
            }

        }else{
            return false;
        }
    }
}

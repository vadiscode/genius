package genius.module.impl.combat;

import genius.event.impl.EventPacket;
import genius.event.impl.EventUpdate;
import genius.management.command.Command;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import genius.util.Timer;
import genius.util.misc.ChatUtil;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {
    public static String MODE = "MODE";
    private String DEAD = "DEAD";
	ArrayList<Entity>entities = new ArrayList<>();
    private Timer timer = new Timer();
    Timer lastRemoved = new Timer();

    public AntiBot(ModuleData data) {
        super(data);
        settings.put(DEAD, new Setting<>(DEAD, true, "Removes dead bodies from the game."));
        settings.put(MODE, new Setting<>(MODE, new Options("Mode", "Hypixel", new String[]{"Hypixel", "Packet", "Mineplex"}), "Check method for bots."));
		setDisplayName("Anti Bot");
    }

    public static List<EntityPlayer> getInvalid() {
        return invalid;
    }
    private static List<EntityPlayer> invalid = new ArrayList<>();
    private static List<EntityPlayer> removed = new ArrayList<>();

    public void onEnable() {
        invalid.clear();
    }

    public void onDisable() {
        invalid.clear();
        if (mc.getCurrentServerData() != null && (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") || mc.getCurrentServerData().serverIP.toLowerCase().contains("mineplex"))) {
        	//Notifications.getManager().post("Antibot", "Antibot was kept enabled for your protection.", Notifications.Type.INFO);
            toggle();
        }
    }

    @Handler
    public void onPacket(EventPacket event) {
        String currentSetting = ((Options) settings.get(MODE).getValue()).getSelected();
        if (currentSetting.equalsIgnoreCase("Packet")) {
            if (event.isIncoming() && event.getPacket() instanceof S0CPacketSpawnPlayer) {
                S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.getPacket();
                double entX = packet.getX() / 32;
                double entY = packet.getY() / 32;
                double entZ = packet.getZ() / 32;
                double posX = mc.thePlayer.posX;
                double posY = mc.thePlayer.posY;
                double posZ = mc.thePlayer.posZ;
                double var7 = posX - entX;
                double var9 = posY - entY;
                double var11 = posZ - entZ;
                float distance = MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
                if (distance <= 17 && entY > mc.thePlayer.posY + 1 && (mc.thePlayer.posX != entX && mc.thePlayer.posY != entY && mc.thePlayer.posZ != entZ)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @Handler
    public void onUpdate(EventUpdate event) {
        String currentSetting = ((Options) settings.get(MODE).getValue()).getSelected();
        setSuffix(currentSetting);
        if (event.isPre()) {
            if (mc.getIntegratedServer() == null) {
                if (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && !currentSetting.equals("Hypixel")) {
                    ((Options) settings.get(MODE).getValue()).setSelected("Hypixel");
                    ChatUtil.printChat(Command.chatPrefix + "AntiBot has been set to the proper mode.");
                } else if (mc.getCurrentServerData().serverIP.toLowerCase().contains("mineplex") && !currentSetting.equals("Mineplex")) {
                    ((Options) settings.get(MODE).getValue()).setSelected("Mineplex");
                    ChatUtil.printChat(Command.chatPrefix + "AntiBot has been set to the proper mode.");
                }
            }
            if (((Boolean) settings.get(DEAD).getValue()))
                for (Object o : mc.theWorld.loadedEntityList) {
                    if (o instanceof EntityPlayer) {
                        EntityPlayer ent = (EntityPlayer) o;
                        assert ent != mc.thePlayer;
                        if (ent.isPlayerSleeping()) {
                            mc.theWorld.removeEntity(ent);
                        }
                    }
                }
        }
        if(currentSetting.equalsIgnoreCase("Mineplex") && mc.thePlayer.ticksExisted > 40)
            for(Object o : mc.theWorld.loadedEntityList){
                Entity en = (Entity)o;
                if(en instanceof EntityPlayer && !(en instanceof EntityPlayerSP)){
                    int ticks = en.ticksExisted;
                    double diffY = Math.abs(mc.thePlayer.posY - en.posY);
                    String name = en.getName();
                    String customname = en.getCustomNameTag();
                    if(customname == "" && !invalid.contains((EntityPlayer)en)){
                        invalid.add((EntityPlayer)en);
                    }
                }
            }
        if (event.isPre() && currentSetting.equalsIgnoreCase("hypixel")) {
            //Clears the invalid player list after a second to prevent false positives staying permanent.
            if(!removed.isEmpty()){
                if(lastRemoved.hasReached(1000)){
                    if(removed.size() == 1){
                        //Notifications.getManager().post("Watchdog Killer", removed.size() + " bot has been removed", Notifications.Type.INFO);
                    }else{
                        //Notifications.getManager().post("Watchdog Killer", removed.size() + " bots have been removed", Notifications.Type.INFO);
                    }
                    lastRemoved.reset();
                    removed.clear();
                }
            }
            if (!invalid.isEmpty() && timer.hasReached(1000)) {
                invalid.clear();
                timer.reset();
            }
            // Loop through entity list
            for (Object o : mc.theWorld.getLoadedEntityList()) {
                if (o instanceof EntityPlayer) {
                    EntityPlayer ent = (EntityPlayer) o;
                    //Make sure it's not the local player + they are in a worrying distance. Ignore them if they're already invalid.
                    if (ent != mc.thePlayer && !invalid.contains(ent)) {
                        //Handle current mode
                        switch (currentSetting) {
                            case "Hypixel": {

                                String formated = ent.getDisplayName().getFormattedText();
                                String custom = ent.getCustomNameTag();
                                String name = ent.getName();

                                if(ent.isInvisible() && !formated.startsWith("§c") && formated.endsWith("§r") && custom.equals(name)){
                                    double diffX = Math.abs(ent.posX - mc.thePlayer.posX);
                                    double diffY = Math.abs(ent.posY - mc.thePlayer.posY);
                                    double diffZ = Math.abs(ent.posZ - mc.thePlayer.posZ);
                                    double diffH = Math.sqrt(diffX * diffX + diffZ * diffZ);
                                    if(diffY < 13 && diffY > 10 && diffH < 3){
                                        List<EntityPlayer> list = getTabPlayerList();
                                        if(!list.contains(ent)){
                                            lastRemoved.reset();
                                            removed.add(ent);
                                            mc.theWorld.removeEntity(ent);
                                            invalid.add(ent);
                                        }
                                    }
                                }
                                //SHOP BEDWARS
                                if(!formated.startsWith("§") && formated.endsWith("§r")){
                                    invalid.add(ent);
                                }
                                if(ent.isInvisible()){
                                    //BOT INVISIBLES IN GAME
                                    if(!custom.equalsIgnoreCase("") && custom.toLowerCase().contains("§c§c") && name.contains("§c")){
                                        lastRemoved.reset();
                                        removed.add(ent);
                                        mc.theWorld.removeEntity(ent);
                                        invalid.add(ent);
                                    }
                                }
                                //WATCHDOG BOT
                                if(!custom.equalsIgnoreCase("") && custom.toLowerCase().contains("§c") && custom.toLowerCase().contains("§r")){
                                    lastRemoved.reset();
                                    removed.add(ent);
                                    mc.theWorld.removeEntity(ent);
                                    invalid.add(ent);
                                }

                                //BOT LOBBY
                                if(formated.contains("§8[NPC]")){
                                    invalid.add(ent);
                                }
                                if(!formated.contains("§c") && !custom.equalsIgnoreCase("")){

                                    invalid.add(ent);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<EntityPlayer> getTabPlayerList() {
        final NetHandlerPlayClient var4 = mc.thePlayer.sendQueue;
        final List<EntityPlayer> list = new ArrayList<>();
        final List players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
            if (info == null) {
                continue;
            }
            list.add(mc.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }

}
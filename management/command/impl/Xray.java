package genius.management.command.impl;

import genius.management.command.Command;
import genius.util.FileUtils;
import genius.util.StringConversions;
import genius.util.misc.ChatUtil;
import net.minecraft.item.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Xray extends Command {
    public Xray(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        if(args == null) {
            printUsage();
            return;
        }
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a")) {
                if(args.length == 2) {
                    if(StringConversions.isNumeric(args[1])) {
                        genius.module.impl.render.Xray.blockIDs.add(Integer.parseInt(args[1]));
                        ChatUtil.printChat(chatPrefix + "Added p" + args[1] + "] to X-Ray list.");
                    }
                } else if(mc.thePlayer.inventory.getCurrentItem() != null) {
                    Item item = mc.thePlayer.inventory.getCurrentItem().getItem();
                    int i = Item.getIdFromItem(item);
                    genius.module.impl.render.Xray.blockIDs.add(i);
                    ChatUtil.printChat(chatPrefix + "Added [" + Item.getIdFromItem(item) + "] to X-Ray list.");
                }
                Xray.saveIDs();
                return;
            } else if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")) {
                if(args.length == 2) {
                    if(StringConversions.isNumeric(args[1]) && genius.module.impl.render.Xray.blockIDs.contains(Integer.parseInt(args[1]))) {
                        genius.module.impl.render.Xray.blockIDs.remove(new Integer(Integer.parseInt(args[1])));
                        ChatUtil.printChat(chatPrefix + "Removed [" + args[1] + "] from X-Ray list.");
                    }
                } else if(mc.thePlayer.inventory.getCurrentItem() != null && genius.module.impl.render.Xray.blockIDs.contains(Item.getIdFromItem(mc.thePlayer.inventory.getCurrentItem().getItem()))) {
                    Item item = mc.thePlayer.inventory.getCurrentItem().getItem();
                    int i = Item.getIdFromItem(item);
                    genius.module.impl.render.Xray.blockIDs.remove(new Integer(i));
                    ChatUtil.printChat(chatPrefix + "Removed [" + Item.getIdFromItem(item) + "] from X-Ray list.");
                    return;
                }
                Xray.saveIDs();
                return;
            } else if(args[0].equalsIgnoreCase("clear")) {
                if(!genius.module.impl.render.Xray.blockIDs.isEmpty()) {
                    genius.module.impl.render.Xray.blockIDs.clear();
                    ChatUtil.printChat(chatPrefix + "Cleared X-Ray list!");
                    Xray.saveIDs();
                }
            }
        }
        printUsage();
    }

    private static final File CLEAN_DIR = FileUtils.getConfigFile("Xray");

    public static void saveIDs() {
        List<String> fileContent = new ArrayList<>();
        for (Integer integ : genius.module.impl.render.Xray.blockIDs) {
            fileContent.add(integ.toString());
        }
        FileUtils.write(CLEAN_DIR, fileContent, true);
    }

    public static void loadIDs() {
        try {
            List<String> fileContent = FileUtils.read(CLEAN_DIR);
            for (String line : fileContent) {
                if(StringConversions.isNumeric(line) && !genius.module.impl.render.Xray.blockIDs.contains(Integer.parseInt(line))) {
                    genius.module.impl.render.Xray.blockIDs.add(Integer.parseInt(line));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUsage() {
        return "<add/del/clear> [Block ID]";
    }
}
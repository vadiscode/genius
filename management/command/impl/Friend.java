package genius.management.command.impl;

import genius.management.command.Command;
import genius.management.friend.FriendManager;
import genius.util.misc.ChatUtil;

public class Friend extends Command {
    public Friend(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        // Intended arguements
        // 1 - Module
        // 2 - Key
        // 3 - Mask
        if (args == null || args.length < 2) {
            printUsage();
            return;
        }
        try {
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a")) {
                if (FriendManager.isFriend(args[1])) {
                    ChatUtil.printChat(chatPrefix + String.valueOf(args[1]) + " is already your friend.");
                    return;
                }
                FriendManager.removeFriend(args[1]);
                FriendManager.addFriend(args[1], args.length == 3 ? args[2] : args[1]);
                ChatUtil.printChat(chatPrefix + "Added " + args[1]);
                return;
            } else if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")) {
                if (FriendManager.isFriend(args[1])) {
                    FriendManager.removeFriend(args[1]);
                    ChatUtil.printChat(chatPrefix + "Removed friend: " + args[1]);
                    return;
                } else {
                    ChatUtil.printChat(chatPrefix + String.valueOf(args[1]) + " is not your friend.");
                    return;
                }
            }
        } catch(NullPointerException e) {
            printUsage();
        }
        printUsage();
        return;
    }

    @Override
    public String getUsage() {
        return "friend <add/del> <name> [alias]";
    }
}
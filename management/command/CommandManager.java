package genius.management.command;

import genius.management.command.impl.*;

import java.util.Collection;
import java.util.HashMap;

public class CommandManager {
    public void addCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    public Collection<Command> getCommands() {
        return commandMap.values();
    }

    public Command getCommand(String name) {
        return commandMap.get(name.toLowerCase());
    }

    public static final HashMap<String, Command> commandMap = new HashMap<String, Command>();

    public void setup() {
        new Xray(new String[]{"Xray", "x-ray","xr"}, "Add/Remove items from the blacklist.").register(this);
        new Color(new String[]{"Color", "c", "colors"}, "Change customizable colors.").register(this);
        new Toggle(new String[]{"Toggle", "t"}, "Toggles the module.").register(this);
        new Bind(new String[]{"Bind", "key", "b"}, "Send a message with your chat prefix.").register(this);
        new Friend(new String[]{"Friend", "fr", "f"}, "Add and remove friends.").register(this);
        new VClip(new String[] {"VClip", "vc", "clip"}, "Teleports you vertically.").register(this);
    }
}
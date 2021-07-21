package genius.module.impl.misc;

import genius.management.command.Command;
import genius.management.command.CommandManager;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Setting;
import genius.util.misc.ChatUtil;
import me.hippo.api.lwjeb.annotation.Handler;
import genius.event.impl.EventChat;

import java.util.Arrays;

public class Commands extends Module {

    public Commands(ModuleData data) {
        super(data);
        setHidden(true);
    }

    @Handler
    public void onChat(EventChat event) {
        //If the event does not start with the chat prefix, ignore it
        String prefix = ".";
        if (!event.getText().startsWith(prefix)) {
            return;
        }
        //If it begins with the chat prefix, cancel it.
        event.setCancelled(true);
        //Get the command and its arguments
        String commandBits[] = event.getText().substring(prefix.length()).split(" ");
        String commandName = commandBits[0];
        //Get the command and fire it with arguments
        Command command = CommandManager.commandMap.get(commandName);
        if (command == null) {
            ChatUtil.printChat(Command.chatPrefix + "\2477\"\247f" + commandName + "\2477\" is not a valid command.");
            return;
        }
        if (commandBits.length > 1) {
            String[] commandArguments = Arrays.copyOfRange(commandBits, 1, commandBits.length);
            command.fire(commandArguments);
        } else {
            command.fire(null);
        }
    }
}
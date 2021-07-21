package genius;

import genius.management.ColorManager;
import genius.management.FontManager;
import genius.management.command.CommandManager;
import genius.management.friend.FriendManager;
import genius.module.Module;
import genius.module.ModuleManager;
import genius.ui.altmanager.FileManager;
import genius.ui.click.ClickGui;
import me.hippo.api.lwjeb.bus.PubSub;
import me.hippo.api.lwjeb.configuration.BusConfigurations;
import me.hippo.api.lwjeb.configuration.config.impl.BusPubSubConfiguration;
import genius.event.Event;
import me.hippo.api.lwjeb.message.publish.impl.ExperimentalMessagePublisher;

import java.io.File;

public class Client {
    public static Client instance;

    public static final String clientName = "Genius";
    public static final PubSub<Event> EVENT_BUS;

    static {
        EVENT_BUS = new PubSub<>(new BusConfigurations.Builder().setConfiguration(BusPubSubConfiguration.class, () ->
                {
                    BusPubSubConfiguration busPubSubConfiguration = BusPubSubConfiguration.getDefault();
                    busPubSubConfiguration.setPublisher(new ExperimentalMessagePublisher<>());
                    return busPubSubConfiguration;
                }
        ).build());
    }
    public static ColorManager colorManager = new ColorManager();

    private final ModuleManager moduleManager;

    private static FileManager fileManager;
    public static ClickGui clickGui;

    public static FileManager getFileManager() { return fileManager; }

    public static CommandManager commandManager;

    private File dataDirectory;

    public static FontManager fontManager = new FontManager();

    public Client() throws Exception {
        Client.instance = this;
        commandManager = new CommandManager();
        moduleManager = new ModuleManager(Module.class);
        FriendManager.start();
    }

    public static ClickGui getClickGui() {
        return clickGui;
    }

    public void setup() {
        fontManager.loadFonts();
        commandManager.setup();
        dataDirectory = new File(Client.clientName);
        moduleManager.setup();
        (Client.fileManager = new FileManager()).loadFiles();
        Module.loadStatus();
        Module.loadSettings();
        clickGui = new ClickGui();
    }

    public static ModuleManager<Module> getModuleManager() {
        return instance.moduleManager;
    }

    public static File getDataDir() {
        return instance.dataDirectory;
    }
}
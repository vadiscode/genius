package genius.module;

import com.google.gson.annotations.Expose;
import genius.Client;
import genius.management.Saveable;
import genius.management.SubFolder;
import genius.management.keybinding.Bindable;
import genius.management.keybinding.KeyHandler;
import genius.management.keybinding.Keybind;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import genius.module.data.SettingsMap;
import genius.util.FileUtils;
import genius.util.StringConversions;
import genius.util.render.Colors;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Module extends Saveable implements Bindable, Toggleable {
    protected final static Minecraft mc = Minecraft.getMinecraft();
    @Expose
    protected final ModuleData data;
    @Expose
    protected final SettingsMap settings = new SettingsMap();
    private Keybind keybind;
    private boolean enabled;
    private String displayName;
    private String suffix;
    private boolean isHidden;

    public int getColor() {
        return color;
    }

    private int color;

    public Module(ModuleData data) {
        this.data = data;
        this.displayName = data.name;
        setFolderType(SubFolder.Module);
        setKeybind(new Keybind(this, data.key, data.mask));
        loadStatus();
        color = Colors.getColor((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()), 255);
    }

    private static final File MODULE_DIR = FileUtils.getConfigFile("Mods");
    private static final File SETTINGS_DIR = FileUtils.getConfigFile("Sets");

    public static void saveStatus() {
        List<String> fileContent = new ArrayList<>();
        for (Module module : Client.getModuleManager().getArray()) {
            fileContent.add(String.format("%s:%s:%s:%s", module.getName(), module.isEnabled(),  module.data.getKey(), module.isHidden));
        }
        FileUtils.write(MODULE_DIR, fileContent, true);
    }

    public static void saveSettings() {
        List<String> fileContent = new ArrayList<>();
        for (Module module : Client.getModuleManager().getArray()) {
            for (Setting setting : module.getSettings().values()) {
                if (!(setting.getValue() instanceof Options)) {
                    String displayName = module.getName();
                    String settingName = setting.getName();
                    String settingValue = setting.getValue().toString();
                    fileContent.add(String.format("%s:%s:%s", displayName, settingName, settingValue));
                } else {
                    String displayName = module.getName();
                    String settingName = setting.getName();
                    String settingValue = ((Options) setting.getValue()).getSelected();
                    fileContent.add(String.format("%s:%s:%s", displayName, settingName, settingValue));
                }
            }
        }
        FileUtils.write(SETTINGS_DIR, fileContent, true);
    }

    public static void loadStatus() {
        try {
            List<String> fileContent = FileUtils.read(MODULE_DIR);
            for (String line : fileContent) {
                String[] split = line.split(":");
                String displayName = split[0];
                for (Module module : Client.getModuleManager().getArray()) {
                    if (module.getName().equalsIgnoreCase(displayName)) {
                        String strEnabled = split[1];
                        boolean enabled = Boolean.parseBoolean(strEnabled);
                        String key = split[2];
                        module.setKeybind(new Keybind(module, Integer.parseInt(key)));
                        if(split.length == 4) {
                            module.isHidden = Boolean.parseBoolean(split[3]);
                        }
                        if (enabled && !module.isEnabled()) {
                            module.enabled = true;
                            Client.EVENT_BUS.subscribe(module);
                            module.onEnable();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            List<String> fileContent = FileUtils.read(SETTINGS_DIR);
            for (String line : fileContent) {
                String[] split = line.split(":");
                for (Module module : Client.getModuleManager().getArray()) {
                    if (module.getName().equalsIgnoreCase(split[0])) {
                        Setting setting = getSetting(module.getSettings(), split[1]);
                        String settingValue = split[2];
                        if (setting != null) {
                            if (setting.getValue() instanceof Number) {
                                Object newValue = (StringConversions.castNumber(settingValue, setting.getValue()));
                                if (newValue != null) {
                                    setting.setValue(newValue);
                                }
                            } // If the setting is supposed to be a string
                            else if (setting.getValue().getClass().equals(String.class)) {
                                String parsed = settingValue.toString().replaceAll("_", " ");
                                setting.setValue(parsed);
                            } // If the setting is supposed to be a boolean
                            else if (setting.getValue().getClass().equals(Boolean.class)) {
                                setting.setValue(Boolean.parseBoolean(settingValue));
                            } else if (setting.getValue().getClass().equals(Options.class)) {
                                Options dank = ((Options) setting.getValue());
                                for (String str : dank.getOptions()) {
                                    if (str.equalsIgnoreCase(settingValue)) {
                                        dank.setSelected(settingValue);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Setting getSetting(SettingsMap map, String settingText) {
        settingText = settingText.toUpperCase();
        if (map.containsKey(settingText)) {
            return map.get(settingText);
        } else {
            for (String key : map.keySet()) {
                if (key.startsWith(settingText)) {
                    return map.get(key);
                }
            }
        }
        return null;
    }

    /**
     * Handles the toggling of the module
     */
    @Override
    public void toggle() {
        enabled = !enabled;
        if (Client.getModuleManager().isSetup()) {
            saveStatus();
            saveSettings();
        }
        if (enabled) {
            Client.EVENT_BUS.subscribe(this);
            onEnable();
        } else {
            // Save module data
            Client.EVENT_BUS.unsubscribe(this);
            onDisable();
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onBindPress() {
        toggle();
    }

    @Override
    public void onBindRelease() {

    }

    /**
     * Sets the current keybind to another.
     *
     * @param newBind
     */
    @Override
    public void setKeybind(Keybind newBind) {
        if (newBind == null) {
            return;
        }
        // Client init
        if (keybind == null) {
            keybind = newBind;
            KeyHandler.register(keybind);
            return;
        }
        // Not client setup
        boolean sameKey = newBind.getKeyInt() == keybind.getKeyInt();
        boolean sameMask = newBind.getMask() == keybind.getMask();
        if (sameKey && !sameMask) {
            KeyHandler.update(this, keybind, newBind);
        } else if (!sameKey) {
            if (KeyHandler.keyHasBinds(keybind.getKeyInt())) {
                KeyHandler.unregister(this, keybind);
            }
            KeyHandler.register(newBind);
        }
        keybind.update(newBind);
        data.key = keybind.getKeyInt();
        data.mask = keybind.getMask();
        if (Client.getModuleManager().isSetup()) {
            saveStatus();
        }
        /*
         * boolean noBind = newBind.getKeyInt() == Keyboard.CHAR_NONE; boolean
		 * isRegistered = KeyHandler.isRegistered(keybind); if (isRegistered) {
		 * if (noBind) { // Unegister the now-unused keybind
		 * KeyHandler.unregister(keybind); } else { // Update the existing
		 * keybind with new information int curKey = keybind.getKeyInt(); int
		 * newKey = newBind.getKeyInt(); if (curKey == newKey) {
		 * KeyHandler.update(keybind, newBind); } else {
		 * KeyHandler.unregister(keybind); KeyHandler.register(newBind); } }
		 * }else{ KeyHandler.register(newBind); } keybind.update(newBind); // if
		 * (!isRegistered && !noBind) { // Register the new keybind
		 * //KeyHandler.register(keybind); // } if (keybind != null) { data.key
		 * = keybind.getKeyInt(); }
		 */
    }

    /**
     * TODO: UN FUCK THE GOD FUCKING KEYBINDS
     * <p>
     * What I want to happen: - Client loads up - Module init - - Checks if
     * settings exist - - - If so load them and use the information for making
     * the keybind - - - Else use the default as provided in the constructor
     * <p>
     * Then later on: .bind Module key
     * <p>
     * - Check if the new key and current key match, if so just update the bind
     * (IE: adding a mask) - - If the keys dont match, unregister the old one,
     * register the new one
     */

    public static int getColor(ModuleData.Type type) {
        int color = -1;
        switch (type) {
            case Combat:
                color = Colors.getColor(135, 39, 39);
                break;
            case Movement:
                color = Colors.getColor(161, 180, 196);
                break;
            case Player:
                color = Colors.getColor(90, 90, 90);
                break;
            case Render:
                color = Colors.getColor(27, 198, 190);
                break;
            case Misc:
                color = Colors.getColor(90, 90, 90);
                break;
            default:
                break;
        }
        return color;
    }

    public Keybind getKeybind() {
        return keybind;
    }

    /**
     * Adds a setting to the settings map
     *
     * @param key
     * @param setting
     * @return Returns if the setting was added
     */
    public boolean addSetting(String key, Setting setting) {
        if (settings.containsKey(key)) {
            return false;
        } else {
            settings.put(key, setting);
            return true;
        }
    }
    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public Setting getSetting(String key) { return settings.get(key); }

    public String getSuffix() { return suffix; }

    public void setSuffix(String suffix) { this.suffix = suffix; }

    public SettingsMap getSettings() {
        return settings;
    }

    public String getName() { return data.name; }

    public String getDescription() { return data.description; }

    public ModuleData.Type getType() { return data.type; }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
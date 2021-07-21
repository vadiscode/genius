package genius.module;

import genius.management.AbstractManager;
import genius.management.keybinding.KeyMask;
import genius.module.data.ModuleData;
import genius.module.impl.combat.*;
import genius.module.impl.misc.ChatBypass;
import genius.module.impl.misc.Commands;
import genius.module.impl.misc.MCF;
import genius.module.impl.movement.*;
import genius.module.impl.player.AutoTool;
import genius.module.impl.player.FastBreak;
import genius.module.impl.player.InvWalk;
import genius.module.impl.render.*;
import org.lwjgl.input.Keyboard;

public class ModuleManager<E extends Module> extends AbstractManager<Module> {
	private boolean setup;

	public ModuleManager(Class<Module> clazz) {
		super(clazz, 0);
	}

	/**
	 * Sets up the ModuleManager.
	 * <hr>
	 * Two ways to initiate modules internally:<br>
	 * Modify constructor: so that it looks like: <i>super(clazz, numOfMods)</i>
	 * ; Initiate modules by array index like so:
	 * 
	 * <pre>
	 * array[0] = new ModuleExample(...);
	 * array[1] = new ModuleExample(...);
	 * array[2] = new ModuleExample(...);
	 * </pre>
	 * 
	 * or use the add method (Slight addition to startup time)
	 * 
	 * <pre>
	 *                                 
	 * add(new ModuleExample(...);             
	 * add(new ModuleExample(...);               
	 * add(new ModuleExample(...);
	 * </pre>
	 */
	@Override
	public void setup() {
		add(new KillAura(new ModuleData(ModuleData.Type.Combat, "KillAura", "Attacks entities for you.")));
		add(new TargetStrafe(new ModuleData(ModuleData.Type.Combat, "TargetStrafe", "Strafing on entity's.")));
		add(new AntiBot(new ModuleData(ModuleData.Type.Combat, "AntiBot", "Anticheat bots remover.")));
		add(new Disabler(new ModuleData(ModuleData.Type.Combat, "Disabler", "Disables anticheat for you.")));
		add(new AntiVoid(new ModuleData(ModuleData.Type.Movement, "AntiVoid", "Prevents you from falling into the void.")));
		add(new FastBreak(new ModuleData(ModuleData.Type.Player, "FastBreak", "Increase your block breaking speed.")));
		add(new AntiVelocity(new ModuleData(ModuleData.Type.Combat, "AntiVelocity", "Reduce/Remove velocity.")));
		add(new AutoArmor(new ModuleData(ModuleData.Type.Combat, "AutoArmor", "Automatically equips best armor.")));
		add(new Brightness(new ModuleData(ModuleData.Type.Render, "Brightness", "Lets you see in the dark.")));
		add(new AutoTool(new ModuleData(ModuleData.Type.Player, "AutoTool", "Switches to best tool.")));
		add(new InvWalk(new ModuleData(ModuleData.Type.Player, "InvWalk", "Allows you walk through Gui's.")));
		add(new Criticals(new ModuleData(ModuleData.Type.Combat, "Criticals", "Forces critical attack each hit.")));
		add(new Sprint(new ModuleData(ModuleData.Type.Movement, "Sprint", "Automatically sprints for you.")));
		add(new Flight(new ModuleData(ModuleData.Type.Movement, "Flight", "Makes you like a bird.")));
		add(new NoSlowdown(new ModuleData(ModuleData.Type.Movement, "NoSlowdown", "Movement isn't reduced when using an item.")));
		add(new Speed(new ModuleData(ModuleData.Type.Movement, "Speed", "Movement speed get hacked.")));
		add(new Hud(new ModuleData(ModuleData.Type.Render, "Hud", "Open a hud.")));
		add(new TabGUI(new ModuleData(ModuleData.Type.Render, "TabGUI", "Open a TabGUI.")));
		add(new Animations(new ModuleData(ModuleData.Type.Render, "Animations", "Item animations.")));
		add(new Chams(new ModuleData(ModuleData.Type.Render, "Chams", "Highlighting entity's.")));
		add(new Glow(new ModuleData(ModuleData.Type.Render, "Glow", "Glowing players.")));
		add(new ChatBypass(new ModuleData(ModuleData.Type.Misc, "ChatBypass", "Bypass chat filters.")));
		add(new Commands(new ModuleData(ModuleData.Type.Misc, "Commands", "Commands, but for chat.")));
		add(new Xray(new ModuleData(ModuleData.Type.Render, "Xray", "Sends brain waves to blocks.", Keyboard.KEY_X, KeyMask.None)));
		add(new Crosshair(new ModuleData(ModuleData.Type.Render, "Crosshair", "Draws a custom crosshair.")));
		add(new ESP(new ModuleData(ModuleData.Type.Render, "ESP", "Wallhacks on players.")));
		add(new NoHurtCam(new ModuleData(ModuleData.Type.Render, "NoHurtCam", "Removes hurt cam effect.")));
		add(new MCF(new ModuleData(ModuleData.Type.Misc, "MCF", "Middle click friends.")));

		setup = true;
		if (!get(Hud.class).isEnabled()) {
			get(Hud.class).toggle();
		}
		if (!get(Commands.class).isEnabled()) {
			get(Commands.class).toggle();
		}
		Module.loadStatus();
		Module.loadSettings();
	}

	public boolean isSetup() {
		return setup;
	}

	public boolean isEnabled(Class<? extends Module> clazz) {
		Module module = get(clazz);
		return module != null && module.isEnabled();
	}

	public Module get(String name) {
		for (Module module : getArray()) {
			if (module.getName().toLowerCase().equals(name.toLowerCase())) {
				return module;
			}
		}
		return null;
	}
}
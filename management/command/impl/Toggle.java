package genius.management.command.impl;

import genius.Client;
import genius.management.command.Command;
import genius.module.Module;
import genius.util.misc.ChatUtil;

public class Toggle extends Command {

	/**
	 * @param names
	 * @param description
	 */
	public Toggle(String[] names, String description) {
		super(names, description);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.arithmo.command.Fireable#fire(java.lang.String[])
	 */
	@Override
	public void fire(String[] args) {
		if (args == null) {
			printUsage();
			return;
		}
		Module module = null;
		if (args.length > 0) {
			module = Client.getModuleManager().get(args[0]);
		}
		if (module == null) {
			printUsage();
			return;
		}
		if (args.length == 1) {
			module.toggle();
			ChatUtil.printChat(chatPrefix + module.getDisplayName() + " has been" + (module.isEnabled() ? "\247a enabled.": "\247c disabled."));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.arithmo.command.Command#getUsage()
	 */
	@Override
	public String getUsage() {
		
		return "toggle <module name>";
	}
}
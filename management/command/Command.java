package genius.management.command;

import genius.util.misc.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;

public abstract class Command implements Fireable {
	private final String[] names;
	private final String description;
	public final static String chatPrefix = "\247c[\247fG\247c]\2477 ";
	public Minecraft mc = Minecraft.getMinecraft();

	public Command(String[] names, String description) {
		this.names = names;
		this.description = description;
	}

	protected void printDescription() {
		String message = chatPrefix + getName() + EnumChatFormatting.GRAY + ": " + description;
		ChatUtil.printChat(message);
	}

	protected void printUsage() {
		String message = chatPrefix + getName() + EnumChatFormatting.GRAY + ": " + getUsage();
		ChatUtil.printChat(message);
	}

	public void register(CommandManager manager) {
		for (String name : names) {
			manager.addCommand(name.toLowerCase(), this);
		}
	}

	public abstract String getUsage();

	public String getName() {
		return names[0];
	}

	public boolean isMatch(String text) {
		return Arrays.asList(names).contains(text.toLowerCase());
	}

	public String getDescription() {
		return description;
	}
}
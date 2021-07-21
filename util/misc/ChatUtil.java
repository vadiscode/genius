package genius.util.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static void printChat(String text) {
		mc.thePlayer.addChatComponentMessage(new ChatComponentText(text));
	}

	public static void sendChat_NoFilter(String text) {
		mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(text));
	}

	public static void sendChat(String text) {
		mc.thePlayer.sendChatMessage(text);
	}
}
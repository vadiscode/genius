package genius.module.impl.misc;

import genius.event.impl.EventPacket;
import genius.module.Module;
import genius.module.data.ModuleData;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.util.ArrayList;

public class ChatBypass extends Module {
    public ChatBypass(ModuleData data) {
        super(data);
        setDisplayName("Chat Bypass");
    }

    @Handler
    public void onPacket(EventPacket event) {
        if (event.isOutgoing() && event.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage p = (C01PacketChatMessage) event.getPacket();
            String finalmsg = "";
            ArrayList<String> splitshit = new ArrayList();
            String[] msg = p.getMessage().split(" ");
            for (int i = 0; i < msg.length; i++) {
                char[] characters = msg[i].toCharArray();
                for (int i2 = 0; i2 < characters.length; i2++) {
                    splitshit.add(characters[i2] + "\u061C");
                }
                splitshit.add(" ");
            }
            for (int i = 0; i < splitshit.size(); i++) {
                finalmsg += splitshit.get(i);
            }
            if (p.getMessage().startsWith("%")) {
                p.setMessage(finalmsg.replaceFirst("%", ""));
                splitshit.clear();
            }
        }
    }
}

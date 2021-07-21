package genius.module.impl.misc;

import genius.event.impl.EventMouse;
import genius.management.command.Command;
import genius.management.friend.FriendManager;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.util.misc.ChatUtil;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
    public MCF(ModuleData data) {
        super(data);
    }

    @Handler
    public void onMouse(EventMouse event) {
        if (event.getButtonID() == 2 && Mouse.getEventButtonState() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
            if (FriendManager.isFriend(entityPlayer.getName())) {
                ChatUtil.printChat(Command.chatPrefix + "\247b" + entityPlayer.getName() + "\2477 has been \247cunfriended.");
                FriendManager.removeFriend(entityPlayer.getName());
            } else {
                ChatUtil.printChat(Command.chatPrefix + "\247b" + entityPlayer.getName() + "\2477 has been \247afriended.");
                FriendManager.addFriend(entityPlayer.getName(), entityPlayer.getName());
            }
        }
    }
}
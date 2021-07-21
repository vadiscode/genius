package genius.module.impl.player;

import genius.module.Module;
import genius.module.data.ModuleData;
import me.hippo.api.lwjeb.annotation.Handler;
import genius.event.impl.EventPacket;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;

public class AutoTool extends Module {
	public AutoTool(ModuleData data) {
		super(data);
		this.setDisplayName("Auto Tool");
	}

	@Handler
	public void onPacket(EventPacket event) {
		// Returns if the packet isn't a digging packet and the event type isn't a pre event.
		if(!(event.getPacket() instanceof C07PacketPlayerDigging && event.isPre()))
			return;

		// The packet being sent in the form of a {@link C07PacketPlayerDigging}.
		C07PacketPlayerDigging packet = (C07PacketPlayerDigging) event.getPacket();

		// If the packet is starting the block break.
		if(packet.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK)
			autotool(packet.getPosition());
	}

	private static void autotool(BlockPos position){
		// The block type being broken.
		Block block = Minecraft.getMinecraft().theWorld.getBlockState(position).getBlock();

		// The item index of the strongest item in the player's hotbar.
		int itemIndex = getStrongestItem(block);

		// Returns if item index is less than 0 (Invalid).
		if(itemIndex < 0) return;

		// The item strength of the strongest item.
		float itemStrength = getStrengthAgainstBlock(block, Minecraft.getMinecraft().thePlayer.inventory.mainInventory[itemIndex]);

		// Returns if the held item is the strongest.
		if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null && getStrengthAgainstBlock(block, Minecraft.getMinecraft().thePlayer.getHeldItem()) >= itemStrength)
			return;

		// Sets the held item.
		Minecraft.getMinecraft().thePlayer.inventory.currentItem = itemIndex;
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(itemIndex));
	}

	private static int getStrongestItem(Block block){
		// The strength of the strongest item.
		float strength = Float.NEGATIVE_INFINITY;

		// The strongest item index.
		int strongest = -1;

		// Iterates through all items in the player's hotbar.
		for(int i = 0; i < 8; i++){
			// The itemstack in i slot.
			ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];

			// Continues if the slot is empty.
			if(itemStack == null || itemStack.getItem() == null) continue;

			// The strength of the item.
			float itemStrength = getStrengthAgainstBlock(block, itemStack);

			// Continues if the item strength is lower than the current highest strength.
			if(itemStrength <= strength || itemStrength == 1) continue;

			// Sets the strongest item index.
			strongest = i;

			// Sets the highest strength.
			strength = itemStrength;
		}

		return strongest;
	}

	/**
	 * Gets the given item's strength against the given block.
	 *
	 * @param block The block to get the strength against.
	 * @param item  The item to get the strength of.
	 * @return The item strength with enchantments.
	 */
	public static float getStrengthAgainstBlock(Block block, ItemStack item){
		// The item strength.
		float strength = item.getStrVsBlock(block);

		// Returns the strength if the item doesn't have a strength modifier or if the strength is 1.
		if(!EnchantmentHelper.getEnchantments(item).containsKey(Enchantment.efficiency.effectId) || strength == 1)
			return strength;

		// The enchantment level.
		int enchantLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, item);

		// Returns the item strength.
		return strength + (enchantLevel * enchantLevel + 1);
	}
}
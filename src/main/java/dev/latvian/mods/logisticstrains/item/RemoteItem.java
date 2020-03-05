package dev.latvian.mods.logisticstrains.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * @author LatvianModder
 */
public class RemoteItem extends Item
{
	public RemoteItem(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
}
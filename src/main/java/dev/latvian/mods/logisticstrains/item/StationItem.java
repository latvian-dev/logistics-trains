package dev.latvian.mods.logisticstrains.item;

import dev.latvian.mods.logisticstrains.LogisticsTrains;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author LatvianModder
 */
public class StationItem extends BlockItem
{
	public StationItem(Block block)
	{
		super(block, new Item.Properties().group(LogisticsTrains.instance.itemGroup));
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return stack.getTag() != null && stack.getTag().hasUniqueId("StationID") ? 1 : 64;
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return stack.getTag() != null && stack.getTag().hasUniqueId("StationID");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		if (stack.getTag() != null && stack.getTag().hasUniqueId("StationID"))
		{
			tooltip.add(new StringTextComponent(stack.getTag().getUniqueId("StationID").toString()).mergeStyle(TextFormatting.DARK_GRAY));
		}
	}
}
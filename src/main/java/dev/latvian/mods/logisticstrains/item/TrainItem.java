package dev.latvian.mods.logisticstrains.item;

import dev.latvian.mods.logisticstrains.LogisticsTrains;
import net.minecraft.item.Item;

/**
 * @author LatvianModder
 */
public class TrainItem extends Item
{
	public TrainItem()
	{
		super(new Properties().group(LogisticsTrains.itemGroup).maxStackSize(16));
	}
}
package dev.latvian.mods.logisticstrains.item;

import dev.latvian.mods.logisticstrains.LogisticsTrains;
import dev.latvian.mods.logisticstrains.block.LTBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * @author LatvianModder
 */
public class LTItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "logisticstrains");

	public static final RegistryObject<Item> REMOTE = ITEMS.register("remote", RemoteItem::new);

	private static Supplier<BlockItem> railItem(RegistryObject<Block> b)
	{
		return () -> new BlockItem(b.get(), new Item.Properties().group(LogisticsTrains.instance.itemGroup));
	}

	public static final RegistryObject<Item> STATION = ITEMS.register("station", () -> new StationItem(LTBlocks.STATION.get()));
	public static final RegistryObject<Item> RAIL = ITEMS.register("rail", railItem(LTBlocks.RAIL));
	public static final RegistryObject<Item> SLANTING_RAIL = ITEMS.register("slanting_rail", railItem(LTBlocks.SLANTING_RAIL));
	public static final RegistryObject<Item> VERTICAL_RAIL = ITEMS.register("vertical_rail", railItem(LTBlocks.VERTICAL_RAIL));
	public static final RegistryObject<Item> CROSS_RAIL = ITEMS.register("cross_rail", railItem(LTBlocks.CROSS_RAIL));
	public static final RegistryObject<Item> DOUBLE_RAIL_NESW = ITEMS.register("double_rail_nesw", railItem(LTBlocks.DOUBLE_RAIL_NESW));
	public static final RegistryObject<Item> DOUBLE_RAIL_NWSE = ITEMS.register("double_rail_nwse", railItem(LTBlocks.DOUBLE_RAIL_NWSE));
}
package dev.latvian.mods.logisticstrains;

import dev.latvian.mods.logisticstrains.block.CrossLTRailBlock;
import dev.latvian.mods.logisticstrains.block.DoubleLTRailBlockNESW;
import dev.latvian.mods.logisticstrains.block.DoubleLTRailBlockNWSE;
import dev.latvian.mods.logisticstrains.block.LTBlocks;
import dev.latvian.mods.logisticstrains.block.LTRailBlock;
import dev.latvian.mods.logisticstrains.block.SlantingLTRailBlock;
import dev.latvian.mods.logisticstrains.block.StationBlock;
import dev.latvian.mods.logisticstrains.block.VerticalLTRailBlock;
import dev.latvian.mods.logisticstrains.block.entity.StationEntity;
import dev.latvian.mods.logisticstrains.client.LTClient;
import dev.latvian.mods.logisticstrains.item.LTItems;
import dev.latvian.mods.logisticstrains.item.RemoteItem;
import dev.latvian.mods.logisticstrains.item.StationItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author LatvianModder
 */
@Mod("logisticstrains")
public class LogisticsTrains
{
	public static LogisticsTrains instance;
	public ItemGroup itemGroup;

	public LogisticsTrains()
	{
		instance = this;
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::registerBlocks);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::registerBlockEntities);

		itemGroup = new ItemGroup("logisticstrains")
		{
			@Override
			@OnlyIn(Dist.CLIENT)
			public ItemStack createIcon()
			{
				return new ItemStack(LTItems.REMOTE);
			}
		};

		//noinspection Convert2MethodRef
		DistExecutor.runForDist(() -> () -> new LTClient(), () -> () -> new LTCommon()).init();
	}

	private void registerItems(RegistryEvent.Register<Item> event)
	{
		Item[] items = {
				new RemoteItem(new Item.Properties().group(itemGroup).maxStackSize(1)).setRegistryName("remote"),
		};

		Item[] blockItems = {
				new StationItem(LTBlocks.STATION, new Item.Properties().group(itemGroup)).setRegistryName("station"),
				new BlockItem(LTBlocks.RAIL, new Item.Properties().group(itemGroup)).setRegistryName("rail"),
				new BlockItem(LTBlocks.SLANTING_RAIL, new Item.Properties().group(itemGroup)).setRegistryName("slanting_rail"),
				new BlockItem(LTBlocks.VERTICAL_RAIL, new Item.Properties().group(itemGroup)).setRegistryName("vertical_rail"),
				new BlockItem(LTBlocks.CROSS_RAIL, new Item.Properties().group(itemGroup)).setRegistryName("cross_rail"),
				new BlockItem(LTBlocks.DOUBLE_RAIL_NESW, new Item.Properties().group(itemGroup)).setRegistryName("double_rail_nesw"),
				new BlockItem(LTBlocks.DOUBLE_RAIL_NWSE, new Item.Properties().group(itemGroup)).setRegistryName("double_rail_nwse"),
		};

		event.getRegistry().registerAll(items);
		event.getRegistry().registerAll(blockItems);
	}

	private Block.Properties createRailProperties()
	{
		return Block.Properties.create(Material.IRON, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.4F).sound(SoundType.METAL).notSolid();
	}

	private void registerBlocks(RegistryEvent.Register<Block> event)
	{
		Block[] blocks = {
				new StationBlock(createRailProperties().hardnessAndResistance(2F)).setRegistryName("station"),
				new LTRailBlock(createRailProperties()).setRegistryName("rail"),
				new SlantingLTRailBlock(createRailProperties()).setRegistryName("slanting_rail"),
				new VerticalLTRailBlock(createRailProperties()).setRegistryName("vertical_rail"),
				new CrossLTRailBlock(createRailProperties()).setRegistryName("cross_rail"),
				new DoubleLTRailBlockNESW(createRailProperties()).setRegistryName("double_rail_nesw"),
				new DoubleLTRailBlockNWSE(createRailProperties()).setRegistryName("double_rail_nwse"),
		};

		event.getRegistry().registerAll(blocks);
	}

	private void registerBlockEntities(RegistryEvent.Register<TileEntityType<?>> event)
	{
		TileEntityType[] entityTypes = {
				TileEntityType.Builder.create(StationEntity::new, LTBlocks.STATION).build(null).setRegistryName("station"),
		};

		event.getRegistry().registerAll(entityTypes);
	}
}
package dev.latvian.mods.logisticstrains;

import dev.latvian.mods.logisticstrains.block.LTAscendingRailBlock;
import dev.latvian.mods.logisticstrains.block.LTBlocks;
import dev.latvian.mods.logisticstrains.block.LTRailBlock;
import dev.latvian.mods.logisticstrains.block.LTVerticalRailBlock;
import dev.latvian.mods.logisticstrains.block.TrainStationBlock;
import dev.latvian.mods.logisticstrains.block.entity.TrainStationEntity;
import dev.latvian.mods.logisticstrains.client.LTClient;
import dev.latvian.mods.logisticstrains.item.LTItems;
import dev.latvian.mods.logisticstrains.item.RemoteItem;
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
				new BlockItem(LTBlocks.RAIL, new Item.Properties().group(itemGroup)).setRegistryName("rail"),
				new BlockItem(LTBlocks.TRAIN_STATION, new Item.Properties().group(itemGroup)).setRegistryName("train_station"),
		};

		event.getRegistry().registerAll(items);
		event.getRegistry().registerAll(blockItems);
	}

	private void registerBlocks(RegistryEvent.Register<Block> event)
	{
		Block[] blocks = {
				new LTRailBlock(Block.Properties.create(Material.IRON, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.4F).sound(SoundType.METAL).notSolid()).setRegistryName("rail"),
				new LTAscendingRailBlock(Block.Properties.create(Material.IRON, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.4F).sound(SoundType.METAL).notSolid()).setRegistryName("ascending_rail"),
				new LTVerticalRailBlock(Block.Properties.create(Material.IRON, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.4F).sound(SoundType.METAL).notSolid()).setRegistryName("vertical_rail"),
				new TrainStationBlock(Block.Properties.create(Material.IRON, MaterialColor.GRAY).hardnessAndResistance(1F).sound(SoundType.METAL)).setRegistryName("train_station"),
		};

		event.getRegistry().registerAll(blocks);
	}

	private void registerBlockEntities(RegistryEvent.Register<TileEntityType<?>> event)
	{
		TileEntityType[] entityTypes = {
				TileEntityType.Builder.create(TrainStationEntity::new, LTBlocks.TRAIN_STATION).build(null).setRegistryName("train_station"),
		};

		event.getRegistry().registerAll(entityTypes);
	}
}
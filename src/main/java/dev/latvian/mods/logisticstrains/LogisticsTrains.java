package dev.latvian.mods.logisticstrains;

import dev.latvian.mods.logisticstrains.block.LTBlocks;
import dev.latvian.mods.logisticstrains.block.entity.LTBlockEntities;
import dev.latvian.mods.logisticstrains.client.LTClient;
import dev.latvian.mods.logisticstrains.item.LTItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
		LTBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		LTItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		LTBlockEntities.BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

		itemGroup = new ItemGroup("logisticstrains")
		{
			@Override
			@OnlyIn(Dist.CLIENT)
			public ItemStack createIcon()
			{
				return new ItemStack(LTItems.REMOTE.get());
			}
		};

		DistExecutor.safeRunForDist(() -> LTClient::new, () -> LTCommon::new).init();
	}
}
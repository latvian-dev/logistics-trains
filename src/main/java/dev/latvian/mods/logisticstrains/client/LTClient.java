package dev.latvian.mods.logisticstrains.client;

import dev.latvian.mods.logisticstrains.LTCommon;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author LatvianModder
 */
public class LTClient extends LTCommon
{
	@Override
	public void init()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	private void setup(FMLClientSetupEvent event)
	{
		//RenderTypeLookup.setRenderLayer(LuxNetBlocks.MIRROR, RenderType.getCutout());
		//ClientRegistry.bindTileEntityRenderer(LTBlockEntities.TRAIN_STATION, TrainStationRenderer::new);
	}
}
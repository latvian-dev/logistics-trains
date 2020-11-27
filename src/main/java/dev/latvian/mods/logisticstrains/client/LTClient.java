package dev.latvian.mods.logisticstrains.client;

import dev.latvian.mods.logisticstrains.LTCommon;
import dev.latvian.mods.logisticstrains.entity.LTEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
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
		EntityRendererManager manager = Minecraft.getInstance().getRenderManager();
		manager.register(LTEntities.TRAIN.get(), new TrainRenderer(manager));
	}
}
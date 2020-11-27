package dev.latvian.mods.logisticstrains.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.latvian.mods.logisticstrains.entity.TrainEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;

/**
 * @author LatvianModder
 */
public class TrainRenderer extends EntityRenderer<TrainEntity>
{
	protected TrainRenderer(EntityRendererManager renderManager)
	{
		super(renderManager);
		shadowSize = 0.8F;
	}

	@Override
	public void render(TrainEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F + entityYaw));
		matrixStackIn.translate(-0.5D, 0D, -0.5D);
		IBakedModel bakedModel = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getModel(new ModelResourceLocation("logisticstrains:model/item/train#inventory"));
		Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(matrixStackIn.getLast(), bufferIn.getBuffer(RenderType.getSolid()), null, bakedModel, 1F, 1F, 1F, packedLightIn, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
		matrixStackIn.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(TrainEntity entity)
	{
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
}

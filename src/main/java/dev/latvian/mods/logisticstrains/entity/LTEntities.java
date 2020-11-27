package dev.latvian.mods.logisticstrains.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author LatvianModder
 */
public class LTEntities
{
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "logisticstrains");

	public static final RegistryObject<EntityType<TrainEntity>> TRAIN = ENTITIES.register("train", () -> EntityType.Builder.<TrainEntity>create((type, world) -> new TrainEntity(world), EntityClassification.MISC).size(0.8F, 0.8F).trackingRange(10).build("train"));
}
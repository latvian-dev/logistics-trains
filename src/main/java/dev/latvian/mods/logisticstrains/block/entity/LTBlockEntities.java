package dev.latvian.mods.logisticstrains.block.entity;

import dev.latvian.mods.logisticstrains.block.LTBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author LatvianModder
 */
public class LTBlockEntities
{
	public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "logisticstrains");

	public static final RegistryObject<TileEntityType<StationEntity>> STATION = BLOCK_ENTITIES.register("station", () -> TileEntityType.Builder.create(StationEntity::new, LTBlocks.STATION.get()).build(null));
}
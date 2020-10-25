package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author LatvianModder
 */
public class LTBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "logisticstrains");

	public static final RegistryObject<Block> STATION = BLOCKS.register("station", StationBlock::new);
	public static final RegistryObject<Block> RAIL = BLOCKS.register("rail", LTRailBlock::new);
	public static final RegistryObject<Block> SLANTING_RAIL = BLOCKS.register("slanting_rail", SlantingLTRailBlock::new);
	public static final RegistryObject<Block> VERTICAL_RAIL = BLOCKS.register("vertical_rail", VerticalLTRailBlock::new);
	public static final RegistryObject<Block> CROSS_RAIL = BLOCKS.register("cross_rail", CrossLTRailBlock::new);
	public static final RegistryObject<Block> DOUBLE_RAIL_NESW = BLOCKS.register("double_rail_nesw", DoubleLTRailBlockNESW::new);
	public static final RegistryObject<Block> DOUBLE_RAIL_NWSE = BLOCKS.register("double_rail_nwse", DoubleLTRailBlockNWSE::new);
}
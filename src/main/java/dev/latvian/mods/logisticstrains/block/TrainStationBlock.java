package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * @author LatvianModder
 */
public class TrainStationBlock extends Block
{
	public TrainStationBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	@Deprecated
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		return ActionResultType.SUCCESS;
	}
}
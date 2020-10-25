package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

/**
 * @author LatvianModder
 */
public class DoubleLTRailBlockNESW extends HorizontalLTRailBlock
{
	private static final VoxelShape SHAPE = VoxelShapes.or(RailShape.turnNEShape(), RailShape.turnSWShape());
	private static final int[] REDIRECT = {-1, -1, 3, 2, 5, 4};

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}

	@Override
	public int redirect(BlockState state, World world, BlockPos pos, int from)
	{
		return REDIRECT[from];
	}

	@Override
	@Deprecated
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target)
	{
		if (player.isCrouching())
		{
			world.setBlockState(pos, LTBlocks.DOUBLE_RAIL_NWSE.get().getDefaultState().with(BlockStateProperties.WATERLOGGED, state.get(BlockStateProperties.WATERLOGGED)), Constants.BlockFlags.DEFAULT);
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}
}
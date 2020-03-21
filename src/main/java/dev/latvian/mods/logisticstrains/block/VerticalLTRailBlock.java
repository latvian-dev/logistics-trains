package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

/**
 * @author LatvianModder
 */
public class VerticalLTRailBlock extends LTRailBlockBase
{
	private static final VoxelShape N_SHAPE = Block.makeCuboidShape(6, 0, 0, 10, 16, 3);
	private static final VoxelShape S_SHAPE = Block.makeCuboidShape(6, 0, 13, 10, 16, 16);
	private static final VoxelShape W_SHAPE = Block.makeCuboidShape(0, 0, 6, 3, 16, 10);
	private static final VoxelShape E_SHAPE = Block.makeCuboidShape(13, 0, 6, 16, 16, 10);

	public VerticalLTRailBlock(Properties properties)
	{
		super(properties);
		setDefaultState(getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.SOUTH));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HorizontalBlock.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
	}

	@Override
	@Deprecated
	public BlockState rotate(BlockState state, Rotation rotation)
	{
		return state.with(HorizontalBlock.HORIZONTAL_FACING, rotation.rotate(state.get(HorizontalBlock.HORIZONTAL_FACING)));
	}

	@Override
	@Deprecated
	public BlockState mirror(BlockState state, Mirror mirror)
	{
		return state.rotate(mirror.toRotation(state.get(HorizontalBlock.HORIZONTAL_FACING)));
	}

	@Override
	public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity)
	{
		return true;
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		switch (state.get(HorizontalBlock.HORIZONTAL_FACING))
		{
			case NORTH:
				return N_SHAPE;
			case WEST:
				return W_SHAPE;
			case EAST:
				return E_SHAPE;
			default:
				return S_SHAPE;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		if (context.getFace().getAxis() == Direction.Axis.Y)
		{
			BlockState state = context.getWorld().getBlockState(context.getPos().offset(context.getFace().getOpposite()));

			if (state.getBlock() == LTBlocks.VERTICAL_RAIL)
			{
				return super.getStateForPlacement(context).with(HorizontalBlock.HORIZONTAL_FACING, state.get(HorizontalBlock.HORIZONTAL_FACING));
			}

			return super.getStateForPlacement(context).with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
		}

		return super.getStateForPlacement(context).with(HorizontalBlock.HORIZONTAL_FACING, context.getFace().getOpposite());
	}
}
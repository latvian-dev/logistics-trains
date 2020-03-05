package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

/**
 * @author LatvianModder
 */
public class LTVerticalRailBlock extends LTRailBaseBlock
{
	private static final VoxelShape N_SHAPE = Block.makeCuboidShape(6, 0, 0, 10, 16, 3);
	private static final VoxelShape S_SHAPE = Block.makeCuboidShape(6, 0, 0, 10, 16, 3);
	private static final VoxelShape W_SHAPE = Block.makeCuboidShape(6, 0, 0, 10, 16, 3);
	private static final VoxelShape E_SHAPE = Block.makeCuboidShape(6, 0, 0, 10, 16, 3);

	public LTVerticalRailBlock(Properties properties)
	{
		super(properties);
		setDefaultState(stateContainer.getBaseState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.SOUTH).with(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	@Deprecated
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(HorizontalBlock.HORIZONTAL_FACING, rot.rotate(state.get(HorizontalBlock.HORIZONTAL_FACING)));
	}

	@Override
	@Deprecated
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(HorizontalBlock.HORIZONTAL_FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HorizontalBlock.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
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
}
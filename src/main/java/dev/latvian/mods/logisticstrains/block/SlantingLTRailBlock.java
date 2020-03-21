package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

/**
 * @author LatvianModder
 */
public class SlantingLTRailBlock extends LTRailBlockBase
{
	private static final int Y_OFF = 2;
	private static final VoxelShape CENTER = box(5, 5 + Y_OFF, 5);
	private static final VoxelShape N_SHAPE = VoxelShapes.or(CENTER, box(5, Y_OFF, 10), box(5, 10 + Y_OFF, 0), box(5, 8 + Y_OFF, 2), box(5, 2 + Y_OFF, 8));
	private static final VoxelShape S_SHAPE = VoxelShapes.or(CENTER, box(5, Y_OFF, 0), box(5, 10 + Y_OFF, 10), box(5, 2 + Y_OFF, 2), box(5, 8 + Y_OFF, 8));
	private static final VoxelShape W_SHAPE = VoxelShapes.or(CENTER, box(10, Y_OFF, 5), box(0, 10 + Y_OFF, 5), box(2, 8 + Y_OFF, 5), box(8, 2 + Y_OFF, 5));
	private static final VoxelShape E_SHAPE = VoxelShapes.or(CENTER, box(0, Y_OFF, 5), box(10, 10 + Y_OFF, 5), box(2, 2 + Y_OFF, 5), box(8, 8 + Y_OFF, 5));

	private static VoxelShape box(int x, int y, int z)
	{
		return Block.makeCuboidShape(x, y, z, x + 6, y + 6, z + 6);
	}

	public SlantingLTRailBlock(Properties properties)
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
		return super.getStateForPlacement(context).with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
	}
}
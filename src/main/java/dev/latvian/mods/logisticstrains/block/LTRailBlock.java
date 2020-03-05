package dev.latvian.mods.logisticstrains.block;

import dev.latvian.mods.logisticstrains.item.LTItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * @author LatvianModder
 */
public class LTRailBlock extends LTRailBaseBlock
{
	public static final EnumProperty<RailShape> SHAPE = EnumProperty.create("shape", RailShape.class);

	public LTRailBlock(Properties properties)
	{
		super(properties);
		setDefaultState(stateContainer.getBaseState().with(SHAPE, RailShape.Z_AXIS).with(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	@Deprecated
	public BlockState rotate(BlockState state, Rotation rotation)
	{
		return state.with(SHAPE, state.get(SHAPE).rotate(rotation));
	}

	@Override
	@Deprecated
	public BlockState mirror(BlockState state, Mirror mirror)
	{
		return state.with(SHAPE, state.get(SHAPE).mirror(mirror));
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return state.get(SHAPE).voxelShape;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		if (context.getFace().getAxis().isHorizontal() && !(context.getWorld().getBlockState(context.getPos().offset(context.getFace().getOpposite())).getBlock() instanceof LTRailBaseBlock))
		{
			return LTBlocks.VERTICAL_RAIL.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, context.getFace().getOpposite()).with(BlockStateProperties.WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
		}

		BlockState s = getDefaultState().with(BlockStateProperties.WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);

		boolean railN = isRail(context, Direction.NORTH);
		boolean railS = isRail(context, Direction.SOUTH);
		boolean railW = isRail(context, Direction.WEST);
		boolean railE = isRail(context, Direction.EAST);

		if (railN && railS && railW && railE)
		{
			return s.with(SHAPE, RailShape.CROSS);
		}
		else if ((railN || railS) && !railW && !railE)
		{
			return s.with(SHAPE, RailShape.Z_AXIS);
		}
		else if (!railN && !railS && (railW || railE))
		{
			return s.with(SHAPE, RailShape.X_AXIS);
		}

		return s;
	}

	@Override
	@Deprecated
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target)
	{
		if (player.getHeldItem(hand).getItem() == LTItems.REMOTE)
		{
			RailShape shape = state.get(SHAPE);

			if (shape == RailShape.CROSS)
			{
				world.setBlockState(pos, state.with(SHAPE, RailShape.DOUBLE_NE_SW), 3);
			}
			else if (shape == RailShape.DOUBLE_NE_SW)
			{
				world.setBlockState(pos, state.with(SHAPE, RailShape.DOUBLE_NW_SE), 3);
			}
			else if (shape == RailShape.DOUBLE_NW_SE)
			{
				world.setBlockState(pos, state.with(SHAPE, RailShape.CROSS), 3);
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	private boolean isRail(BlockItemUseContext context, Direction dir)
	{
		BlockState state = context.getWorld().getBlockState(context.getPos().offset(dir));
		return state.getBlock() == this;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(SHAPE, BlockStateProperties.WATERLOGGED);
	}
}
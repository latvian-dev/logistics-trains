package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.common.util.Constants;

/**
 * @author LatvianModder
 */
public class LTRailBlock extends HorizontalLTRailBlock
{
	public static final EnumProperty<RailShape> SHAPE = EnumProperty.create("shape", RailShape.class);

	public LTRailBlock()
	{
		setDefaultState(getDefaultState().with(SHAPE, RailShape.Z_AXIS));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(SHAPE, BlockStateProperties.WATERLOGGED);
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
	@Deprecated
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
	{
		if (player.isCrouching())
		{
			worldIn.setBlockState(pos, state.func_235896_a_(SHAPE), Constants.BlockFlags.DEFAULT);
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockState parent = super.getStateForPlacement(context);
		Direction placement = context.getPlacementHorizontalFacing();

		if (placement.getAxis() == Direction.Axis.Z)
		{
			if (context.getPlayer() == null || !context.getPlayer().isCrouching())
			{
				BlockPos wp = context.getPos().offset(Direction.WEST);
				BlockPos ep = context.getPos().offset(Direction.EAST);
				BlockState w = context.getWorld().getBlockState(wp);
				BlockState e = context.getWorld().getBlockState(ep);

				boolean wr = w.getBlock() instanceof RedirectingRail && ((RedirectingRail) w.getBlock()).redirect(w, context.getWorld(), wp, Direction.EAST.getIndex()) != -1;
				boolean er = e.getBlock() instanceof RedirectingRail && ((RedirectingRail) e.getBlock()).redirect(e, context.getWorld(), ep, Direction.WEST.getIndex()) != -1;

				if (wr && !er)
				{
					return parent.with(SHAPE, placement == Direction.NORTH ? RailShape.TURN_NW : RailShape.TURN_SW);
				}
				else if (er && !wr)
				{
					return parent.with(SHAPE, placement == Direction.NORTH ? RailShape.TURN_NE : RailShape.TURN_SE);
				}
			}

			return parent.with(SHAPE, RailShape.Z_AXIS);
		}
		else
		{
			if (context.getPlayer() == null || !context.getPlayer().isCrouching())
			{
				BlockPos np = context.getPos().offset(Direction.NORTH);
				BlockPos sp = context.getPos().offset(Direction.SOUTH);
				BlockState n = context.getWorld().getBlockState(np);
				BlockState s = context.getWorld().getBlockState(sp);

				boolean nr = n.getBlock() instanceof RedirectingRail && ((RedirectingRail) n.getBlock()).redirect(n, context.getWorld(), np, Direction.SOUTH.getIndex()) != -1;
				boolean sr = s.getBlock() instanceof RedirectingRail && ((RedirectingRail) s.getBlock()).redirect(s, context.getWorld(), np, Direction.NORTH.getIndex()) != -1;

				if (nr && !sr)
				{
					return parent.with(SHAPE, placement == Direction.WEST ? RailShape.TURN_NW : RailShape.TURN_NE);
				}
				else if (sr && !nr)
				{
					return parent.with(SHAPE, placement == Direction.WEST ? RailShape.TURN_SW : RailShape.TURN_SE);
				}
			}

			return parent.with(SHAPE, RailShape.X_AXIS);
		}
	}

	@Override
	public int redirect(BlockState state, World world, BlockPos pos, int from)
	{
		return state.get(SHAPE).redirect[from];
	}
}
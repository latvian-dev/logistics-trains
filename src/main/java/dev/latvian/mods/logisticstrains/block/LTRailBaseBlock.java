package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author LatvianModder
 */
public class LTRailBaseBlock extends Block
{
	public LTRailBaseBlock(Properties properties)
	{
		super(properties);
		setDefaultState(stateContainer.getBaseState().with(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	@Deprecated
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		return new ItemStack(LTBlocks.RAIL);
	}

	@Override
	@Deprecated
	public boolean isTransparent(BlockState state)
	{
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	@Deprecated
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader world, BlockPos pos)
	{
		return 1F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos)
	{
		return true;
	}

	@Override
	@Deprecated
	public boolean causesSuffocation(BlockState state, IBlockReader world, BlockPos pos)
	{
		return false;
	}

	@Override
	@Deprecated
	public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos)
	{
		return false;
	}

	@Override
	@Deprecated
	public boolean canEntitySpawn(BlockState state, IBlockReader world, BlockPos pos, EntityType<?> entityType)
	{
		return false;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return getDefaultState().with(BlockStateProperties.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	@Deprecated
	public IFluidState getFluidState(BlockState state)
	{
		return state.get(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	@Deprecated
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos)
	{
		if (state.get(BlockStateProperties.WATERLOGGED))
		{
			world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.updatePostPlacement(state, facing, facingState, world, pos, facingPos);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockStateProperties.WATERLOGGED);
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return VoxelShapes.fullCube();
	}

	@Override
	@Deprecated
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
	{
		return false;
	}

	@Override
	@Deprecated
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		notifyNetwork(state, world, pos);
	}

	@Override
	@Deprecated
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
	{
		super.onReplaced(state, world, pos, newState, isMoving);
		notifyNetwork(state, world, pos);
	}

	private void notifyNetwork(BlockState state, World world, BlockPos pos)
	{
	}
}
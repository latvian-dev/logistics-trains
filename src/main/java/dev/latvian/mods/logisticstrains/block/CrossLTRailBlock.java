package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * @author LatvianModder
 */
public class CrossLTRailBlock extends HorizontalLTRailBlock
{
	private static final VoxelShape SHAPE = VoxelShapes.or(RailShape.xAxisShape(), RailShape.zAxisShape());
	private static final int[] REDIRECT = {-1, -1, 3, 2, 5, 4};

	public CrossLTRailBlock(Properties properties)
	{
		super(properties);
	}

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
}
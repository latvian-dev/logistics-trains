package dev.latvian.mods.logisticstrains.block;

import dev.latvian.mods.logisticstrains.block.entity.StationEntity;
import dev.latvian.mods.logisticstrains.item.LTItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author LatvianModder
 */
public class StationBlock extends LTRailBlockBase
{
	private static final VoxelShape SHAPE = makeCuboidShape(0, 0, 0, 16, 3, 16);

	public StationBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new StationEntity();
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
	{
		if (stack.hasTag())
		{
			TileEntity entity = world.getTileEntity(pos);

			if (entity instanceof StationEntity)
			{
				((StationEntity) entity).stationID = stack.getTag().getUniqueId("StationID");
			}

			stack.setTag(null);
		}
	}

	@Override
	@Deprecated
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target)
	{
		TileEntity entity = world.getTileEntity(pos);

		if (entity instanceof StationEntity && !world.isRemote())
		{
			player.sendMessage(new StringTextComponent("Station " + ((StationEntity) entity).stationID));
		}

		return ActionResultType.SUCCESS;
	}

	@Override
	@Deprecated
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity entity = world.getTileEntity(pos);

			if (entity instanceof StationEntity)
			{
				if (!world.isRemote() && !((StationEntity) entity).stationID.equals(new UUID(0L, 0L)))
				{
					//world.updateComparatorOutputLevel(pos, this);
					double d0 = EntityType.ITEM.getWidth();
					double d1 = 1.0D - d0;
					double d2 = d0 / 2.0D;
					double px = pos.getX() + RANDOM.nextDouble() * d1 + d2;
					double py = pos.getY() + RANDOM.nextDouble() * d1;
					double pz = pos.getZ() + RANDOM.nextDouble() * d1 + d2;

					ItemStack stack = new ItemStack(LTItems.STATION);
					stack.setTag(new CompoundNBT());
					stack.getTag().putUniqueId("StationID", ((StationEntity) entity).stationID);
					ItemEntity itemEntity = new ItemEntity(world, px, py, pz, stack);
					double f = 0.05F;
					itemEntity.setMotion(RANDOM.nextGaussian() * f, RANDOM.nextGaussian() * f + 0.2D, RANDOM.nextGaussian() * f);
					world.addEntity(itemEntity);
				}

				world.removeTileEntity(pos);
			}
		}

		super.onReplaced(state, world, pos, newState, isMoving);
	}
}
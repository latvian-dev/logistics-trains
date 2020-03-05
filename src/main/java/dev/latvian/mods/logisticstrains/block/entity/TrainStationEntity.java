package dev.latvian.mods.logisticstrains.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class TrainStationEntity extends TileEntity
{
	public UUID owner;
	public RailPathNode path;

	public TrainStationEntity()
	{
		super(LTBlockEntities.TRAIN_STATION);
		owner = new UUID(0L, 0L);
		path = new RailPathNode();
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		nbt.putUniqueId("owner", owner);

		if (!path.isEmpty())
		{
			ListNBT list = new ListNBT();

			for (RailPathNode node : path)
			{
				CompoundNBT nbt1 = new CompoundNBT();
				nbt1.putString("direction", node.direction.getName());
				nbt1.putInt("length", node.length);
				list.add(nbt1);
			}

			nbt.put("path", list);
		}

		return super.write(nbt);
	}

	@Override
	public void read(CompoundNBT nbt)
	{
		super.read(nbt);

		owner = nbt.getUniqueId("owner");

		path = new RailPathNode();

		ListNBT list = nbt.getList("path", Constants.NBT.TAG_COMPOUND);

		if (!list.isEmpty())
		{
			CompoundNBT nbt1 = list.getCompound(0);
			path.direction = Direction.byName(nbt1.getString("direction"));
			path.length = nbt1.getInt("length");

			RailPathNode prev = path;

			for (int i = 1; i < list.size(); i++)
			{
				CompoundNBT nbt2 = list.getCompound(i);
				prev.next = new RailPathNode();
				prev.next.direction = Direction.byName(nbt2.getString("direction"));
				prev.next.length = nbt2.getInt("length");
				prev = prev.next;
			}
		}
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		return write(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag)
	{
		read(tag);
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(pos, 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		handleUpdateTag(pkt.getNbtCompound());
	}

	public void redstoneChanged(boolean powered)
	{
		if (!powered)
		{
			if (!path.isEmpty())
			{
				path = new RailPathNode();
				markDirty();
			}
		}
		else
		{
			tracePath();
		}
	}

	public void tracePath()
	{
		if (world != null)
		{
			tracePath(getBlockState(), new LaserPathTracer(world, pos));
		}
	}

	public void tracePath(BlockState state, LaserPathTracer tracer)
	{
		path = new RailPathNode();
		/*
		LaserPathNode prevPath = path;
		path = new LaserPathNode();

		path.direction = state.get(DirectionalBlock.FACING);
		path.trace(tracer, tracer.startPosition);

		if (!prevPath.equals(path))
		{
			markDirty();
			tracer.world.notifyBlockUpdate(tracer.startPosition, state, state, Constants.BlockFlags.DEFAULT_AND_RERENDER);
		}
		*/
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 256D * 256D;
	}
}
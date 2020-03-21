package dev.latvian.mods.logisticstrains.block.entity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class StationEntity extends TileEntity
{
	public UUID stationID;

	public StationEntity()
	{
		super(LTBlockEntities.STATION);
		stationID = new UUID(0L, 0L);
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		nbt.putUniqueId("StationID", stationID);
		return super.write(nbt);
	}

	@Override
	public void read(CompoundNBT nbt)
	{
		super.read(nbt);
		stationID = nbt.getUniqueId("StationID");
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
	}
}
package dev.latvian.mods.logisticstrains.block;

import net.minecraft.util.Direction;

/**
 * @author LatvianModder
 */
public class DirectionPair
{
	public final int directionA1;
	public final int directionA2;
	public final int directionB1;
	public final int directionB2;
	public final int[] directions;

	public DirectionPair(Direction da1, Direction da2)
	{
		directionA1 = da1.getIndex();
		directionA2 = da2.getIndex();
		directionB1 = da1.getOpposite().getIndex();
		directionB2 = da2.getOpposite().getIndex();
		directions = new int[] {directionA1, directionA2, directionB1, directionB2};
	}

	public int redirect(int from)
	{
		if (from == directionA1)
		{
			return directionA2;
		}
		else if (from == directionA2)
		{
			return directionA1;
		}
		else if (from == directionB1)
		{
			return directionB2;
		}
		else if (from == directionB2)
		{
			return directionB1;
		}

		return -1;
	}
}
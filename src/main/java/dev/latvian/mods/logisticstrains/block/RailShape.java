package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

/**
 * @author LatvianModder
 */
public enum RailShape implements IStringSerializable
{
	X_AXIS("x_axis", xAxisShape(), new DirectionPair(Direction.WEST, Direction.EAST)),
	Z_AXIS("z_axis", zAxisShape(), new DirectionPair(Direction.NORTH, Direction.SOUTH)),
	CROSS("cross", VoxelShapes.or(xAxisShape(), zAxisShape()), new DirectionPair(Direction.WEST, Direction.EAST), new DirectionPair(Direction.NORTH, Direction.SOUTH)),
	TURN_NE("turn_ne", turnNEShape(), new DirectionPair(Direction.NORTH, Direction.EAST)),
	TURN_SE("turn_se", turnSEShape(), new DirectionPair(Direction.SOUTH, Direction.EAST)),
	TURN_SW("turn_sw", turnSWShape(), new DirectionPair(Direction.SOUTH, Direction.WEST)),
	TURN_NW("turn_nw", turnNWShape(), new DirectionPair(Direction.NORTH, Direction.WEST)),
	DOUBLE_NE_SW("double_ne_sw", VoxelShapes.or(turnNEShape(), turnSWShape()), new DirectionPair(Direction.NORTH, Direction.EAST), new DirectionPair(Direction.SOUTH, Direction.WEST)),
	DOUBLE_NW_SE("double_nw_se", VoxelShapes.or(turnNWShape(), turnSEShape()), new DirectionPair(Direction.NORTH, Direction.WEST), new DirectionPair(Direction.SOUTH, Direction.EAST)),

	;

	private final String name;
	public VoxelShape voxelShape;
	public final DirectionPair[] pairs;
	public final int[] redirect;

	RailShape(String n, VoxelShape s, DirectionPair... p)
	{
		name = n;
		voxelShape = s;
		pairs = p;
		redirect = new int[6];

		for (int from = 0; from < 6; from++)
		{
			redirect[from] = -1;

			for (DirectionPair pair : pairs)
			{
				redirect[from] = pair.redirect(from);

				if (redirect[from] != -1)
				{
					break;
				}
			}
		}
	}

	@Override
	public String getName()
	{
		return name;
	}

	public RailShape rotate(Rotation rotation)
	{
		return this;
	}

	public RailShape mirror(Mirror mirror)
	{
		return this;
	}

	public static VoxelShape xAxisShape()
	{
		return Block.makeCuboidShape(0, 0, 6, 16, 3, 10);
	}

	public static VoxelShape zAxisShape()
	{
		return Block.makeCuboidShape(6, 0, 0, 10, 3, 16);
	}

	public static VoxelShape turnNEShape()
	{
		return Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
	}

	public static VoxelShape turnSEShape()
	{
		return Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
	}

	public static VoxelShape turnSWShape()
	{
		return Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
	}

	public static VoxelShape turnNWShape()
	{
		return Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
	}
}
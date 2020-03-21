package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LatvianModder
 */
public enum RailShape implements IStringSerializable
{
	X_AXIS("x_axis", "z_axis", xAxisShape(), new DirectionPair(Direction.WEST, Direction.EAST)),
	Z_AXIS("z_axis", "x_axis", zAxisShape(), new DirectionPair(Direction.NORTH, Direction.SOUTH)),
	TURN_NW("turn_nw", "turn_ne", turnNWShape(), new DirectionPair(Direction.NORTH, Direction.WEST)),
	TURN_NE("turn_ne", "turn_se", turnNEShape(), new DirectionPair(Direction.NORTH, Direction.EAST)),
	TURN_SE("turn_se", "turn_sw", turnSEShape(), new DirectionPair(Direction.SOUTH, Direction.EAST)),
	TURN_SW("turn_sw", "turn_nw", turnSWShape(), new DirectionPair(Direction.SOUTH, Direction.WEST)),

	;

	public static final Map<String, RailShape> MAP = new HashMap<>();

	static
	{
		for (RailShape shape : values())
		{
			MAP.put(shape.name, shape);
		}

		for (RailShape shape : MAP.values())
		{
			shape.rotationCW = MAP.get(shape.rotationCW0);
		}
	}

	private final String name;
	private final String rotationCW0;
	public final VoxelShape voxelShape;
	public final DirectionPair directionPair;
	public final int[] redirect;

	public RailShape rotationCW;

	RailShape(String n, String rc, VoxelShape s, DirectionPair p)
	{
		name = n;
		rotationCW0 = rc;
		voxelShape = s;
		directionPair = p;
		redirect = new int[6];

		for (int from = 0; from < 6; from++)
		{
			redirect[from] = directionPair.redirect(from);
		}
	}

	@Override
	public String getName()
	{
		return name;
	}

	public RailShape rotate(Rotation rotation)
	{
		switch (rotation)
		{
			case CLOCKWISE_90:
				return rotationCW;
			case CLOCKWISE_180:
				return rotationCW.rotationCW;
			case COUNTERCLOCKWISE_90:
				return rotationCW.rotationCW.rotationCW;
			default:
				return this;
		}
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

	public static VoxelShape turnNWShape()
	{
		return Block.makeCuboidShape(0, 0, 0, 10, 3, 10);
	}

	public static VoxelShape turnNEShape()
	{
		return Block.makeCuboidShape(6, 0, 0, 16, 3, 10);
	}

	public static VoxelShape turnSWShape()
	{
		return Block.makeCuboidShape(0, 0, 6, 10, 3, 16);
	}

	public static VoxelShape turnSEShape()
	{
		return Block.makeCuboidShape(6, 0, 6, 16, 3, 16);
	}
}
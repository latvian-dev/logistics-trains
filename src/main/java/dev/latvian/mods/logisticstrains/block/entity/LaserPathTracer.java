package dev.latvian.mods.logisticstrains.block.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

/**
 * @author LatvianModder
 */
public class LaserPathTracer
{
	public final World world;
	public final BlockPos startPosition;
	public final HashSet<BlockPos> visitedPositions;

	public LaserPathTracer(World w, BlockPos p)
	{
		world = w;
		startPosition = p;
		visitedPositions = new HashSet<>();
	}
}
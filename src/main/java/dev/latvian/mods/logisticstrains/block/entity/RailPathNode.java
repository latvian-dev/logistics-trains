package dev.latvian.mods.logisticstrains.block.entity;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author LatvianModder
 */
public class RailPathNode implements Iterable<RailPathNode>
{
	private static class PathIterator implements Iterator<RailPathNode>
	{
		private RailPathNode node;

		private PathIterator(RailPathNode n)
		{
			node = n;
		}

		@Override
		public boolean hasNext()
		{
			return node != null;
		}

		@Override
		public RailPathNode next()
		{
			RailPathNode n = node;

			if (n == null)
			{
				throw new NoSuchElementException();
			}

			node = node.next;
			return n;
		}
	}

	public RailPathNode next = null;
	public Direction direction = null;
	public int length = 0;

	@Override
	public String toString()
	{
		if (next == null)
		{
			return direction.getName() + ":" + length;
		}

		return direction.getName() + ":" + length + "+" + next;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(direction, length, next);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		else if (obj instanceof RailPathNode)
		{
			RailPathNode r = (RailPathNode) obj;
			return r.direction == direction && r.length == length && Objects.equals(next, r.next);
		}

		return false;
	}

	@Override
	public Iterator<RailPathNode> iterator()
	{
		return isEmpty() ? Collections.emptyIterator() : new PathIterator(this);
	}

	public boolean isEmpty()
	{
		return length <= 0 && next == null;
	}

	/*
	public BlockPos trace(LaserPathTracer tracer, BlockPos pos)
	{
		int firstNonAirBlock = 0;

		for (length = 1; length < 64; length++)
		{
			BlockPos pos1 = pos.offset(direction, length);
			IChunk chunk = tracer.world.getChunk(pos1.getX() >> 4, pos1.getZ() >> 4, ChunkStatus.FULL, false);

			if (chunk == null)
			{
				return pos1;
			}

			BlockState state = chunk.getBlockState(pos1);

			if (state.getBlock() instanceof LaserDevice)
			{
				if (tracer.visitedPositions.contains(pos1))
				{
					return pos1;
				}

				tracer.visitedPositions.add(pos1);

				Direction r = ((LaserDevice) state.getBlock()).redirectLaser(tracer, state, pos1, direction.getOpposite());

				if (r != null)
				{
					LaserPathNode n = new LaserPathNode();
					n.direction = r;
					n.trace(tracer, pos1);

					if (n.length > 0)
					{
						next = n;
					}
				}
				else if (((LaserDevice) state.getBlock()).laserPassesThrough(tracer, state, pos1))
				{
					LaserPathNode n = new LaserPathNode();
					n.direction = direction;
					n.trace(tracer, pos1);

					if (n.length > 0)
					{
						next = n;
					}
				}

				return pos1;
			}
			else if (firstNonAirBlock == 0 && !state.getBlock().isAir(state, tracer.world, pos1))
			{
				firstNonAirBlock = length;
			}
		}

		if (firstNonAirBlock != 0)
		{
			length = firstNonAirBlock;
		}

		BlockPos pos1 = pos.offset(direction, length);
		tracer.visitedPositions.add(pos1);
		return pos1;
	}
	 */

	public BlockPos last(BlockPos pos)
	{
		pos = pos.offset(direction, length);

		if (next != null)
		{
			return next.last(pos);
		}

		return pos;
	}
}
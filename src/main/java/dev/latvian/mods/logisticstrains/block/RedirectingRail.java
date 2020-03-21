package dev.latvian.mods.logisticstrains.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author LatvianModder
 */
public interface RedirectingRail
{
	int redirect(BlockState state, World world, BlockPos pos, int from);
}
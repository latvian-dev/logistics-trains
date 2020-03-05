package dev.latvian.mods.logisticstrains.block;

/**
 * @author LatvianModder
 */
public enum RailDirection
{
	DOWN("down", 0, 1),
	UP("up", 1, 0),
	NORTH("north", 2, 3),
	SOUTH("south", 3, 2),
	WEST("west", 4, 5),
	EAST("east", 5, 4),
	;

	public final String name;
	public final int index;
	public final int opposite;

	RailDirection(String n, int i, int o)
	{
		name = n;
		index = i;
		opposite = o;
	}
}
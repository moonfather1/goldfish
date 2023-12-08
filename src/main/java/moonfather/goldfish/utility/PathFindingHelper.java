package moonfather.goldfish.utility;

import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;

public class PathFindingHelper
{
	public static boolean IsPartOfASeriousBodyOfWater(World world, BlockPos position)
	{
		return (new PathFindingHelper()).IsPartOfASeriousBodyOfWaterInternal(world, position);
	}
	
	
	
	private final LinkedList<BlockPos> markedToExpand = new LinkedList<BlockPos>();
	private final LinkedList<BlockPos> markedAndDone = new LinkedList<BlockPos>();
	public boolean IsPartOfASeriousBodyOfWaterInternal(World world, BlockPos position)
	{
		//zaboravi//this.initializeValidTargets(12, 10, world, position);    if (this.targets.size() == 0) return false;
		this.markedToExpand.add(position);
		while (true)
		{
			if (this.markedToExpand.isEmpty())
			{
				boolean result = this.markedAndDone.size() > 1500;
				this.markedAndDone.clear();
				this.markedToExpand.clear();
				return result;
			}
			if (this.markedToExpand.size() + this.markedAndDone.size() > 1500)
			{
				this.markedAndDone.clear();
				this.markedToExpand.clear();
				return true;
			}
			BlockPos current = this.markedToExpand.removeLast();
			this.markedAndDone.addFirst(current);
			this.CheckBlock(world, current.getX() - 1, current.getY(), current.getZ());
			this.CheckBlock(world, current.getX(), current.getY() + 1, current.getZ());
			this.CheckBlock(world, current.getX(), current.getY(), current.getZ() - 1);
			this.CheckBlock(world, current.getX() + 1, current.getY(), current.getZ());
			this.CheckBlock(world, current.getX(), current.getY() - 1, current.getZ());
			this.CheckBlock(world, current.getX(), current.getY(), current.getZ() + 1);
		}
	}
	
	
	
	private void CheckBlock(World world, int x, int y, int z)
	{
		if (y < 1 || y >= BuildHeight)
		{
			return;
		}
		if (this.ListsContain(x, y, z))
		{
			return;
		}
		this.temp.set(x, y, z);
		if (world.getFluidState(this.temp).isIn(FluidTags.WATER))
		{
			this.markedToExpand.addFirst(new BlockPos(x, y, z));
		}
	}
	
	
	
	private boolean ListsContain(int x, int y, int z)
	{
		for (BlockPos toCheck : this.markedAndDone)
		{
			if (toCheck.getX() == x && toCheck.getY() == y && toCheck.getZ() == z)
			{
				return true;
			}
		}
		for (BlockPos toCheck : this.markedToExpand)
		{
			if (toCheck.getX() == x && toCheck.getY() == y && toCheck.getZ() == z)
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	/*
	/// segmentCount: broj tacaka na krugu; 12==30deg, distance: horizontalno rastojanje
	
	/// vraca tacke na krugu koje imaju vodenu povrsinu
	private List<BlockPos> targets;
	private void initializeValidTargets(int segmentCount, int distance, World world, BlockPos center)
	{
		MutableBlockPos pos = new MutableBlockPos(), posUp = new MutableBlockPos();
		this.targets = new LinkedList<BlockPos>();
		for (int i = 0; i < segmentCount; i++)
		{
			int x = center.getX() + (int)Math.round((distance + 0.4F) + Math.cos(i/segmentCount * 2 * Math.PI));
			int z = center.getZ() + (int)Math.round((distance + 0.4F) + Math.sin(i/segmentCount * 2 * Math.PI));
			pos.setPos(x,  center.getY(),  z);
			Material material = world.getBlockState(pos).getMaterial();
			while (material != Material.WATER && material != Material.AIR)
			{
				if (pos.getY() >= BuildHeight - 1)
				{
					continue;
				}
				pos.setPos(x,  pos.getY() + 1,  z);
				material = world.getBlockState(pos).getMaterial();
			}
			posUp.setPos(x,  pos.getY() + 1,  z);
			Material materialUp = world.getBlockState(posUp).getMaterial();
			while (material == Material.WATER && materialUp == Material.WATER)
			{
				if (pos.getY() >= BuildHeight - 1)
				{
					continue;
				}
				pos.setPos(x,  pos.getY() + 1,  z);
				material = materialUp;
				posUp.setPos(x,  pos.getY() + 1,  z);
				material = world.getBlockState(posUp).getMaterial();
			}
			if (material == Material.WATER && materialUp == Material.AIR)
			{
				this.targets.add(pos);
			}
		}/// i sad nesto mislim da mi ovo ne treba uopste... odradicu BFS, ali bez ovih ciljeva nego samo gledam da li se napuni dovoljno...
	}

	*/

	private final int BuildHeight = 320;
	private final BlockPos.Mutable temp = new BlockPos.Mutable();
}

package wildCaves.generation.structureGen;

import java.util.Random;

import wildCaves.Utils;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GenerateFloodedCaves 
{
	public static void generate(World world, Random random, int x, int y, int z) 
	{
		int distance = Utils.getNumEmptyBlocks(world, x, y, z);
		if(distance > 3)
		{
			y -= (distance/3)*2;
			
			int i=0, j=0;
			while ((world.isAirBlock(x, y, z) || world.getBlockId(x, y, z) != Block.waterMoving.blockID)&&i<30)
			{
				while((world.isAirBlock(x, y, z) || world.getBlockId(x, y, z) != Block.waterMoving.blockID)&&j<30)
				{
					world.setBlock(x, y, z, Block.waterStill.blockID);
					z--;
					j++;
				}
				i++;
				x--;
			}
		}
	}
}

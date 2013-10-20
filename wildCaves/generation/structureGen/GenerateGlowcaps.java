package wildCaves.generation.structureGen;

import java.util.Random;

import wildCaves.WildCaves;

import net.minecraft.world.World;

public class GenerateGlowcaps 
{
	// Genarates Glowing mushrooms at the specified x,y,z
	public static boolean generate(World world, Random random, int x, int y, int z)
	{
		if(!world.isAirBlock(x, y-1, z))
		{
			int glowcapNum = 0;
			if(world.isAirBlock(x, y+1, z))
				glowcapNum = random.nextInt(5);
			else
				glowcapNum = random.nextInt(4);
			
			world.setBlock(x, y, z, WildCaves.blockFloraID, glowcapNum, 2);
			if(glowcapNum == 4)
			{
				world.setBlock(x, y+1, z, WildCaves.blockFloraID, glowcapNum+1, 2);
			}
			return true;
		}
		else
			return false;
	}
}

package wildCaves.generation.structureGen;

import java.util.Random;

import wildCaves.Utils;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class GenerateVines
{
	// returns wether it managed to place a vine or not
	public static boolean generate(World world, Random random, int x, int y, int z) 
	{
		int distance = Utils.getNumEmptyBlocks(world, x, y, z);
		int aux;
		boolean result = false;
		
		if (distance > 5)
			aux = random.nextInt(distance-5)+5;
		else
			aux = distance;
		
		//the side the vine should be facing
		int side = random.nextInt(4)+2;
		result = true;
		
		// length of the vine
		int i=0;
		while(i<aux && !world.getBlockMaterial(x, y-i, z).isLiquid())
		{
			world.setBlock(x, y - i, z, Block.vine.blockID,	1 << Direction.facingToDirection[side], 0);
			//world.setBlock(x, y - i, z, Block.vine.blockID,	1 << Direction.vineGrowth[Facing.faceToSide[side]], 0);
			i+=1;
		}

		return result;
	}
}
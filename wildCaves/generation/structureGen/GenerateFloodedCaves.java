package wildCaves.generation.structureGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import wildCaves.Utils;

import net.minecraft.world.World;

public class GenerateFloodedCaves 
{
	public static void generate(World world, Random random, int x, int y, int z) 
	{
        int blocks = 0;
        int di = 16* MathHelper.truncateDoubleToInt(x / 16) + random.nextInt(10);
        int dk = 16* MathHelper.truncateDoubleToInt(z / 16) + random.nextInt(10);
		for(int i=di;i<di+7;i++){
            for(int k=dk; k<dk+7;k++){
                blocks+=Utils.getNumEmptyBlocks(world, i, y, k);
                if(blocks > 400)
                    return;
            }
        }
		if(blocks > 150){
			y -= (Utils.getNumEmptyBlocks(world, x, y, z)/3)*2;
            for(int i=di;i<di+7;i++){
                for(int k=dk; k<dk+7;k++){
                    if(world.isAirBlock(i, y, k)){
                        world.setBlock(i, y, k, Blocks.flowing_water, 0, 2);
                    }
                }
			}
		}
	}
}

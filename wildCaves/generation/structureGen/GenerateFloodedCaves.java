package wildCaves.generation.structureGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import wildCaves.Utils;

import net.minecraft.world.World;

public class GenerateFloodedCaves 
{
	public static void generate(World world, Random random, int x, int y, int z) 
	{
        int blocks = 0;
		for(int i=x-3;i<x+4;i++){
            for(int k=z-3; k<z+4;k++){
                blocks+=Utils.getNumEmptyBlocks(world, i, y, k);
                if(blocks > 400)
                    return;
            }
        }
		if(blocks > 150){
			y -= (Utils.getNumEmptyBlocks(world, x, y, z)/3)*2;
            for(int i=x-3;i<x+4;i++){
                for(int k=z-3; k<z+4;k++){
                    if(world.func_147437_c(i, y, k)){
                        world.func_147465_d(x, y, k, Blocks.flowing_water, 0, 2);
                    }
                }
			}
		}
	}
}

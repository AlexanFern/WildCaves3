package wildCaves.generation.biomeGen;

import java.util.Random;

import wildCaves.Utils;
import wildCaves.generation.structureGen.GenerateIceshrooms;
import wildCaves.generation.structureGen.GenerateIcicles;
import wildCaves.generation.structureGen.GenerateSkulls;
import wildCaves.generation.structureGen.GenerateStoneStalactite;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenerationFrozen extends WorldGenerator
{
	private static float probabilityIceshrooms;
	private static float probabilityStalactite;
	private static float probabilitySpiderWeb;
	private static float probabilityIcicle;
	private static float probabilitySkulls;
	public static int maxLength;
	
	public GenerationFrozen(float probabilityStalactite, int maxLength, float probabilityIceshrooms, float probabilitySpiderWeb, float probabilityIcicle, float probabilitySkulls)
	{
		this.probabilityStalactite = probabilityStalactite;
		this.maxLength = maxLength;
		this.probabilityIcicle = probabilityIcicle;
		this.probabilityIceshrooms = probabilityIceshrooms;
		this.probabilitySpiderWeb = probabilitySpiderWeb;
		this.probabilitySkulls = probabilitySkulls;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) 
	{
		boolean success = false;

		switch(Utils.weightedChoise(probabilityIceshrooms, probabilitySpiderWeb, probabilityIcicle, probabilitySkulls, probabilityStalactite, 0))
		{
			case 1:
				GenerateIceshrooms.generate(world, random, x, y-Utils.getNumEmptyBlocks(world, x, y, z)+1, z);
				success = true;
				break;
			case 2:
				world.setBlock(x, y , z, Block.web.blockID);
				success = true;
				break;
			case 3:
				GenerateIcicles.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
				success = true;
				break;
			case 4:
				GenerateSkulls.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
				success = true;
				break;
			default:
				GenerateStoneStalactite.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), maxLength);
				success = true;
		}
		return success;
	}
}

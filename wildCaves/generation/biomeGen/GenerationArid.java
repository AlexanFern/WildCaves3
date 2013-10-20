package wildCaves.generation.biomeGen;

import java.util.Random;

import wildCaves.Utils;
import wildCaves.generation.structureGen.GenerateSandstoneStalactites;
import wildCaves.generation.structureGen.GenerateSkulls;
import wildCaves.generation.structureGen.GenerateStoneStalactite;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenerationArid extends WorldGenerator
{
	private static float probabilityStalactite;
	private static float probabilitySandStalactites;
	private static float probabilitySpiderWeb;
	private static float probabilityDry;
	private static float probabilitySkulls;
	private static int maxLength;

	public GenerationArid(float probabilityStalactite, int maxLength, float probabilitySandStalactites, float probabilitySpiderWeb, float probabilityDry, float probabilitySkulls)
	{
		this.probabilityStalactite = probabilityStalactite;
		this.maxLength = maxLength;
		this.probabilitySandStalactites = probabilitySandStalactites;
		this.probabilitySpiderWeb = probabilitySpiderWeb;
		this.probabilityDry = probabilityDry;
		this.probabilitySkulls = probabilitySkulls;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) 
	{
		boolean success = false;
			
		switch(Utils.weightedChoise(probabilitySandStalactites, probabilitySpiderWeb, probabilityDry, probabilitySkulls, probabilityStalactite, 0))
		{
			case 1:
				GenerateSandstoneStalactites.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), maxLength);
				success = true;
				break;
			case 2:
				world.setBlock(x, y , z, Block.web.blockID);
				success = true;
				break;
			case 3:
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

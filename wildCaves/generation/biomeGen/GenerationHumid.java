package wildCaves.generation.biomeGen;

import java.util.Random;

import wildCaves.Utils;
import wildCaves.generation.structureGen.GenerateGlowcaps;
import wildCaves.generation.structureGen.GenerateSkulls;
import wildCaves.generation.structureGen.GenerateStoneStalactite;
import wildCaves.generation.structureGen.GenerateVines;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenerationHumid extends WorldGenerator
{
	private static float probabilityWet;
	private static float probabilityGlowcapsHumid;
	private static float probabilityStalactite;
	private static float probabilityVines;
	private static float probabilitySpiderWeb;
	private static float probabilitySkulls;
	public static int maxLength;
	
	public GenerationHumid(float probabilityStalactite, int maxLength, float probabilityWet, float probabilityGlowcapsHumid, float probabilityVines, float probabilitySpiderWeb, float probabilitySkulls)
	{
		this.probabilityStalactite = probabilityStalactite;
		this.maxLength = maxLength;
		this.probabilityWet = probabilityWet;
		this.probabilityGlowcapsHumid = probabilityGlowcapsHumid;
		this.probabilityVines = probabilityVines;
		this.probabilitySpiderWeb = probabilitySpiderWeb;
		this.probabilitySkulls = probabilitySkulls;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) 
	{
		boolean success = false;

		switch(Utils.weightedChoise(probabilityGlowcapsHumid, probabilityWet, probabilityVines, probabilitySpiderWeb, probabilitySkulls, probabilityStalactite))
		{
			case 1:
				GenerateGlowcaps.generate(world, random, x, y-Utils.getNumEmptyBlocks(world, x, y, z)+1, z);
				success = true;
				break;
			case 2:
				world.setBlock(x, y + 1, z, Block.waterMoving.blockID);
				success = true;
				break;
			case 3:
				GenerateVines.generate(world, random, x, y, z);
				success = true;
				break;
			case 4:
				world.setBlock(x, y-Utils.getNumEmptyBlocks(world, x, y, z)+1, z, Block.web.blockID);
				break;
			case 5:
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

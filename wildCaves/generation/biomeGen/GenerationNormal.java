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

public class GenerationNormal extends WorldGenerator
{
	private static float probabilityGlowcaps;
	private static float probabilityStalactite;
	private static float probabilityVines;
	private static float probabilitySpiderWeb;
	private static float probabilitySkulls;
	private static int maxLength;
	private static int maxGenHeightGlowcapNormal;
	
	public GenerationNormal(float probabilityStalactite, int maxLength, float probabilityGlowcaps, float probabilityVines, float probabilitySpiderWeb, float probabilitySkulls, int maxGenHeightGlowcapNormal)
	{
		this.probabilityStalactite = probabilityStalactite;
		this.maxLength = maxLength;
		this.probabilityVines = probabilityVines;
		this.probabilitySpiderWeb = probabilitySpiderWeb;
		this.probabilitySkulls = probabilitySkulls;
		this.maxGenHeightGlowcapNormal = maxGenHeightGlowcapNormal;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) 
	{
		boolean success = false;
		float glowcapsAux = 0;
		if(y<maxGenHeightGlowcapNormal)
			glowcapsAux = probabilityGlowcaps;

		switch(Utils.weightedChoise(probabilityVines, probabilitySpiderWeb, probabilityStalactite, probabilityGlowcaps, probabilitySkulls, 0))
		{
			case 1:
				GenerateVines.generate(world, random, x, y, z);
				success = true;
				break;
			case 2:
				world.setBlock(x, y , z, Block.web.blockID);
				break;
			case 3:
				GenerateStoneStalactite.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), maxLength);
				success = true;
				break;
			case 4:
				GenerateGlowcaps.generate(world, random, x, y-Utils.getNumEmptyBlocks(world, x, y, z)+1, z);
				success = true;
				break;
			case 5:
				GenerateSkulls.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
				success = true;
				break;			
		}
		return success;
	}
}

package wildCaves.generation.biomeGen;

import java.util.Random;

import wildCaves.Utils;
import wildCaves.generation.structureGen.GenerateGlowcaps;
import wildCaves.generation.structureGen.GenerateSkulls;
import wildCaves.generation.structureGen.GenerateStoneStalactite;
import wildCaves.generation.structureGen.GenerateVines;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenerationJungle extends WorldGenerator {
	private static float probabilityVinesJungle;
	private static float probabilityGlowcapsHumid;
	private static float probabilityStalactite;
	private static float probabilitySpiderWeb;
	private static float probabilitySkulls;
	private static int maxLength;

	public GenerationJungle(float probabilityStalactite, int maxLength, float probabilityVinesJungle, float probabilityGlowcapsHumid, float probabilitySpiderWeb, float probabilitySkulls) {
		this.probabilityStalactite = probabilityStalactite;
		this.maxLength = maxLength;
		this.probabilityVinesJungle = probabilityVinesJungle;
		this.probabilityGlowcapsHumid = probabilityVinesJungle;
		this.probabilitySpiderWeb = probabilitySpiderWeb;
		this.probabilitySkulls = probabilitySkulls;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		boolean success = false;
		switch (Utils.weightedChoise(probabilityGlowcapsHumid, probabilityVinesJungle, probabilitySpiderWeb, probabilitySkulls, probabilityStalactite, 0)) {
		case 1:
			GenerateGlowcaps.generate(world, random, x, y - Utils.getNumEmptyBlocks(world, x, y, z) + 1, z);
			success = true;
			break;
		case 2:
			GenerateVines.generate(world, random, x, y, z);
			success = true;
			break;
		case 3:
			world.setBlock(x, y, z, Block.web.blockID);
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

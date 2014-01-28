package wildCaves.generation.biomeGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.GenerateGlowcaps;
import wildCaves.generation.structureGen.GenerateSkulls;
import wildCaves.generation.structureGen.GenerateStoneStalactite;
import wildCaves.generation.structureGen.GenerateVines;

public class GenerationNormal extends WorldGenerator {
	public GenerationNormal() {
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		boolean success = false;
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityVines, WorldGenWildCaves.probabilitySpiderWeb, WorldGenWildCaves.probabilityStalactite, WorldGenWildCaves.probabilityGlowcaps,
				WorldGenWildCaves.probabilitySkulls, 0)) {
		case 1:
			GenerateVines.generate(world, random, x, y, z);
			success = true;
			break;
		case 2:
			world.func_147465_d(x, y, z, Blocks.web, 0, 2);
			break;
		case 3:
			GenerateStoneStalactite.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), WorldGenWildCaves.maxLength);
			success = true;
			break;
		case 4:
			GenerateGlowcaps.generate(world, random, x, y - Utils.getNumEmptyBlocks(world, x, y, z) + 1, z);
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

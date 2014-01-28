package wildCaves.generation.biomeGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.*;

public class GenerationHumid extends WorldGenerator {
	public GenerationHumid() {
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		boolean success = false;
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityGlowcapsHumid, WorldGenWildCaves.probabilityWet, WorldGenWildCaves.probabilityVines, WorldGenWildCaves.probabilitySpiderWeb,
				WorldGenWildCaves.probabilitySkulls, WorldGenWildCaves.probabilityStalactite)) {
		case 1:
			GenerateGlowcaps.generate(world, random, x, y - Utils.getNumEmptyBlocks(world, x, y, z) + 1, z);
			success = true;
			break;
		case 2:
			GenerateFloodedCaves.generate(world, random, x, y, z);
			success = true;
			break;
		case 3:
			GenerateVines.generate(world, random, x, y, z);
			success = true;
			break;
		case 4:
			world.func_147465_d(x, y - Utils.getNumEmptyBlocks(world, x, y, z) + 1, z, Blocks.web, 0, 2);
			break;
		case 5:
			GenerateSkulls.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
			success = true;
			break;
		default:
			GenerateStoneStalactite.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), WorldGenWildCaves.maxLength);
			success = true;
		}
		return success;
	}
}

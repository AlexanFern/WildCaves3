package wildCaves.generation.biomeGen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.GenerateGlowcaps;
import wildCaves.generation.structureGen.GenerateSkulls;
import wildCaves.generation.structureGen.GenerateStoneStalactite;
import wildCaves.generation.structureGen.GenerateVines;

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
			world.setBlock(x, y + 1, z, Block.waterMoving.blockID);
			success = true;
			break;
		case 3:
			GenerateVines.generate(world, random, x, y, z);
			success = true;
			break;
		case 4:
			world.setBlock(x, y - Utils.getNumEmptyBlocks(world, x, y, z) + 1, z, Block.web.blockID);
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

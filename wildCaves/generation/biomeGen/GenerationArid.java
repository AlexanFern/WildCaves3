package wildCaves.generation.biomeGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.DecorationHelper;
import wildCaves.generation.structureGen.GenerateSandstoneStalactites;
import wildCaves.generation.structureGen.GenerateStoneStalactite;

public class GenerationArid extends WorldGenerator {
	public GenerationArid() {
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilitySandStalactites, WorldGenWildCaves.probabilitySpiderWeb, WorldGenWildCaves.probabilityDry, WorldGenWildCaves.probabilitySkulls,
				WorldGenWildCaves.probabilityStalactite, 0)) {
		case 1:
			GenerateSandstoneStalactites.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), WorldGenWildCaves.maxLength);
            return true;
		case 2:
			world.setBlock(x, y, z, Blocks.web, 0, 2);
            return true;
		case 3:
            return false;
		case 4:
			DecorationHelper.generateSkulls(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
            return true;
		default:
			GenerateStoneStalactite.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), WorldGenWildCaves.maxLength);
            return true;
		}
	}
}

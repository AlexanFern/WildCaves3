package wildCaves.generation.biomeGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.*;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenerationJungle extends WorldGenerator {
	public GenerationJungle() {
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityGlowcapsHumid, WorldGenWildCaves.probabilityVinesJungle, WorldGenWildCaves.probabilitySpiderWeb, WorldGenWildCaves.probabilitySkulls,
				WorldGenWildCaves.probabilityStalactite, 0)) {
		case 1:
			DecorationHelper.generateGlowcaps(world, random, x, y, z);
            return true;
		case 2:
			DecorationHelper.generateVines(world, random, x, y, z);
            return true;
		case 3:
			world.setBlock(x, y, z, Blocks.web, 0, 2);
            return true;
		case 4:
			DecorationHelper.generateSkulls(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
            return true;
		default:
			GenerateStoneStalactite.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), WorldGenWildCaves.maxLength);
            return true;
		}
	}
}

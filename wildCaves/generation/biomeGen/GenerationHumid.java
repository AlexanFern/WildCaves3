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
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityGlowcapsHumid, WorldGenWildCaves.probabilityWet, WorldGenWildCaves.probabilityVines, WorldGenWildCaves.probabilitySpiderWeb,
				WorldGenWildCaves.probabilitySkulls, WorldGenWildCaves.probabilityStalactite)) {
		case 1:
			DecorationHelper.generateGlowcaps(world, random, x, y, z);
			return true;
		case 2:
			DecorationHelper.generateFloodedCaves(world, random, x, y, z);
			return true;
		case 3:
			DecorationHelper.generateVines(world, random, x, y, z);
            return true;
		case 4:
			world.setBlock(x, y - Utils.getNumEmptyBlocks(world, x, y, z) + 1, z, Blocks.web, 0, 2);
            return true;
		case 5:
			DecorationHelper.generateSkulls(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
            return true;
		default:
			GenerateStoneStalactite.generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), WorldGenWildCaves.maxLength);
            return true;
		}
	}
}

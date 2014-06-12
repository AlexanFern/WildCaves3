package wildCaves.generation.biomeGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.*;

public class GenerationFrozen extends WorldGenerator {
	public GenerationFrozen() {
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityIceshrooms, WorldGenWildCaves.probabilitySpiderWeb, WorldGenWildCaves.probabilityIcicle, WorldGenWildCaves.probabilitySkulls,
				WorldGenWildCaves.probabilityStalactite, 0)) {
		case 1:
			DecorationHelper.generateIceshrooms(world, random, x, y, z);
            return true;
		case 2:
			world.setBlock(x, y, z, Blocks.web, 0, 2);
            return true;
		case 3:
			DecorationHelper.generateIcicles(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
            return true;
		case 4:
			DecorationHelper.generateSkulls(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z));
            return true;
		default:
			new GenerateStoneStalactite().generate(world, random, x, y, z, Utils.getNumEmptyBlocks(world, x, y, z), WorldGenWildCaves.maxLength);
            return true;
		}
	}
}

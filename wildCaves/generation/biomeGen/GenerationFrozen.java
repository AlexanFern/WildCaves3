package wildCaves.generation.biomeGen;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.DecorationHelper;
import wildCaves.generation.structureGen.GenerateStoneStalactite;

import java.util.Random;

public final class GenerationFrozen extends WorldGenerator {
	public GenerationFrozen() {
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityIceshrooms, WorldGenWildCaves.probabilitySpiderWeb, WorldGenWildCaves.probabilityIcicle, WorldGenWildCaves.probabilitySkulls,
				WorldGenWildCaves.probabilityStalactite, 0)) {
		case 1:
			DecorationHelper.generateIceshrooms(world, random, pos);
            return true;
		case 2:
			world.setBlockState(pos, Blocks.web.getDefaultState(), 2);
            return true;
		case 3:
			DecorationHelper.generateIcicles(world, random, pos, Utils.getNumEmptyBlocks(world, pos));
            return true;
		case 4:
			DecorationHelper.generateSkulls(world, random, pos, Utils.getNumEmptyBlocks(world, pos));
            return true;
		default:
			new GenerateStoneStalactite().generate(world, random, pos, Utils.getNumEmptyBlocks(world, pos), WorldGenWildCaves.maxLength);
            return true;
		}
	}
}

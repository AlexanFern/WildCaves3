package wildCaves.generation.biomeGen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import wildCaves.Utils;
import wildCaves.WorldGenWildCaves;
import wildCaves.generation.structureGen.DecorationHelper;
import wildCaves.generation.structureGen.GenerateStoneStalactite;

import java.util.Random;

public final class GenerationNormal extends WorldGenerator {
	public GenerationNormal() {
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityVines, WorldGenWildCaves.probabilitySpiderWeb, WorldGenWildCaves.probabilityStalactite, WorldGenWildCaves.probabilityGlowcaps,
				WorldGenWildCaves.probabilitySkulls, 0)) {
		case 1:
			DecorationHelper.generateVines(world, random, pos);
            return true;
		case 2:
			world.setBlockState(pos, Blocks.web.getDefaultState(), 2);
            return true;
		case 3:
            new GenerateStoneStalactite().generate(world, random, pos, Utils.getNumEmptyBlocks(world, pos), WorldGenWildCaves.maxLength);
            return true;
		case 4:
			DecorationHelper.generateGlowcaps(world, random, pos);
            return true;
		case 5:
			DecorationHelper.generateSkulls(world, random, pos, Utils.getNumEmptyBlocks(world, pos));
            return true;
		}
        return false;
	}
}

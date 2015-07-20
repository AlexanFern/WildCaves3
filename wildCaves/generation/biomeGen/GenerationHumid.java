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

public final class GenerationHumid extends WorldGenerator {
	public GenerationHumid() {
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		switch (Utils.weightedChoise(WorldGenWildCaves.probabilityGlowcapsHumid, WorldGenWildCaves.probabilityWet, WorldGenWildCaves.probabilityVines, WorldGenWildCaves.probabilitySpiderWeb,
				WorldGenWildCaves.probabilitySkulls, WorldGenWildCaves.probabilityStalactite)) {
		case 1:
			DecorationHelper.generateGlowcaps(world, random, pos);
			return true;
		case 2:
			DecorationHelper.generateFloodedCaves(world, random, pos);
			return true;
		case 3:
			DecorationHelper.generateVines(world, random, pos);
            return true;
		case 4:
			world.setBlockState(pos.down(Utils.getNumEmptyBlocks(world, pos) - 1), Blocks.web.getDefaultState(), 2);
            return true;
		case 5:
			DecorationHelper.generateSkulls(world, random, pos, Utils.getNumEmptyBlocks(world, pos));
            return true;
		default:
            new GenerateStoneStalactite().generate(world, random, pos, Utils.getNumEmptyBlocks(world, pos), WorldGenWildCaves.maxLength);
            return true;
		}
	}
}

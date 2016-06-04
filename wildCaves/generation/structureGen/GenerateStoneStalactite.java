package wildCaves.generation.structureGen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wildCaves.Utils;
import wildCaves.WildCaves;
import wildCaves.WorldGenWildCaves;

import java.util.Random;

public class GenerateStoneStalactite {
    public final Block blockId;
    public GenerateStoneStalactite(){this(WildCaves.blockStoneStalactite);}
    public GenerateStoneStalactite(Block toGen){
        blockId = toGen;
    }

	public void generate(World world, Random random, BlockPos pos, int distance, int maxLength) {
		boolean stalagmiteGenerated = false;
		if (distance <= 1) {
			//x,y,z,blockID, metadata, no update
            if (!world.isAirBlock(pos.up())) {
			    world.setBlockState(pos, blockId.getDefaultState(), 2);
            }
		} else {
			int j = 0; // blocks placed
			BlockPos topY = new BlockPos(pos);
			BlockPos botY = pos.down(distance - 1);
			int aux;
			//stalactite base
			if (!world.isAirBlock(topY.up())) {
                generateStalactiteBase(world, random, topY);
				j++;
			}
			// stalagmite base
			if (!world.getBlockState(botY).getMaterial().isLiquid() && WorldGenWildCaves.isWhiteListed(world.getBlockState(botY.down()).getBlock())) {
				aux = Utils.randomChoise(-1, 8, 9, 10);
				if (aux != -1) {
                    generateStalagmiteBase(world, random, botY, aux);
					j++;
					stalagmiteGenerated = true;
				}
			}
			if (j==2) {
                int k = 0; // counter
                int topMetadata, bottomMetadata;
				while (k < maxLength && topY.getY() >= botY.getY() && j < distance && !world.getBlockState(topY.down()).getMaterial().isLiquid()) {
					k++;
					IBlockState state = world.getBlockState(topY);
					topMetadata = state.getBlock().getMetaFromState(state);
					state = world.getBlockState(botY);
					bottomMetadata = state.getBlock().getMetaFromState(state);
					topY = topY.down();
					botY = botY.up();
					// Expand downwards
					if (world.isAirBlock(topY) && topMetadata > 2 && topMetadata < 6) {
						aux = random.nextInt(5);
						if (aux != 4)
							world.setBlockState(topY, blockId.getStateFromMeta(Utils.randomChoise(4, 5, 7, 11)), 2);
						else
							world.setBlockState(topY, blockId.getStateFromMeta(Utils.randomChoise(7, 11)), 2);
						j++;
					}
					// Expand upwards
					if (world.isAirBlock(botY) && (bottomMetadata > 3 && bottomMetadata < 5 || bottomMetadata == 8) && j < distance && stalagmiteGenerated) {
						aux = random.nextInt(5);
						if (aux != 4)
							world.setBlockState(botY, blockId.getStateFromMeta(Utils.randomChoise(4, 5, 6, 12)), 2);
						else
							world.setBlockState(botY, blockId.getStateFromMeta(Utils.randomChoise(12, 6)), 2);
						j++;
					}
				}
			}
		}
	}

    protected void generateStalagmiteBase(World world, Random random, BlockPos botY, int aux) {
        world.setBlockState(botY, blockId.getStateFromMeta(aux), 2);
    }

    protected void generateStalactiteBase(World world, Random random, BlockPos topY) {
        world.setBlockState(topY, blockId.getStateFromMeta(Utils.randomChoise(1, 2, 3, 3)), 2);
    }
}

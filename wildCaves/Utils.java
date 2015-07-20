package wildCaves;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class Utils {
    public static Block frozen = Blocks.ice;
    public static List<Block> freezable = Arrays.asList(Blocks.stone, Blocks.dirt, Blocks.gravel, Blocks.grass);
	// transforms an area into snow and ice
	public static void convertToFrozenType(World world, Random random, BlockPos pos) {
		int height = random.nextInt(5) + 3;
		int length = random.nextInt(5) + 3;
		int width = random.nextInt(5) + 3;
		int newX = pos.getX() - length / 2;
		int newY = pos.getY() + height / 2;
		int newZ = pos.getZ() - width / 2;
		Block aux;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				for (int k = 0; k < width; k++) {
					// basically transform or not
					if (weightedChoise(0.8f, 0.2f, 0, 0, 0, 0) == 1) {
						BlockPos newPos = new BlockPos(newX + j, newY - i, newZ + k);
						aux = world.getBlockState(newPos).getBlock();
						if (freezable.contains(aux))// stone -> Ice
							world.setBlockState(newPos, frozen.getDefaultState(), 2);
					}
				}
			}
		}
	}
    private static IdentityHashMap<Block, Block> sandEquivalent = new IdentityHashMap<Block, Block>(8);
    static{
        sandEquivalent.put(Blocks.stone, Blocks.sandstone);
        sandEquivalent.put(Blocks.dirt, Blocks.sand);
        sandEquivalent.put(Blocks.gravel, Blocks.sand);
    }
	//transform an area in to sand and sandstone
	public static void convertToSandType(World world, Random random, BlockPos pos) {
		int height = random.nextInt(5) + 3;
		int length = random.nextInt(5) + 3;
		int width = random.nextInt(5) + 3;
		int newX = pos.getX() - length / 2;
		int newY = pos.getY() + height / 2;
		int newZ = pos.getZ() - width / 2;
		Block aux;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				for (int k = 0; k < width; k++) {
					// basically transform or not
					if (weightedChoise(0.7f, 0.3f, 0, 0, 0, 0) == 1) {
						BlockPos newPos = new BlockPos(newX + j, newY - i, newZ + k);
						aux = sandEquivalent.get(world.getBlockState(newPos).getBlock());
						if (aux != null)// stone -> sandstone // dirt/gravel -> sand
							world.setBlockState(newPos, aux.getDefaultState(), 2);
					}
				}
			}
		}
	}

	// gets the number of empty blocks between the current one and the closest one bellow
	public static int getNumEmptyBlocks(World world, BlockPos pos) {
		int dist = 0;
		while (pos.getY() > 5 && !world.isBlockNormalCube(pos, true) && world.isAirBlock(pos)) {
			pos = pos.down();
			dist++;
		}
		return dist;
	}

	// chooses one of the given ints at random
	public static int randomChoise(int... val) {
		Random random = new Random();
		return val[random.nextInt(val.length)];
	}

	// returns the order number of the probability that was chosen (1-6)
	// all parameters are probabilities
	// probabilities can be 0
	public static int weightedChoise(float par1, float par2, float par3, float par4, float par5, float par6) {
		float total = par1 + par2 + par3 + par4 + par5 + par6;
		float val = new Random().nextFloat();
		float previous;
		par1 = par1 / total;
		par2 = par2 / total;
		par3 = par3 / total;
		par4 = par4 / total;
		par5 = par5 / total;
		//par6 is the remaining probability
		if (val < par1)
			return 1;
		else
			previous = par1;
		if (val < par2 + previous)
			return 2;
		else
			previous += par2;
		if (val < par3 + previous)
			return 3;
		else
			previous += par3;
		if (val < par4 + previous)
			return 4;
		else
			previous += par4;
		if (val < par5 + previous)
			return 5;
		else
			return 6;
	}
}

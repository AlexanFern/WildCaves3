package wildCaves;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Utils {
    public static Block frozen = Blocks.ice;
	// transforms an area into snow and ice
	public static void convertToFrozenType(World world, Random random, int x, int y, int z) {
		int height = random.nextInt(5) + 3;
		int length = random.nextInt(5) + 3;
		int width = random.nextInt(5) + 3;
		int newX = x - length / 2;
		int newY = y + height / 2;
		int newZ = z - width / 2;
		Block aux;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				for (int k = 0; k < width; k++) {
					// basically transform or not
					if (weightedChoise(0.8f, 0.2f, 0, 0, 0, 0) == 1) {
						aux = world.getBlock(newX + j, newY - i, newZ + k);
						if (aux == Blocks.stone || aux == Blocks.dirt || aux == Blocks.gravel || aux == Blocks.grass)// stone -> Ice
							world.setBlock(newX + j, newY - i, newZ + k, frozen);
					}
				}
			}
		}
	}

	//transform an area in to sand and sandstone
	public static void convertToSandType(World world, Random random, int x, int y, int z) {
		int height = random.nextInt(5) + 3;
		int length = random.nextInt(5) + 3;
		int width = random.nextInt(5) + 3;
		int newX = x - length / 2;
		int newY = y + height / 2;
		int newZ = z - width / 2;
		Block aux;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				for (int k = 0; k < width; k++) {
					// basically transform or not
					if (weightedChoise(0.7f, 0.3f, 0, 0, 0, 0) == 1) {
						aux = world.getBlock(newX + j, newY - i, newZ + k);
						if (aux == Blocks.stone)// stone -> sandstone
							world.setBlock(newX + j, newY - i, newZ + k, Blocks.sandstone, 0, 2);
						else if (aux == Blocks.dirt || aux == Blocks.gravel) // dirt/gravel -> sand
							world.setBlock(newX + j, newY - i, newZ + k, Blocks.sand, 0, 2);
					}
				}
			}
		}
	}

	// gets the number of empty blocks between the current one and the closest one bellow
	public static int getNumEmptyBlocks(World world, int x, int y, int z) {
		int dist = 0;
		while (y > 5 && !world.isBlockNormalCubeDefault(x, y, z, true) && world.isAirBlock(x, y, z)) {
			y--;
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
		float previous = 0.0f;
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

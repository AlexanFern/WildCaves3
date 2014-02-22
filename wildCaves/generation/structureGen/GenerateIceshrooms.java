package wildCaves.generation.structureGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import wildCaves.Utils;
import wildCaves.WildCaves;

import net.minecraft.world.World;

public class GenerateIceshrooms {
	public static void generate(World world, Random random, int x, int y, int z) {
		if (!world.isAirBlock(x, y - 1, z)) {
			if (!world.getBlock(x, y, z).getMaterial().isLiquid()) {
				world.setBlock(x, y - 1, z, Blocks.ice, 0, 2);
				world.setBlock(x, y, z, WildCaves.blockFlora, Utils.randomChoise(6, 7, 8, 9), 2);
			}
			Utils.convertToFrozenType(world, random, x, y, z);
		}
	}
}

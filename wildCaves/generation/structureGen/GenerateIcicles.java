package wildCaves.generation.structureGen;

import java.util.Random;

import wildCaves.Utils;
import wildCaves.WildCaves;

import net.minecraft.world.World;

public class GenerateIcicles {
	public static void generate(World world, Random random, int x, int y, int z, int distance) {
		int botY = y - distance + 1;
		world.setBlock(x, y + 1, z, Utils.frozen, 0, 2);
		world.setBlock(x, y, z, WildCaves.blockDecorations, Utils.randomChoise(0, 1, 2), 2);
		Utils.convertToFrozenType(world, random, x, y, z);
		if (!world.getBlock(x, botY, z).getMaterial().isLiquid()) {
			Utils.convertToFrozenType(world, random, x, botY, z);
		}
	}
}

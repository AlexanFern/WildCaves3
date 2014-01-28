package wildCaves.generation.structureGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;

public class GenerateSkulls {
	public static void generate(World world, Random random, int x, int y, int z, int numEmptyBlocks) {
		if (numEmptyBlocks > 0) {
			int auxY = y - numEmptyBlocks + 1;
			if (auxY > 0) {
				world.func_147465_d(x, auxY, z, Blocks.skull, 1, 2);
				TileEntity skullTE = world.func_147438_o(x, auxY, z);
				if (skullTE instanceof TileEntitySkull) {
					((TileEntitySkull) skullTE).func_145903_a(random.nextInt(360));//set rotation
				}
			}
		}
	}
}

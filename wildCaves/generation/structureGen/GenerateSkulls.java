package wildCaves.generation.structureGen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;

public class GenerateSkulls {
	public static void generate(World world, Random random, int x, int y, int z, int numEmptyBlocks) {
		if (numEmptyBlocks > 0) {
			int auxY = y - numEmptyBlocks + 1;
			if (auxY > 0) {
				world.setBlock(x, auxY, z, Block.skull.blockID, 1, 2);
				TileEntity skullTE = world.getBlockTileEntity(x, auxY, z);
				if (skullTE instanceof TileEntitySkull) {
					((TileEntitySkull) skullTE).setSkullRotation(random.nextInt(360));
				}
			}
		}
	}
}

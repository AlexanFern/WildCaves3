package wildCaves;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockStoneStalactite extends BlockStalactite {
	public BlockStoneStalactite(int id) {
		super(id, ItemStoneStalactite.stalacs.length);
		setUnlocalizedName("stoneStalactiteBlock");
		setTextureName(":stoneStructure");
	}

	@Override
	public int idDropped(int metadata, Random random, int par3) {
		return Block.cobblestone.blockID;
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3) - 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		int l;
		if (world.getBlockMetadata(x, y, z) < 4) {
			boolean isWatered = world.getBlockMaterial(x, y + 2, z).isLiquid();
			int h = y;
			while (world.getBlockId(x, h, z) == this.blockID) {
				if (random.nextInt(5 + (isWatered ? 0 : 10)) == 0) {
					double d0 = x + random.nextFloat();
					double d2 = z + random.nextFloat();
					double d1 = h + 0.05D + (d0 - x) * (d2 - z);
					world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
				h--;
			}
		}
	}
}

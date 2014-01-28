package wildCaves;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockStoneStalactite extends BlockStalactite {
	public BlockStoneStalactite() {
		super(ItemStoneStalactite.stalacs.length);
        func_149663_c("stoneStalactiteBlock");
        func_149658_d(":stoneStructure");
	}

	@Override
	public Item func_149650_a(int metadata, Random random, int par3) {
		return Item.func_150898_a(Blocks.cobblestone);
	}

	@Override
	public int func_149745_a(Random rand) {
		return rand.nextInt(3) - 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149734_b(World world, int x, int y, int z, Random random) {
		int l;
		if (world.getBlockMetadata(x, y, z) < 4) {
			boolean isWatered = world.func_147439_a(x, y + 2, z).func_149688_o().isLiquid();
			int h = y;
			while (world.func_147439_a(x, h, z) == this) {
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

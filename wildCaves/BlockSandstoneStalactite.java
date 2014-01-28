package wildCaves;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class BlockSandstoneStalactite extends BlockStalactite {
	public BlockSandstoneStalactite() {
		super(ItemSandstoneStalactite.sandStalacs.length);
        func_149663_c("sandstoneStalactiteBlock");
        func_149658_d(":sandstoneStructure");
	}

	@Override
	public Item func_149650_a(int par1, Random par2Random, int par3) {
		return Item.func_150898_a(Blocks.sandstone);
	}

	@Override
	public int func_149745_a(Random rand) {
		return rand.nextInt(3) - 1;
	}
}

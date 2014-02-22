package wildCaves;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class BlockSandstoneStalactite extends BlockStalactite {
	public BlockSandstoneStalactite() {
		super(ItemSandstoneStalactite.sandStalacs.length);
        setBlockName("sandstoneStalactiteBlock");
        setBlockTextureName(":sandstoneStructure");
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Item.getItemFromBlock(Blocks.sandstone);
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3) - 1;
	}
}

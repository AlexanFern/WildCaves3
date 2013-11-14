package wildCaves;

import java.util.Random;

import net.minecraft.block.Block;

public class BlockSandstoneStalactite extends BlockStalactite {
	public BlockSandstoneStalactite(int id) {
		super(id, ItemSandstoneStalactite.sandStalacs.length);
		setUnlocalizedName("sandstoneStalactiteBlock");
		setTextureName(":sandstoneStructure");
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.sandStone.blockID;
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3) - 1;
	}
}

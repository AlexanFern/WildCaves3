package wildCaves;

import java.util.Random;

import net.minecraft.block.Block;

public class BlockStoneStalactite extends BlockStalactite {
	public BlockStoneStalactite(int id, boolean solidStalactites, boolean damageWhenFallenOn) {
		super(id, solidStalactites, damageWhenFallenOn);
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
}

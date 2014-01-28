package wildCaves;

import net.minecraft.block.Block;

public class ItemFossil extends MultiItemBlock {
	public static final String[] fossils = { "fossil1" };

	public ItemFossil(Block block) {
		super(block, fossils);
	}
}

package wildCaves;

import net.minecraft.block.Block;

public class ItemDecoration extends MultiItemBlock {
	public static final String[] icicles = { "icicle1", "icicle2", "icicle3" };

	public ItemDecoration(Block block) {
		super(block, icicles);
		setUnlocalizedName("decorationsBlock");
	}
}

package wildCaves;

import net.minecraft.block.Block;

public class ItemFlora extends MultiItemBlock {
	public static final String[] caps = { "glowcap1", "glowcap2", "glowcap3", "gloweed1", "glowcap4top", "glowcap4bottom", "bluecap1", "bluecap2", "bluecap3", "bluecap4" };

	public ItemFlora(Block block) {
		super(block, caps);
		setUnlocalizedName("floraBlock");
	}
}

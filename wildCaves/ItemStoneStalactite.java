package wildCaves;

import net.minecraft.block.Block;

public class ItemStoneStalactite extends ItemStalactite {
	public static final String[] stalacs = { "stalactite1", "stalactite2", "stalactite3", "stalactite4", "stalactiteConnection1", "stalactiteConnection2", "stalactiteConnection3",
			"stalactiteConnection4", "stalactiteEnd", "stalacmiteEnd", "stalacmite1", "stalacmite2", "stalacmite3" };

	public ItemStoneStalactite(Block block) {
		super(block, stalacs);
		setUnlocalizedName("stoneStalactiteBlock");
	}
}

package wildCaves;

public class ItemSandstoneStalactite extends ItemStalactite {
	public static final String[] sandStalacs = { "sandstoneStalactite1", "sandstoneStalactite2", "sandstoneStalactite3", "sandstoneStalactite4", "sandstoneStalactiteConnection1",
			"sandstoneStalactiteConnection2", "sandstoneStalactiteConnection3", "sandstoneStalactiteConnection4", "sandstoneStalactiteEnd", "sandstoneStalacmiteEnd", "sandstoneStalacmite1",
			"sandstoneStalacmite2", "sandstoneStalacmite3" };

	public ItemSandstoneStalactite(int par1) {
		super(par1, WildCaves.blockSandStalactite, sandStalacs);
		setUnlocalizedName("sandstoneStalactiteBlock");
	}
}

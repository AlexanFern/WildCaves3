package wildCaves;

public class ItemDecoration extends MultiItemBlock {
	public static final String[] icicles = { "icicle1", "icicle2", "icicle3" };

	public ItemDecoration(int par1) {
		super(par1, WildCaves.blockDecorations, icicles);
		setUnlocalizedName("decorationsBlock");
	}
}

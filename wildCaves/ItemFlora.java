package wildCaves;

public class ItemFlora extends MultiItemBlock {
	public static final String[] caps = { "glowcap1", "glowcap2", "glowcap3", "gloweed1", "glowcap4top", "glowcap4bottom", "bluecap1", "bluecap2", "bluecap3", "bluecap4" };

	public ItemFlora(int par1) {
		super(par1, WildCaves.blockFlora, caps);
		setUnlocalizedName("floraBlock");
	}
}

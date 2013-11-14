package wildCaves;

public class ItemFossil extends MultiItemBlock {
	public static final String[] fossils = { "fossil1" };

	public ItemFossil(int i) {
		super(i, WildCaves.blockFossils, fossils);
	}
}

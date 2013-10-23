package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Icon;

public class ItemDecoration extends MultiItemBlock {
	public ItemDecoration(int par1) {
		super(par1, "icicle1", "icicle2", "icicle3");
		setUnlocalizedName("decorationsBlock");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int damage) {
		if (damage > 13)
			damage = 13;
		return WildCaves.blockDecorations.getIcon(0, damage);
	}
}

package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Icon;

public class ItemFlora extends MultiItemBlock {
	public ItemFlora(int par1) {
		super(par1, "glowcap1", "glowcap2", "glowcap3", "gloweed1", "glowcap4top", "glowcap4bottom", "bluecap1", "bluecap2", "bluecap3", "bluecap4");
		setUnlocalizedName("floraBlock");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int damage) {
		if (damage > 13)
			damage = 13;
		return WildCaves.blockFlora.getIcon(0, damage);
	}
}

package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemFlora extends ItemBlock
{
	private final static String[] subNames = 
		{
		"glowcap1", "glowcap2",  "glowcap3", "gloweed1", "glowcap4top", "glowcap4bottom", "bluecap1", "bluecap2", "bluecap3", "bluecap4"
		};

	public ItemFlora(int par1, Block block) 
	{
		super(par1);
		setHasSubtypes(true);
		setUnlocalizedName("floraBlock");
	}
	
	@Override
	public int getMetadata(int damageValue)
    {
        return damageValue;
    }
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) 
	{
		return subNames[itemstack.getItemDamage()];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	 public Icon getIconFromDamage(int damage)
	 {
		 if (damage > 13)
			 damage=13;
		 return WildCaves.blockFlora.getIcon(0, damage);
	 }
}

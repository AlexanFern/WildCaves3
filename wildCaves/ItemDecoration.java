package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemDecoration extends ItemBlock
{
	private final static String[] subNames = 
		{
		"icicle1", "icicle2",  "icicle3"
		};

	public ItemDecoration(int par1, Block block) 
	{
		super(par1);
		setHasSubtypes(true);
		setUnlocalizedName("decorationsBlock");
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
		 return WildCaves.blockDecorations.getIcon(0, damage);
	 }
}

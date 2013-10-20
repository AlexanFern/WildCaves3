package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemFossil extends ItemBlock 
{
	private final static String[] subNames = 
		{
		"fossil1"
		};
	
	public ItemFossil(int id, Block block)
	{
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("fossilBlock");
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
}

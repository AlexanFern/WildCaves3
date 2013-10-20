package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemSandstoneStalactite extends ItemStalactite
{
	private final static String[] subNames = 
		{
		"sandstoneStalactite1", "sandstoneStalactite2",  "sandstoneStalactite3", "sandstoneStalactite4", "sandstoneStalactiteConnection1", "sandstoneStalactiteConnection2", 
		"sandstoneStalactiteConnection3", "sandstoneStalactiteConnection4", "sandstoneStalactiteEnd", "sandstoneStalacmiteEnd", "sandstoneStalacmite1", "sandstoneStalacmite2","sandstoneStalacmite3","-","--","---"
		};

	public ItemSandstoneStalactite(int par1, Block block) 
	{
		super(par1, block);
		setUnlocalizedName("sandstoneStalactiteBlock");
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
		 if (damage >= WildCaves.blockSandStalactite.getNumOfStructures())
			 damage=WildCaves.blockSandStalactite.getNumOfStructures()-1;
		 return WildCaves.blockSandStalactite.getIcon(0, damage);
	 }
}

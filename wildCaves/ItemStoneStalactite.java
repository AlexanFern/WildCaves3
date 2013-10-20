package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemStoneStalactite extends ItemStalactite
{
	private final static String[] subNames = 
		{
		"stalactite1", "stalactite2",  "stalactite3", "stalactite4", "stalactiteConnection1", "stalactiteConnection2", 
		"stalactiteConnection3", "stalactiteConnection4", "stalactiteEnd", "stalacmiteEnd", "stalacmite1", "stalacmite2","stalacmite3","","","icicle"
		};

	public ItemStoneStalactite(int par1, Block block) 
	{
		super(par1, block);
		setUnlocalizedName("stoneStalactiteBlock");
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
		 if (damage >= WildCaves.blockStoneStalactite.getNumOfStructures())
			 damage=WildCaves.blockStoneStalactite.getNumOfStructures()-1;
		 return WildCaves.blockStoneStalactite.getIcon(0, damage);
	 }
}

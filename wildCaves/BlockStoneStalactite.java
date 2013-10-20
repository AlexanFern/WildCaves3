package wildCaves;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneStalactite extends BlockStalactite
{
	@SideOnly(Side.CLIENT)
	private Icon[] iconArray;
	
	public BlockStoneStalactite(int id, boolean solidStalactites, boolean damageWhenFallenOn) 
	{
        super(id, solidStalactites, damageWhenFallenOn);
        setUnlocalizedName("stoneStalactiteBlock");
	}

	@Override
    public int idDropped(int metadata, Random random, int par3)
    {
    	return Block.cobblestone.blockID;
    }
    
	@Override
    public int quantityDropped(Random rand)
    {
        return rand.nextInt(3)-1;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) 
	{
        this.iconArray = new Icon[getNumOfStructures()];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":stoneStructure"+ i);
        }
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata)
    {
    	if(metadata >= getNumOfStructures())
    		metadata = getNumOfStructures() -1;
        return this.iconArray[metadata];
    }

    @Override
	public int getNumOfStructures() 
	{
		return super.getNumOfStructures();
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
    	return this.iconArray[blockAccess.getBlockMetadata(x, y, z)];
    }
}

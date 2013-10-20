package wildCaves;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecorations extends Block
{
	@SideOnly(Side.CLIENT)
	private Icon[] iconArray;
	private int numOfStructures=3;
	
	public BlockDecorations(int id) 
	{
        super(id, Material.rock);
        float f = 0.25F;
        this.setCreativeTab(WildCaves.tabWildCaves);
        setResistance(0.6F);
        setUnlocalizedName("decorationsBlock");
        this.setStepSound(soundGlassFootstep);
	}

	@Override
    public int idDropped(int metadata, Random random, int par3)
    {
    	return Block.ice.blockID;
    }
    
	@Override
    public int quantityDropped(Random rand)
    {
        return rand.nextInt(3)-1;
    }
    
    @Override
	public int damageDropped (int metadata) 
    {
		return 0;
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata)
    {
    	if(metadata >= numOfStructures)
    		metadata = numOfStructures-1;
        return this.iconArray[metadata];
    }
    
    /**
     * Get the block's damage value (for use with pick block).
     */
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }
    
    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
    	return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < numOfStructures; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
    	return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
    	return world.isBlockNormalCube(x, y+1, z) || world.getBlockId(x, y+1, z)==Block.ice.blockID;
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID)
    {
	    if( !world.isRemote && !this.canBlockStay(world, x, y, z))
	    {
	    	this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	    	world.setBlockToAir(x, y, z);
	    }
    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return 1;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
    
    @Override
     //Updates the blocks bounds based on its current state. Args: world, x, y, z
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        switch(metadata)
        {
        case 1:
        	this.setBlockBounds(0.25F, 0.2F, 0.25F, 0.75F, 1F, 0.75F);
        	break;
        case 2:
        	this.setBlockBounds(0.25F, 0.5F, 0.25F, 0.75F, 1F, 0.75F);
        	break;
        case 9:
        	this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F,0.8F, 0.75F);
        	break;
        case 10:
        	this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F,0.4F, 0.75F);
        	break;
        default:
        	this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
        	break;
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
    	return this.iconArray[blockAccess.getBlockMetadata(x, y, z)];
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) 
	{
        this.iconArray = new Icon[numOfStructures];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":decorations"+ i);
        }
	}
}

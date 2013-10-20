package wildCaves;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockStalactite extends Block
{
	private int numOfStructures = 13;
	private boolean solidStalactites;
	private boolean damageWhenFallenOn;
	@SideOnly(Side.CLIENT)
	private Icon[] iconArray;
	
	public BlockStalactite(int id, boolean solidStalactites, boolean damageWhenFallenOn) 
	{
        super(id, Material.rock);
        this.setHardness(0.8F);
        this.setCreativeTab(WildCaves.tabWildCaves);
        this.solidStalactites = solidStalactites;
        this.damageWhenFallenOn = damageWhenFallenOn;
        //this.setRequiresSelfNotify();
	}
	
	@SideOnly(Side.CLIENT)
	public abstract void registerIcons(IconRegister iconRegister);
	
	@Override
    @SideOnly(Side.CLIENT)
    public abstract Icon getIcon(int side, int metadata);
	
	@Override
    @SideOnly(Side.CLIENT)
    public abstract Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side);
	
    @Override
	public int damageDropped (int metadata) 
    {
		return 0;
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
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID)
    {
	    if( !world.isRemote && !this.canBlockStay(world, x, y, z))
	    {
	    	world.destroyBlock(x, y, z, true);
	    }
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
    	if(!world.isRemote && !this.canBlockStay(world, x, y, z))
    	{
    		this.dropBlockAsItem_do(world, x, y, z, new ItemStack(this.blockID, 1, world.getBlockMetadata(x, y, z)));
    		world.setBlockToAir(x, y, z);
    	}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < numOfStructures; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
    	boolean result = false; 
    	int metadata = world.getBlockMetadata(x, y, z);
    	if((metadata !=0 && metadata < 4)|| metadata == 7 || metadata == 11)
    		result = connected(world, x, y, z, true);
    	else if(metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12)
    		result = connected(world, x, y, z, false);
    	else if(metadata==0 || metadata==4 || metadata==5)
    		result = connected(world, x, y, z, true) || connected(world, x, y, z, false);;
    	return result;
    }
    
    //aux funtion for canblockStay
    public boolean connected(World world, int x, int y, int z, boolean searchUp)
    {
    	int increment;
    	int i;
    	if(searchUp)
    		increment = 1;
    	else
    		increment = -1;
    	
    	i=increment;
    		
   		while(world.getBlockId(x, y+i, z)==WildCaves.blockStoneStalactiteID || world.getBlockId(x, y+i, z)==WildCaves.blockSandStalactiteID)
       		i=i+increment;
   		return world.isBlockNormalCube(x, y+i, z);

    }
    
    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World world, int par2, int par3, int par4, Entity entity, float par6) 
    {
    	if(entity.isEntityAlive() && damageWhenFallenOn)
    	{
    		((EntityLiving) entity).attackEntityFrom(DamageSource.generic, 5);
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
    	if(solidStalactites)
    		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    	else
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
    
    public int getNumOfStructures()
    {
    	return numOfStructures;
    }
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	entity.motionX *= 0.7D;
    	entity.motionZ *= 0.7D;
    }
}

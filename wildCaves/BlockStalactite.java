package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockStalactite extends Block {
    private final Item droppedItem;
	private PropertyInteger ALL_TYPE;

	public BlockStalactite(Item drop) {
		super(Material.rock);
        this.droppedItem = drop;
		this.setHardness(0.8F);
		this.setCreativeTab(WildCaves.tabWildCaves);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ALL_TYPE, 0));
	}

	public int getNumOfStructures(){
		return WildCaves.sandStalacs.size();
	}

	public boolean isUp(IBlockState state){
		return getMetaFromState(state) < 4;
	}

	@Override
	protected BlockState createBlockState(){
		if(ALL_TYPE == null) {
			ALL_TYPE = PropertyInteger.create("type", 0, getNumOfStructures() - 1);
		}
		return new BlockState(this, new IProperty[]{ALL_TYPE});
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return (Integer) state.getValue(ALL_TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(ALL_TYPE, meta);
	}

    @Override
    public Item getItemDropped(IBlockState metadata, Random random, int par3) {
        return droppedItem;
    }

    @Override
    public int quantityDropped(Random rand) {
        return rand.nextInt(3) - 1;
    }

	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
		boolean result = false;
		int metadata = getMetaFromState(state);
		if ((metadata != 0 && metadata < 4) || metadata == 7 || metadata == 11)
			result = connected(world, pos, true);
		else if (metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12)
			result = connected(world, pos, false);
		else if (metadata == 0 || metadata == 4 || metadata == 5)
			result = connected(world, pos, true) || connected(world, pos, false);
		return result;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	//aux funtion for canblockStay
	public boolean connected(World world, BlockPos pos, boolean searchUp) {
		int increment;
		int i;
		if (searchUp)
			increment = 1;
		else
			increment = -1;
		i = increment;
		while (world.getBlockState(pos.up(i)).getBlock() == WildCaves.blockStoneStalactite || world.getBlockState(pos.up(i)).getBlock() == WildCaves.blockSandStalactite)
			i = i + increment;
		return world.getBlockState(pos.up(i)).getBlock().isNormalCube(world, pos.up(i));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World par1World, BlockPos pos, IBlockState state) {
		if (WildCaves.solidStalactites)
			return super.getCollisionBoundingBox(par1World, pos, state);
		else
			return null;
	}

	@Override
	public int getDamageValue(World world, BlockPos pos) {
		return getMetaFromState(world.getBlockState(pos));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < getNumOfStructures(); ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean isFullBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		this.updateTick(world, pos, state, null);
	}

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!this.canBlockStay(world, pos, state)){
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
        if(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode && ((EntityPlayer) entity).capabilities.isFlying){
            return;
        }
		entity.motionX *= 0.7D;
		entity.motionZ *= 0.7D;
	}

	@Override
	public void onFallenUpon(World world, BlockPos pos, Entity entity, float par6) {
		if (WildCaves.damageWhenFallenOn && entity.isEntityAlive()) {
			entity.attackEntityFrom(DamageSource.generic, 5);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {
		if (!world.isRemote && !this.canBlockStay(world, pos, state)) {
			world.destroyBlock(pos, true);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, BlockPos pos) {
		int metadata = getMetaFromState(par1IBlockAccess.getBlockState(pos));
		switch (metadata) {
		case 1:
			this.setBlockBounds(0.25F, 0.2F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		case 2:
			this.setBlockBounds(0.25F, 0.5F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		case 9:
			this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.8F, 0.75F);
			break;
		case 10:
			this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.4F, 0.75F);
			break;
		default:
			this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		}
	}
}

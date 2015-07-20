package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockDecorations extends Block {
	private PropertyInteger ALL_TYPE;

	public BlockDecorations() {
		super(Material.rock);
		this.setCreativeTab(WildCaves.tabWildCaves);
        setResistance(0.6F);
        setUnlocalizedName("decorationsBlock");
		this.setStepSound(soundTypeGlass);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ALL_TYPE, 0));
	}

	public int getNumOfStructures(){
		return WildCaves.icicles.size();
	}

	@Override
	protected BlockState createBlockState()
	{
		if(ALL_TYPE == null){
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

	public boolean canBlockStay(World world, BlockPos pos) {
		return world.getBlockState(pos.up()).getBlock().isNormalCube(world, pos) || world.getBlockState(pos.up()).getBlock().getMaterial().getMaterialMapColor() == MapColor.iceColor;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return canBlockStay(world, pos) && super.canPlaceBlockAt(world, pos);
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World par1World, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public int getDamageValue(World world, BlockPos pos) {
		return getMetaFromState(world.getBlockState(pos));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < getNumOfStructures(); ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item getItemDropped(IBlockState metadata, Random random, int par3) {
		return Item.getItemFromBlock(Blocks.ice);
	}

	@Override
	public boolean isFullBlock() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block) {
		if (!this.canBlockStay(world, pos)){
			world.setBlockToAir(pos);
		}
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3) - 1;
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

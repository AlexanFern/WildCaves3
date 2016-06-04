package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockDecorations extends Block {
	private PropertyInteger ALL_TYPE;

	public BlockDecorations() {
		super(Material.packedIce);
        this.setResistance(0.6F);
        this.setUnlocalizedName("decorationsBlock");
		this.setStepSound(SoundType.GLASS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ALL_TYPE, 0));
	}

	public int getNumOfStructures(){
		return WildCaves.icicles.size();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		if(ALL_TYPE == null){
			ALL_TYPE = PropertyInteger.create("type", 0, getNumOfStructures() - 1);
		}
		return new BlockStateContainer(this, ALL_TYPE);
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(ALL_TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(ALL_TYPE, meta);
	}

	public boolean canBlockStay(World world, BlockPos pos) {
		return world.getBlockState(pos.up()).isNormalCube() || world.getBlockState(pos.up()).getMapColor() == MapColor.iceColor;
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
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
		for (int i = 0; i < getNumOfStructures(); ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item getItemDropped(IBlockState metadata, Random random, int par3) {
		return Item.getItemFromBlock(Blocks.ice);
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
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isVisuallyOpaque() {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos) {
		return Utils.getBox(getMetaFromState(state));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isTranslucent(IBlockState state) {
		return true;
	}

	@Override
	public boolean isPassable(IBlockAccess access, BlockPos pos) {
		return true;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB alignedBB, List<AxisAlignedBB> list, Entity entity) {

	}
}

package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFlora extends BlockBush implements IShearable {
	private PropertyInteger ALL_TYPE;
	private static AxisAlignedBB LOW_AABB = new AxisAlignedBB(0.25F, 0F, 0.25F, 0.6F, 0.75F, 0.75F);
	private static AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.25F, 0F, 0.25F, 0.75F, 1F, 0.75F);

	public BlockFlora() {
		super(Material.plants);
		this.setLightOpacity(0);
		this.setStepSound(SoundType.PLANT);
        this.setResistance(0.6F);
        this.setUnlocalizedName("floraBlock");
		this.setDefaultState(this.blockState.getBaseState().withProperty(ALL_TYPE, 0));
	}

	public int getNumOfStructures(){
		return WildCaves.caps.size();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		if(ALL_TYPE == null) {
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

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        if(world.isBlockNormalCube(pos.down(), true))
            return true;
		IBlockState bellowId = world.getBlockState(pos.down());
		return bellowId.getMapColor() == MapColor.iceColor || (bellowId.getBlock() == this && getMetaFromState(bellowId) == 4);
	}

    @Override
    public int damageDropped(IBlockState meta){
        return 0;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
		for (int i = 0; i < getNumOfStructures(); ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item getItemDropped(IBlockState par1, Random par2Random, int par3) {
		return Items.glowstone_dust;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(world, pos, state))
			world.setBlockToAir(pos);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block blockID) {
		checkAndDropBlock(world, pos, state);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, getMetaFromState(world.getBlockState(pos))));
        ((World)world).setBlockToAir(pos);
		return ret;
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(2);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos) {
		int metadata = getMetaFromState(state);
		switch (metadata) {
		case 1:
			return LOW_AABB;
		case 2:
			return DEFAULT_AABB.setMaxY(0.4F);
		default:
			return DEFAULT_AABB;
		}
	}

	@Override//Can Place Block On
	protected boolean func_185514_i(IBlockState par1) {
		return true;
	}

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable){
        return true;
    }

    @Override
    public Block setLightLevel(float val){
        this.lightValue = (int)val;
        return this;
    }
}

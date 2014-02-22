package wildCaves;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecorations extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	private static final int numOfStructures = ItemDecoration.icicles.length;

	public BlockDecorations() {
		super(Material.rock);
		this.setCreativeTab(WildCaves.tabWildCaves);
        setResistance(0.6F);
        setBlockName("decorationsBlock");
		this.setStepSound(soundTypeGlass);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlock(x, y + 1, z).isNormalCube(world, x, y, z) || world.getBlock(x, y + 1, z) == Blocks.ice;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return canBlockStay(world, x, y, z) && super.canPlaceBlockAt(world, x, y, z);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (metadata >= numOfStructures)
			metadata = numOfStructures - 1;
		return this.iconArray[metadata];
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int par3) {
		return Item.getItemFromBlock(Blocks.ice);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!this.canBlockStay(world, x, y, z)){
            this.onBlockEventReceived(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(3) - 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconArray = new IIcon[numOfStructures];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":decorations" + i);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
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

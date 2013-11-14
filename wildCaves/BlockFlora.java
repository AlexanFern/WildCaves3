package wildCaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlora extends BlockFlower implements IShearable {
	@SideOnly(Side.CLIENT)
	private Icon[] iconArray;
	private static final int numOfStructures = ItemFlora.caps.length;

	public BlockFlora(int id) {
		super(id, Material.plants);
		this.setCreativeTab(WildCaves.tabWildCaves);
		this.setTickRandomly(false);
		this.setLightOpacity(0);
		this.setStepSound(soundGrassFootstep);
		setResistance(0.6F);
		setUnlocalizedName("floraBlock");
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		boolean result = false;
		int metadata = world.getBlockMetadata(x, y, z);
		int bellowId = world.getBlockId(x, y - 1, z);
		int metadataBellow = world.getBlockMetadata(x, y - 1, z);
		boolean solidBellow = world.isBlockNormalCube(x, y - 1, z);
		if (solidBellow || bellowId == Block.ice.blockID || (bellowId == this.blockID && metadataBellow == 4)) {
			result = true;
		}
		return result;
	}

	@Override
	public int damageDropped(int metadata) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		if (metadata >= numOfStructures)
			metadata = numOfStructures - 1;
		return this.iconArray[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.glowstone.itemID;
	}

	@Override
	public boolean isShearable(ItemStack item, World world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!this.canPlaceBlockAt(world, x, y, z))
			world.setBlock(x, y, z, 0);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
		if (!this.canPlaceBlockAt(world, x, y, z))
			world.setBlock(x, y, z, 0);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		world.setBlockToAir(x, y, z);
		return ret;
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.iconArray = new Icon[numOfStructures];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":flora" + i);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		//setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ)
		switch (metadata) {
		case 1:
			this.setBlockBounds(0.25F, 0F, 0.25F, 0.60F, 0.75F, 0.75F);
			break;
		case 2:
			this.setBlockBounds(0.25F, 0F, 0.25F, 0.75F, 0.4F, 0.75F);
			break;
		default:
			this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		}
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		return true;
	}
}

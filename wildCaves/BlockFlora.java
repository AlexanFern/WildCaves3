package wildCaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFlora extends BlockBush implements IShearable {
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	private final int numOfStructures;

	public BlockFlora(int number) {
		super(Material.plants);
        this.numOfStructures = number;
		this.setCreativeTab(WildCaves.tabWildCaves);
		this.setLightOpacity(0);
		this.setStepSound(soundTypeGrass);
        setResistance(0.6F);
        setBlockName("floraBlock");
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
        if(world.isBlockNormalCubeDefault(x, y - 1, z, false))
            return true;
		Block bellowId = world.getBlock(x, y - 1, z);
		return bellowId.getMaterial().getMaterialMapColor() == MapColor.iceColor || (bellowId == this && world.getBlockMetadata(x, y - 1, z) == 4);
	}

    @Override
    public int damageDropped(int meta){
        return 0;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (metadata >= numOfStructures)
			metadata = numOfStructures - 1;
		return this.iconArray[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Items.glowstone_dust;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void checkAndDropBlock(World world, int x, int y, int z) {
		if (!this.canBlockStay(world, x, y, z))
			world.setBlockToAir(x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block blockID) {
		checkAndDropBlock(world, x, y, z);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        ((World)world).setBlockToAir(x, y, z);
		return ret;
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconArray = new IIcon[numOfStructures];
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
	protected boolean canPlaceBlockOn(Block par1) {
		return true;
	}

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable){
        return true;
    }

    @Override
    public Block setLightLevel(float val){
        this.lightValue = (int)val;
        return this;
    }
}

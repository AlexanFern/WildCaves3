package wildCaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlora extends BlockBush implements IShearable {
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	private static final int numOfStructures = ItemFlora.caps.length;

	public BlockFlora() {
		super(Material.field_151585_k);
		this.func_149647_a(WildCaves.tabWildCaves);
		this.func_149713_g(0);
		this.func_149672_a(field_149779_h);
        func_149752_b(0.6F);
        func_149663_c("floraBlock");
	}

	@Override
	public boolean func_149718_j(World world, int x, int y, int z) {
		boolean result = false;
		Block bellowId = world.func_147439_a(x, y - 1, z);
		int metadataBellow = world.getBlockMetadata(x, y - 1, z);
		boolean solidBellow = world.func_147445_c(x, y - 1, z, false);
		if (solidBellow || bellowId == Blocks.ice || (bellowId == this && metadataBellow == 4)) {
			result = true;
		}
		return result;
	}

    @Override
    public int func_149692_a(int meta){
        return 0;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149691_a(int side, int metadata) {
		if (metadata >= numOfStructures)
			metadata = numOfStructures - 1;
		return this.iconArray[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149666_a(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item func_149650_a(int par1, Random par2Random, int par3) {
		return Items.glowstone_dust;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void func_149855_e(World world, int x, int y, int z) {
		if (!this.func_149718_j(world, x, y, z))
			world.func_147468_f(x, y, z);
	}

	@Override
	public void func_149695_a(World world, int x, int y, int z, Block blockID) {
		if (!this.func_149718_j(world, x, y, z))
			world.func_147468_f(x, y, z);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        ((World)world).func_147468_f(x, y, z);
		return ret;
	}

	@Override
	public int func_149745_a(Random rand) {
		return rand.nextInt(2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister iconRegister) {
		this.iconArray = new IIcon[numOfStructures];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":flora" + i);
		}
	}

	@Override
	public void func_149719_a(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		//setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ)
		switch (metadata) {
		case 1:
			this.func_149676_a(0.25F, 0F, 0.25F, 0.60F, 0.75F, 0.75F);
			break;
		case 2:
			this.func_149676_a(0.25F, 0F, 0.25F, 0.75F, 0.4F, 0.75F);
			break;
		default:
			this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		}
	}

	@Override
	protected boolean func_149854_a(Block par1) {
		return true;
	}
}

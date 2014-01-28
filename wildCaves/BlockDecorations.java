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
		super(Material.field_151576_e);
		this.func_149647_a(WildCaves.tabWildCaves);
        func_149752_b(0.6F);
        func_149663_c("decorationsBlock");
		this.func_149672_a(field_149778_k);
	}

	@Override
	public boolean func_149718_j(World world, int x, int y, int z) {
		return world.func_147439_a(x, y + 1, z).isNormalCube(world, x, y, z) || world.func_147439_a(x, y + 1, z) == Blocks.ice;
	}

	@Override
	public boolean func_149742_c(World world, int x, int y, int z) {
		return func_149718_j(world, x, y, z) && super.func_149742_c(world, x, y, z);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}

	@Override
	public AxisAlignedBB func_149668_a(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public int func_149643_k(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149691_a(int side, int metadata) {
		if (metadata >= numOfStructures)
			metadata = numOfStructures - 1;
		return this.iconArray[metadata];
	}

	@Override
	public int func_149645_b() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149666_a(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item func_149650_a(int metadata, Random random, int par3) {
		return Item.func_150898_a(Blocks.ice);
	}

	@Override
	public boolean func_149686_d() {
		return false;
	}

	@Override
	public void func_149695_a(World world, int x, int y, int z, Block block) {
		if (!this.func_149718_j(world, x, y, z)){
            this.func_149697_b(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.func_147468_f(x, y, z);
		}
	}

	@Override
	public int func_149745_a(Random rand) {
		return rand.nextInt(3) - 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister iconRegister) {
		this.iconArray = new IIcon[numOfStructures];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":decorations" + i);
		}
	}

	@Override
	public boolean func_149662_c() {
		return false;
	}

	@Override
	public void func_149719_a(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		switch (metadata) {
		case 1:
			this.func_149676_a(0.25F, 0.2F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		case 2:
			this.func_149676_a(0.25F, 0.5F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		case 9:
			this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 0.8F, 0.75F);
			break;
		case 10:
			this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 0.4F, 0.75F);
			break;
		default:
			this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		}
	}
}

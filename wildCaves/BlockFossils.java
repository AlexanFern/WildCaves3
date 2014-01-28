package wildCaves;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFossils extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	private int numOfStructures = ItemFossil.fossils.length;

	public BlockFossils() {
		super(Material.field_151576_e);
		this.func_149711_c(1F);
		this.func_149647_a(WildCaves.tabWildCaves);
        func_149663_c("fossilsBlock");
		this.func_149672_a(field_149780_i);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
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
	@SideOnly(Side.CLIENT)
	public void func_149666_a(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item func_149650_a(int metadata, Random random, int par3) {
		int choise = Utils.weightedChoise(0.5f, 0.15f, 0.05f, 0.5f, 0, 0);
		Item result = null;
		switch (choise) {
		case 1:
			result = Items.bone;
			break;
		case 2:
			result = Items.arrow;
			break;
		case 3:
			result = Items.skull;
			break;
		case 4:
			result = Item.func_150898_a(Blocks.cobblestone);
		}
		return result;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister iconRegister) {
		this.iconArray = new IIcon[numOfStructures];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":fossils" + i);
		}
	}
}

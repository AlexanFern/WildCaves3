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
	private final int numOfStructures;

	public BlockFossils(int number) {
		super(Material.rock);
        this.numOfStructures = number;
		this.setHardness(1F);
		this.setCreativeTab(WildCaves.tabWildCaves);
        setBlockName("fossilsBlock");
		this.setStepSound(soundTypePiston);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
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
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int par3) {
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
			result = Item.getItemFromBlock(Blocks.cobblestone);
		}
		return result;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconArray = new IIcon[numOfStructures];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + ":fossils" + i);
		}
	}
}

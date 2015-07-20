package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockFossils extends Block {
	public BlockFossils() {
		super(Material.rock);
		this.setHardness(1F);
		this.setCreativeTab(WildCaves.tabWildCaves);
        setUnlocalizedName("fossilsBlock");
		this.setStepSound(soundTypePiston);
	}

	@Override
	public Item getItemDropped(IBlockState metadata, Random random, int par3) {
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

}

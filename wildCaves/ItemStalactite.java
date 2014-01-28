package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemStalactite extends MultiItemBlock {
	public ItemStalactite(Block block, String... names) {
		super(block, names);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		Block blockId = world.func_147439_a(x, y, z);
		if (blockId == Blocks.snow && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (blockId != Blocks.vine && blockId != Blocks.tallgrass && blockId != Blocks.deadbush
				&& (blockId == Blocks.air || !blockId.isReplaceable(world, x, y, z))) {
			if (side == 0) {
				--y;
			}
			if (side == 1) {
				++y;
			}
			if (side == 2) {
				--z;
			}
			if (side == 3) {
				++z;
			}
			if (side == 4) {
				--x;
			}
			if (side == 5) {
				++x;
			}
		}
		if (itemStack.stackSize == 0) {
			return false;
		} else if (!par2EntityPlayer.canPlayerEdit(x, y, z, side, itemStack)) {
			return false;
		} else if (y == 255) {
			return false;
		} else if (world.func_147472_a(WildCaves.blockStoneStalactite, x, y, z, false, side, par2EntityPlayer, itemStack)
				|| world.func_147472_a(WildCaves.blockSandStalactite, x, y, z, false, side, par2EntityPlayer, itemStack)) {
			if (canPlace(itemStack, world, x, y, z)) {
				Block block = WildCaves.blockStoneStalactite;
				int j1 = this.getMetadata(itemStack.getItemDamage());
				int k1 = block.func_149660_a(world, x, y, z, side, par8, par9, par10, j1);
				if (placeBlockAt(itemStack, par2EntityPlayer, world, x, y, z, side, par8, par9, par10, k1)) {
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.field_149762_H.func_150496_b(), (block.field_149762_H.func_150497_c() + 1.0F) / 2.0F, block.field_149762_H.func_150494_d() * 0.8F);
					--itemStack.stackSize;
				}
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}

	private boolean canPlace(ItemStack itemStack, World world, int x, int y, int z) {
		boolean result = false;
		int metadata = getMetadata(itemStack.getItemDamage());
		boolean upNormal = world.func_147445_c(x, y + 1, z, false);
		boolean downNormal = world.func_147445_c(x, y - 1, z, false);
		boolean upStalactite = world.func_147439_a(x, y + 1, z) == WildCaves.blockStoneStalactite || world.func_147439_a(x, y + 1, z) == WildCaves.blockSandStalactite;
		boolean downStalactite = world.func_147439_a(x, y - 1, z) == WildCaves.blockStoneStalactite || world.func_147439_a(x, y - 1, z) == WildCaves.blockSandStalactite;
		if ((metadata == 0 || metadata == 4 || metadata == 5) && (upNormal || downNormal || upStalactite || downStalactite))
			result = true;
		else if ((metadata < 4 || metadata == 7 || metadata == 11) && (upNormal || upStalactite))
			result = true;
		else if ((metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12) && (downNormal || downStalactite))
			result = true;
		return result;
	}
}
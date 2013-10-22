package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public abstract class ItemStalactite extends MultiItemBlock {
	private BlockStalactite block;

	public ItemStalactite(int par1, BlockStalactite block, String... names) {
		super(par1, names);
		this.block = block;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int damage) {
		if (damage >= block.getNumOfStructures())
			damage = block.getNumOfStructures() - 1;
		return block.getIcon(0, damage);
	}

	@Override
	public int getMetadata(int damageValue) {
		if (damageValue > 13)
			damageValue = 13;
		return damageValue;
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		int blockId = world.getBlockId(x, y, z);
		if (blockId == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (blockId != Block.vine.blockID && blockId != Block.tallGrass.blockID && blockId != Block.deadBush.blockID
				&& (Block.blocksList[blockId] == null || !Block.blocksList[blockId].isBlockReplaceable(world, x, y, z))) {
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
		} else if (world.canPlaceEntityOnSide(WildCaves.blockStoneStalactiteID, x, y, z, false, side, par2EntityPlayer, itemStack)
				|| world.canPlaceEntityOnSide(WildCaves.blockSandStalactiteID, x, y, z, false, side, par2EntityPlayer, itemStack)) {
			if (canPlace(itemStack, world, x, y, z)) {
				Block block = Block.blocksList[WildCaves.blockStoneStalactiteID];
				int j1 = this.getMetadata(itemStack.getItemDamage());
				int k1 = Block.blocksList[WildCaves.blockStoneStalactiteID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, j1);
				if (placeBlockAt(itemStack, par2EntityPlayer, world, x, y, z, side, par8, par9, par10, k1)) {
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
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
		boolean upNormal = world.isBlockNormalCube(x, y + 1, z);
		boolean downNormal = world.isBlockNormalCube(x, y - 1, z);
		boolean upStalactite = world.getBlockId(x, y + 1, z) == WildCaves.blockStoneStalactiteID || world.getBlockId(x, y + 1, z) == WildCaves.blockSandStalactiteID;
		boolean downStalactite = world.getBlockId(x, y - 1, z) == WildCaves.blockStoneStalactiteID || world.getBlockId(x, y - 1, z) == WildCaves.blockSandStalactiteID;
		if ((metadata == 0 || metadata == 4 || metadata == 5) && (upNormal || downNormal || upStalactite || downStalactite))
			result = true;
		else if ((metadata < 4 || metadata == 7 || metadata == 11) && (upNormal || upStalactite))
			result = true;
		else if ((metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12) && (downNormal || downStalactite))
			result = true;
		return result;
	}
}
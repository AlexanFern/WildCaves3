package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemStalactite extends MultiItemBlock {
	public ItemStalactite(Block block, ArrayList<String> names) {
		super(block, names);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world, BlockPos pos, EnumHand hand, EnumFacing side, float par8, float par9, float par10) {
		IBlockState state = world.getBlockState(pos);
		Block blockId = state.getBlock();
		if (blockId == Blocks.snow && (state.getValue(BlockSnow.LAYERS)) < 1) {
			side = EnumFacing.UP;
		} else if (blockId != Blocks.tallgrass && blockId != Blocks.deadbush && (blockId == Blocks.air || !blockId.isReplaceable(world, pos))) {
			pos = pos.offset(side);
		}
		if (itemStack.stackSize > 0 && par2EntityPlayer.canPlayerEdit(pos, side, itemStack) && world.canBlockBePlaced(block, pos, false, side, par2EntityPlayer, itemStack)) {
			if (canPlace(itemStack, world, pos)) {
				int j1 = this.getMetadata(itemStack.getMetadata());
				IBlockState k1 = block.onBlockPlaced(world, pos, side, par8, par9, par10, j1, par2EntityPlayer);
				if (placeBlockAt(itemStack, par2EntityPlayer, world, pos, side, par8, par9, par10, k1)) {
					world.playSound(par2EntityPlayer, pos, block.getStepSound().getPlaceSound(), SoundCategory.BLOCKS, (block.getStepSound().getVolume() + 1.0F) / 2.0F, block.getStepSound().getPitch() * 0.8F);
					--itemStack.stackSize;
				}
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}

	private boolean canPlace(ItemStack itemStack, World world, BlockPos pos) {
		boolean result = false;
		int metadata = getMetadata(itemStack.getMetadata());
		boolean upNormal = world.isBlockNormalCube(pos.up(), false);
		boolean downNormal = world.isBlockNormalCube(pos.down(), false);
		boolean upStalactite = isStalactite(world.getBlockState(pos.up()));
		boolean downStalactite = isStalactite(world.getBlockState(pos.down()));
		if ((metadata == 0 || metadata == 4 || metadata == 5) && (upNormal || downNormal || upStalactite || downStalactite))
			result = true;
		else if ((metadata < 4 || metadata == 7 || metadata == 11) && (upNormal || upStalactite))
			result = true;
		else if ((metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12) && (downNormal || downStalactite))
			result = true;
		return result;
	}

	private boolean isStalactite(IBlockState state){
		return state.getBlock() == WildCaves.blockStoneStalactite || state.getBlock() == WildCaves.blockSandStalactite;
	}
}
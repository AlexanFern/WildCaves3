package wildCaves;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockStoneStalactite extends BlockStalactite {
	public BlockStoneStalactite() {
		super(Item.getItemFromBlock(Blocks.cobblestone));
        setUnlocalizedName("stoneStalactiteBlock");
	}

	@Override
	public int getNumOfStructures(){
		return WildCaves.stalacs.size();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		if (isUp(state)) {
			boolean isWatered = world.getBlockState(pos.up(2)).getMaterial().isLiquid();
			while (world.getBlockState(pos).getBlock() == this) {
				if (random.nextInt(5 + (isWatered ? 0 : 10)) == 0) {
					double d0 = pos.getX() + random.nextFloat();
					double d2 = pos.getZ() + random.nextFloat();
					double d1 = pos.getY() + 0.05D + (d0 - pos.getX()) * (d2 - pos.getZ());
					world.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
				pos = pos.down();
			}
		}
	}
}

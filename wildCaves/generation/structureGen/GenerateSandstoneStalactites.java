package wildCaves.generation.structureGen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wildCaves.Utils;
import wildCaves.WildCaves;

import java.util.Random;

public final class GenerateSandstoneStalactites extends GenerateStoneStalactite{
    public GenerateSandstoneStalactites(){
        super(WildCaves.blockSandStalactite);
    }

    @Override
    protected void generateStalactiteBase(World world, Random random, BlockPos topY) {
        super.generateStalactiteBase(world, random, topY);
        Utils.convertToSandType(world, random, topY);
    }

    @Override
    protected void generateStalagmiteBase(World world, Random random, BlockPos botY, int aux) {
        if (world.getBlockState(botY.down()) == Blocks.stone)
            world.setBlockState(botY.down(), Blocks.sandstone.getDefaultState(), 2);
        super.generateStalagmiteBase(world, random, botY, aux);
        Utils.convertToSandType(world, random, botY);
    }
}

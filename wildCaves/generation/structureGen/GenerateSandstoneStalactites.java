package wildCaves.generation.structureGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import wildCaves.Utils;
import wildCaves.WildCaves;
import net.minecraft.world.World;

public final class GenerateSandstoneStalactites extends GenerateStoneStalactite{
    public GenerateSandstoneStalactites(){
        super(WildCaves.blockSandStalactite);
    }

    @Override
    protected void generateStalactiteBase(World world, Random random, int x, int topY, int z) {
        super.generateStalactiteBase(world, random, x, topY, z);
        Utils.convertToSandType(world, random, x, topY, z);
    }

    @Override
    protected void generateStalagmiteBase(World world, Random random, int x, int botY, int z, int aux) {
        if (world.getBlock(x, botY - 1, z) == Blocks.stone)
            world.setBlock(x, botY - 1, z, Blocks.sandstone, 0, 2);
        super.generateStalagmiteBase(world, random, x, botY, z, aux);
        Utils.convertToSandType(world, random, x, botY, z);
    }
}

package wildCaves.generation.structureGen;

import net.minecraft.block.BlockVine;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import wildCaves.Utils;
import wildCaves.WildCaves;

import java.util.Random;

public final class DecorationHelper {

    //flood some cave with water around the specified x,y,z
    public static void generateFloodedCaves(World world, Random random, BlockPos pos){
        int blocks = 0;
        int x = pos.getX();
        int z = pos.getZ();
        for(int i=x-3;i<x+3;i++){
            for(int k=z-3; k<z+3;k++){
                blocks+= Utils.getNumEmptyBlocks(world, new BlockPos(i, pos.getY(), k));
                if(blocks > 400)
                    return;
            }
        }
        if(blocks > 150){
            int y = pos.getY() - (Utils.getNumEmptyBlocks(world, pos)/3);
            System.out.println(pos);
            for(int i=x-6; i<x+6; i++){
                for(int k=z-7; k<z+7; k++){
                    BlockPos temp = new BlockPos(i, y, k);
                    if(world.isAirBlock(temp)){
                        world.setBlockState(temp, Blocks.flowing_water.getDefaultState(), 2);
                        k++;
                    }
                }
            }
        }
    }

    //place Glowing mushrooms at the specified x,z and lowest y available
    public static boolean generateGlowcaps(World world, Random random, BlockPos pos) {
        int vary = Utils.getNumEmptyBlocks(world, pos);
        if(vary!=0){
            pos = pos.down(vary - 1);
        }
        if (!world.isAirBlock(pos.down())) {
            int glowcapNum;
            if (world.isAirBlock(pos.up()))
                glowcapNum = random.nextInt(5);
            else
                glowcapNum = random.nextInt(4);
            world.setBlockState(pos, WildCaves.blockFlora.getStateFromMeta(glowcapNum), 2);
            if (glowcapNum == 4) {
                world.setBlockState(pos.up(), WildCaves.blockFlora.getStateFromMeta(glowcapNum + 1), 2);
            }
            return true;
        } else
            return false;
    }

    //place iceshrooms surrounded by "frozen" type of blocks at the specified x,z and lowest y available
    public static void generateIceshrooms(World world, Random random, BlockPos pos) {
        int vary = Utils.getNumEmptyBlocks(world, pos);
        if(vary!=0){
            pos = pos.down(vary - 1);
        }
        if (!world.isAirBlock(pos.down())) {
            if (!world.getBlockState(pos).getMaterial().isLiquid()) {
                world.setBlockState(pos.down(), Utils.frozen.getDefaultState(), 2);
                world.setBlockState(pos, WildCaves.blockFlora.getStateFromMeta(Utils.randomChoise(6, 7, 8, 9)), 2);
            }
            Utils.convertToFrozenType(world, random, pos);
        }
    }

    //place icicle surrounded by "frozen" type of blocks  at the specified x,y,z
    public static void generateIcicles(World world, Random random, BlockPos pos, int distance) {
        world.setBlockState(pos.up(), Utils.frozen.getDefaultState(), 2);
        world.setBlockState(pos, WildCaves.blockDecorations.getStateFromMeta(Utils.randomChoise(0, 1, 2)), 2);
        Utils.convertToFrozenType(world, random, pos);
        BlockPos botY = pos.down(distance - 1);
        if (distance!=0 && !world.getBlockState(botY).getMaterial().isLiquid()) {
            Utils.convertToFrozenType(world, random, botY);
        }
    }

    //place a skull with random rotation at the specified x,y,z
    public static void generateSkulls(World world, Random random, BlockPos pos, int numEmptyBlocks) {
        if (numEmptyBlocks > 0) {
            BlockPos auxY = pos.down(numEmptyBlocks - 1);
            if (auxY.getY() > 0 && numEmptyBlocks>0) {
                world.setBlockState(auxY, Blocks.skull.getStateFromMeta(1), 2);
                TileEntity skullTE = world.getTileEntity(auxY);
                if (skullTE instanceof TileEntitySkull) {
                    ((TileEntitySkull) skullTE).setSkullRotation(random.nextInt(360));
                }
            }
        }
    }

    // place a vine from top to bottom at the specified x,y,z
    public static void generateVines(World world, Random random, BlockPos pos) {
        int distance = Utils.getNumEmptyBlocks(world, pos);
        int aux;
        if (distance > 5)
            aux = random.nextInt(distance-5)+5;
        else
            aux = distance;

        //the side the vine should be facing
        EnumFacing side = EnumFacing.values()[random.nextInt(4)+2];
        // length of the vine
        int i=0;
        while(i<aux && !world.getBlockState(pos.down(i)).getMaterial().isLiquid()){
            world.setBlockState(pos.down(i), Blocks.vine.getDefaultState().withProperty(BlockVine.getPropertyFor(side), true), 0);
            i+=1;
        }
    }
}

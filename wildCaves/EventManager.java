package wildCaves;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public final class EventManager
{
    private final WorldGenMinable[] mines = {new WorldGenMinable(WildCaves.blockFossils.getDefaultState(), 4), new WorldGenMinable(WildCaves.blockFossils.getDefaultState(), 5), new WorldGenMinable(WildCaves.blockFossils.getDefaultState(), 6)};
	private final int chanceForNodeToSpawn;
	public EventManager(int chanceForNodeToSpawn)
	{
		this.chanceForNodeToSpawn = chanceForNodeToSpawn;
	}

	@SubscribeEvent
	public void generate(OreGenEvent.Post oreGen){
        if (!WorldGenWildCaves.dimensionBlacklist.contains(oreGen.world.provider.getDimensionId())) {
            this.addOreSpawn(oreGen.rand.nextInt(mines.length), oreGen.world, oreGen.rand, oreGen.pos);
        }
    }

    /**
     * Adds an Ore Spawn to Minecraft
     * @param mine the type of ore generator
     * @param world to spawn in
     * @param pos to spawn around
     * @param random object for retrieving random positions within the world to spawn the Block
     **/
    private void addOreSpawn(int mine, World world, Random random, BlockPos pos)
    {
        for(int x = 0; x < chanceForNodeToSpawn; x++)
        {
            mines[mine].generate(world, random, pos.add(random.nextInt(16), 1 + random.nextInt(89), random.nextInt(16)));
        }
    }
}

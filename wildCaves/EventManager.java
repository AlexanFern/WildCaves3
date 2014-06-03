package wildCaves;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.OreGenEvent;

public class EventManager
{
    private final WorldGenMinable[] mines = {new WorldGenMinable(WildCaves.blockFossils, 4), new WorldGenMinable(WildCaves.blockFossils, 5), new WorldGenMinable(WildCaves.blockFossils, 6)};
	private final int chanceForNodeToSpawn;
	public EventManager(int chanceForNodeToSpawn)
	{
		this.chanceForNodeToSpawn = chanceForNodeToSpawn;
	}

	@SubscribeEvent
	public void generate(OreGenEvent.Post oreGen){
        if (!WorldGenWildCaves.dimensionBlacklist.contains(oreGen.world.provider.dimensionId)) {
            this.addOreSpawn(mines[oreGen.rand.nextInt(3)], oreGen.world, oreGen.rand, oreGen.worldX, oreGen.worldZ, 16, 16, chanceForNodeToSpawn, 1, 90);
        }
    }

    /**
     * Adds an Ore Spawn to Minecraft
     * @param mine the ore generator
     * @param world to spawn in
     * @param random object for retrieving random positions within the world to spawn the Block
     * @param blockXPos the X-Coordinate for the Generation method
     * @param blockZPos the Z-Coordinate for the Generation method
     * @param maxX maximum X-Coordinate values for spawning on the X-Axis on a Per-Chunk basis
     * @param maxZ maximum Z-Coordinate values for spawning on the Z-Axis on a Per-Chunk basis
     * @param chancesToSpawn Number of chances available for the Block to spawn per-chunk
     * @param minY minimum Y-Coordinate height at which this block may spawn
     * @param maxY maximum Y-Coordinate height at which this block may spawn
     **/
    public void addOreSpawn(WorldGenMinable mine, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int chancesToSpawn, int minY, int maxY)
    {
        assert minY > 0 && maxY > 0: "addOreSpawn: The Minimum Y and Maximum Y must be greater than 0";
        int diffBtwnMinMaxY = maxY - minY;
        if(maxY <= minY)
            diffBtwnMinMaxY = 1;
        assert maxX > 0 && maxX <= 16: "addOreSpawn: The Maximum X must be greater than 0 and less than 16";
        assert maxZ > 0 && maxZ <= 16: "addOreSpawn: The Maximum Z must be greater than 0 and less than 16";
        for(int x = 0; x < chancesToSpawn; x++)
        {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(diffBtwnMinMaxY);
            int posZ = blockZPos + random.nextInt(maxZ);
            mine.generate(world, random, posX, posY, posZ);
        }
    }
}

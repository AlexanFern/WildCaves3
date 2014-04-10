package wildCaves;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class EventManager implements IWorldGenerator
{
    private final WorldGenMinable[] mines = {new WorldGenMinable(WildCaves.blockFossils, 4), new WorldGenMinable(WildCaves.blockFossils, 5), new WorldGenMinable(WildCaves.blockFossils, 6)};
	private final int chanceForNodeToSpawn;
	public EventManager(int chanceForNodeToSpawn)
	{
		this.chanceForNodeToSpawn = chanceForNodeToSpawn;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.dimensionId==0)
        {
            this.addOreSpawn(mines[random.nextInt(3)], world, random, chunkX*16, chunkZ*16, 16, 16, chanceForNodeToSpawn, 1, 90);
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
        assert minY > 0: "addOreSpawn: The Minimum Y must be greater than 0";
        int diffBtwnMinMaxY = maxY - minY;
        if(maxY <= minY)
            diffBtwnMinMaxY = 1;
        assert maxY < 256: "addOreSpawn: The Maximum Y must be less than 256 but greater than 0";
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

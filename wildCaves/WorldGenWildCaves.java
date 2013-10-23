package wildCaves;

import java.util.Random;
import java.util.logging.Level;

import wildCaves.generation.biomeGen.GenerationArid;
import wildCaves.generation.biomeGen.GenerationFrozen;
import wildCaves.generation.biomeGen.GenerationHumid;
import wildCaves.generation.biomeGen.GenerationJungle;
import wildCaves.generation.biomeGen.GenerationNormal;
import wildCaves.generation.structureGen.GenerateFloodedCaves;
import wildCaves.generation.structureGen.GenerateStoneStalactite;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenWildCaves implements IWorldGenerator {
	private static boolean stalactites;
	private static boolean sandstoneStalactites;
	private static boolean Flora;
	private static float probabilityVinesJungle;
	private static float probabilityVines;
	private static float probabilityIcicle;
	private static float probabilityWet;
	private static float probabilityDry;
	private static float probabilityGlowcapsHumid;
	private static float probabilityGlowcaps;
	private static float probabilityIceshrooms;
	private static float probabilityStalactite;
	private static float probabilitySpiderWeb;
	private static float probabilitySandStalactites;
	private static float probabilitySkulls;
	private static int maxGenHeight;
	private static int maxLength;
	private static int timesPerChunck = 50;
	private static int maxGenHeightGlowcapNormal;
	private static int[] dimensionBlacklist;
	private static int[] blockWhiteList;
	private Configuration config;
	private GenerateStoneStalactite stalactiteGen;
	private GenerationJungle jungleGen;
	private GenerationHumid wetGen;
	private GenerationArid aridGen;
	private GenerationNormal normalGen;
	private GenerationFrozen frozenGen;
	private GenerateFloodedCaves floodCave;

	public WorldGenWildCaves(Configuration config) {
		setConfig(config);
		jungleGen = new GenerationJungle(probabilityStalactite, maxLength, probabilityVinesJungle, probabilityGlowcapsHumid, probabilitySpiderWeb, probabilitySkulls);
		wetGen = new GenerationHumid(probabilityStalactite, maxLength, probabilityWet, probabilityGlowcapsHumid, probabilityVines, probabilitySpiderWeb, probabilitySkulls);
		aridGen = new GenerationArid(probabilityStalactite, maxLength, probabilitySandStalactites, probabilitySpiderWeb, probabilityDry, probabilitySkulls);
		normalGen = new GenerationNormal(probabilityStalactite, maxLength, probabilityVines, probabilityGlowcaps, probabilitySpiderWeb, probabilitySkulls, maxGenHeightGlowcapNormal);
		frozenGen = new GenerationFrozen(probabilityStalactite, maxLength, probabilityIceshrooms, probabilitySpiderWeb, probabilityIcicle, probabilitySkulls);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		boolean genStalactiteNow;
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		int Xcoord;
		int Ycoord;
		int Zcoord;
		int dist;// distance
		BiomeGenBase biome;
		//if( world.provider.dimensionId != 1 && world.provider.dimensionId != -1) // !world.provider.isHellWorld)
		if (!Utils.arrayContainsInt(dimensionBlacklist, world.provider.dimensionId)) {
			for (int i = 0; i < timesPerChunck; i++) {
				genStalactiteNow = true;
				Xcoord = blockX + random.nextInt(16);
				Ycoord = random.nextInt(maxGenHeight);
				Zcoord = blockZ + random.nextInt(16);
				// search for the first available spot
				while (!(Utils.arrayContainsInt(blockWhiteList, world.getBlockId(Xcoord, Ycoord + 1, Zcoord)) && world.isAirBlock(Xcoord, Ycoord, Zcoord)) && Ycoord > 10) {
					Ycoord--;
				}
				// found a spot
				if (Ycoord > 10) {
					// getting the biome
					biome = world.getBiomeGenForCoords(blockX, blockZ);
					dist = Utils.getNumEmptyBlocks(world, Xcoord, Ycoord, Zcoord);
					if (biome.temperature <= 0.1f)
						frozenGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else if (biome.temperature > 1.5f && biome.rainfall < 0.1f)
						aridGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else if (biome.isHighHumidity() && biome.temperature > 1)
						jungleGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else if (biome.isHighHumidity() || biome == BiomeGenBase.ocean || biome == BiomeGenBase.river)
						wetGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else
						normalGen.generate(world, random, Xcoord, Ycoord, Zcoord);
				}
			}
		}
	}

	private void setConfig(Configuration config) {
		try {
			config.load();
			// --generation permissions------
			sandstoneStalactites = config.get("Permissions", "Generate Sandstone stalactites on arid biomes", true).getBoolean(true);
			Flora = config.get("Permissions", "Generate flora on caves", true).getBoolean(true);
			stalactites = config.get("Permissions", "Generate stalactites on caves", true).getBoolean(true);
			dimensionBlacklist = config.get("Permissions", "Dimension Blacklist", new int[] { -1, 1 }).getIntList();
			blockWhiteList = config.get("Permissions", "Block white list", new int[] { 1, 2, 3, 4, 13, 14, 15, 16, 21, 24, 56, 73, 74, 79, 80, 82, 97, 129 }).getIntList();
			// --Biome specific ratios------
			probabilityVinesJungle = (float) config.get("Biome specific", "Probability of vines on jungle caves", 0.5).getDouble(0.5);
			probabilityIcicle = (float) config.get("Biome specific", "Probability of iciles on frozen caves", 0.6).getDouble(0.6);
			probabilityWet = (float) config.get("Biome specific", "Probability of more water fountains on wet caves", 0.1).getDouble(0.1);
			probabilityDry = (float) config.get("Biome specific", "Probability of less generation arid caves", 0.5).getDouble(0.5);
			probabilityGlowcapsHumid = (float) config.get("Biome specific", "Probability of Glowing mushrooms on humid/jungle caves", 0.3).getDouble(0.3);
			probabilityIceshrooms = (float) config.get("Biome specific", "Probability of Glowing Ice mushrooms on frozen caves", 0.3).getDouble(0.3);
			probabilitySandStalactites = (float) config.get("Biome specific", "Probability of sandstone stalactites on arid caves", 0.5).getDouble(0.5);
			// --General ratios------
			probabilityVines = (float) config.get("Non biome specific", "Probability of vines on caves", 0.1).getDouble(0.1);
			probabilityGlowcaps = (float) config.get("Non biome specific", "Probability of glowing mushrooms on caves", 0.1).getDouble(0.1);
			probabilityStalactite = (float) config.get("Non biome specific", "Probability of Stalactites/stalagmites", 0.5).getDouble(0.5);
			probabilitySpiderWeb = (float) config.get("Non biome specific", "Probability of spider webs", 0.15).getDouble(0.15);
			maxGenHeightGlowcapNormal = config.get("Non biome specific", "Max height at wich to generate glowcaps on normal biomes", 30).getInt();
			probabilitySkulls = (float) config.get("Non biome specific", "Probability of skulls", 0.0001).getDouble(0.0001);
			// --other------
			timesPerChunck = config.get(Configuration.CATEGORY_GENERAL, "times to attemp generating per chunk", 40).getInt();
			maxGenHeight = config.get(Configuration.CATEGORY_GENERAL, "Max height at wich to generate", 80).getInt();
			maxLength = config.get(Configuration.CATEGORY_GENERAL, "Max length of structure generation", 8).getInt();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "WildCaves3 has a problem loading it's configuration");
		} finally {
			config.save();
		}
	}
}

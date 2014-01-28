package wildCaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import wildCaves.generation.biomeGen.GenerationArid;
import wildCaves.generation.biomeGen.GenerationFrozen;
import wildCaves.generation.biomeGen.GenerationHumid;
import wildCaves.generation.biomeGen.GenerationJungle;
import wildCaves.generation.biomeGen.GenerationNormal;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenWildCaves implements IWorldGenerator {
	public static float probabilityVinesJungle;
	public static float probabilityVines;
	public static float probabilityIcicle;
	public static float probabilityWet;
	public static float probabilityDry;
	public static float probabilityGlowcapsHumid;
	public static float probabilityGlowcaps;
	public static float probabilityIceshrooms;
	public static float probabilityStalactite;
	public static float probabilitySpiderWeb;
	public static float probabilitySandStalactites;
	public static float probabilitySkulls;
	public static int maxGenHeight;
	public static int maxLength;
	private static int timesPerChunck = 50;
	public static int maxGenHeightGlowcapNormal;
	private static List<Integer> dimensionBlacklist = new ArrayList<Integer>();
	private static List<Block> blockWhiteList = new ArrayList<Block>();
	private static final GenerationJungle jungleGen = new GenerationJungle();
	private static final GenerationHumid wetGen = new GenerationHumid();
	private static final GenerationArid aridGen = new GenerationArid();
	private static final GenerationNormal normalGen = new GenerationNormal();
	private static final GenerationFrozen frozenGen = new GenerationFrozen();

	public WorldGenWildCaves(Configuration config) {
		setConfig(config);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		int Xcoord;
		int Ycoord;
		int Zcoord;
		//int dist;// distance
		BiomeGenBase biome;
		if (!dimensionBlacklist.contains(world.provider.dimensionId)) {
			for (int i = 0; i < timesPerChunck; i++) {
				Xcoord = blockX + random.nextInt(16);
				Zcoord = blockZ + random.nextInt(16);
                Ycoord = Math.min(world.getHeightValue(Xcoord, Zcoord)-1, random.nextInt(maxGenHeight));
				// search for the first available spot
				while (Ycoord > 10 && (!blockWhiteList.contains(world.func_147439_a(Xcoord, Ycoord + 1, Zcoord)) || !world.func_147437_c(Xcoord, Ycoord, Zcoord))) {
					Ycoord--;
				}
				// found a spot
				if (Ycoord > 10) {
					// getting the biome
					biome = world.getBiomeGenForCoords(Xcoord, Zcoord);
					//dist = Utils.getNumEmptyBlocks(world, Xcoord, Ycoord, Zcoord);
					if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FROZEN))
						frozenGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else if (biome.temperature > 1.5f && biome.rainfall < 0.1f)
						aridGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.JUNGLE))
						jungleGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else if (biome.isHighHumidity() || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER))
						wetGen.generate(world, random, Xcoord, Ycoord, Zcoord);
					else
						normalGen.generate(world, random, Xcoord, Ycoord, Zcoord);
				}
			}
		}
	}

	private static void setConfig(Configuration config) {
        // --generation permissions------
        boolean sandstoneStalactites = config.get("Permissions", "Generate Sandstone stalactites on arid biomes", true).getBoolean(true);
        boolean flora = config.get("Permissions", "Generate flora on caves", true).getBoolean(true);
        boolean stalactites = config.get("Permissions", "Generate stalactites on caves", true).getBoolean(true);
        String[] list = config.get("Permissions", "Dimension Blacklist", "-1,1").getString().split(",");
        for (String txt : list) {
            try {
                dimensionBlacklist.add(Integer.parseInt(txt.trim()));
            } catch (NumberFormatException n) {
            }
        }
        list = config.get("Permissions", "Block white list", "stone,grass,dirt,cobblestone,gravel,gold_ore,iron_ore,coal_ore,lapis_ore,sandstone,diamond_ore,redstone_ore,lit_redstone_ore,ice,snow,clay,monster_egg,emerald_ore").getString().split(",");
        Block block;
        for (String txt : list) {
            try {
                block = GameData.blockRegistry.getObject(txt.trim());
                if(block!=null){
                    blockWhiteList.add(block);
                }
            } catch (Exception n) {
            }
        }
        // --Biome specific ratios------
        probabilityVinesJungle = (float) config.get("Biome specific", "Probability of vines on jungle caves", 0.5).getDouble(0.5);
        probabilityIcicle = (float) config.get("Biome specific", "Probability of icicles on frozen caves", 0.6).getDouble(0.6);
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
        maxGenHeightGlowcapNormal = config.get("Non biome specific", "Max height at which to generate glowcaps on normal biomes", 30).getInt();
        probabilitySkulls = (float) config.get("Non biome specific", "Probability of skulls", 0.0001).getDouble(0.0001);
        if(!sandstoneStalactites){
            probabilitySandStalactites = 0;
        }
        if(!flora){
            probabilityGlowcaps = 0;
            probabilityVinesJungle = 0;
            probabilityGlowcapsHumid = 0;
            probabilityIceshrooms = 0;
            probabilityVines = 0;
            probabilityGlowcaps = 0;
        }
        if(!stalactites){
            probabilityStalactite = 0;
            probabilitySandStalactites = 0;
        }
        // --other------
        timesPerChunck = config.get(Configuration.CATEGORY_GENERAL, "times to attempt generating per chunk", 40).getInt();
        maxGenHeight = config.get(Configuration.CATEGORY_GENERAL, "Max height at which to generate", 80).getInt();
        maxLength = config.get(Configuration.CATEGORY_GENERAL, "Max length of structure generation", 8).getInt();
        if(config.hasChanged()){
            config.save();
        }
	}
}

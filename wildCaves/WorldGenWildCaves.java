package wildCaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import wildCaves.generation.biomeGen.GenerationArid;
import wildCaves.generation.biomeGen.GenerationFrozen;
import wildCaves.generation.biomeGen.GenerationHumid;
import wildCaves.generation.biomeGen.GenerationJungle;
import wildCaves.generation.biomeGen.GenerationNormal;

public class WorldGenWildCaves {
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
	public static List<Integer> dimensionBlacklist = new ArrayList<Integer>();
	private static List<Block> blockWhiteList = new ArrayList<Block>();
	private static final GenerationJungle jungleGen = new GenerationJungle();
	private static final GenerationHumid wetGen = new GenerationHumid();
	private static final GenerationArid aridGen = new GenerationArid();
	private static final GenerationNormal normalGen = new GenerationNormal();
	private static final GenerationFrozen frozenGen = new GenerationFrozen();

	public WorldGenWildCaves(Configuration config) {
		setConfig(config);
	}

    public static boolean isWhiteListed(Block block){
        return blockWhiteList.contains(block);
    }

    @SubscribeEvent
    public void decorate(DecorateBiomeEvent.Post decorationEvent){
        generate(decorationEvent.rand, decorationEvent.chunkX + 8, decorationEvent.chunkZ + 8, decorationEvent.world);
    }

	public void generate(Random random, int blockX, int blockZ, World world) {
		if (!dimensionBlacklist.contains(world.provider.dimensionId)) {
            int Xcoord;
            int Ycoord;
            int Zcoord;
            //int dist;// distance
            BiomeGenBase biome;
			for (int i = 0; i < timesPerChunck; i++) {
				Xcoord = blockX + random.nextInt(16);
				Zcoord = blockZ + random.nextInt(16);
                Ycoord = Math.min(world.getHeightValue(Xcoord, Zcoord)-1, random.nextInt(maxGenHeight));
				// search for the first available spot
				while (Ycoord > 10 && (!blockWhiteList.contains(world.getBlock(Xcoord, Ycoord + 1, Zcoord)) || !world.isAirBlock(Xcoord, Ycoord, Zcoord))) {
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
        String category = "Permissions";
        boolean sandstoneStalactites = config.get(category, "Generate Sandstone stalactites on arid biomes", true).getBoolean(true);
        boolean flora = config.get(category, "Generate flora on caves", true).getBoolean(true);
        boolean stalactites = config.get(category, "Generate stalactites on caves", true).getBoolean(true);
        String[] list = config.get(category, "Dimension Blacklist", "-1,1").getString().split(",");
        for (String txt : list) {
            try {
                dimensionBlacklist.add(Integer.parseInt(txt.trim()));
            } catch (NumberFormatException n) {
            }
        }
        list = config.get(category, "Block white list", "stone,grass,dirt,cobblestone,gravel,gold_ore,iron_ore,coal_ore,lapis_ore,sandstone,diamond_ore,redstone_ore,lit_redstone_ore,ice,snow,clay,monster_egg,emerald_ore").getString().split(",");
        Block block;
        for (String txt : list) {
            try {
                block = GameData.blockRegistry.getObject(txt.trim());
                if(block != null && block.getMaterial() != Material.air){
                    blockWhiteList.add(block);
                }
            } catch (Exception n) {
            }
        }
        // --Biome specific ratios------
        category = "Biome specific";
        probabilityVinesJungle = (float) config.get(category, "Probability of vines on jungle caves", 0.5).getDouble(0.5);
        probabilityIcicle = (float) config.get(category, "Probability of icicles on frozen caves", 0.6).getDouble(0.6);
        try{
            block = GameData.blockRegistry.getObject(config.get(category, "Block to generate in frozen caves", "ice").getString().trim());
            if(block!=null && block.getMaterial().getMaterialMapColor()== MapColor.iceColor){
                Utils.frozen = block;
            }
        }catch (Exception n){
        }
        probabilityWet = (float) config.get(category, "Probability of more water fountains on wet caves", 0.1).getDouble(0.1);
        probabilityDry = (float) config.get(category, "Probability of less generation arid caves", 0.5).getDouble(0.5);
        probabilityGlowcapsHumid = (float) config.get(category, "Probability of Glowing mushrooms on humid/jungle caves", 0.3).getDouble(0.3);
        probabilityIceshrooms = (float) config.get(category, "Probability of Glowing Ice mushrooms on frozen caves", 0.3).getDouble(0.3);
        probabilitySandStalactites = (float) config.get(category, "Probability of sandstone stalactites on arid caves", 0.5).getDouble(0.5);
        // --General ratios------
        category = "Non biome specific";
        probabilityVines = (float) config.get(category, "Probability of vines on caves", 0.1).getDouble(0.1);
        probabilityGlowcaps = (float) config.get(category, "Probability of glowing mushrooms on caves", 0.1).getDouble(0.1);
        probabilityStalactite = (float) config.get(category, "Probability of Stalactites/stalagmites", 0.5).getDouble(0.5);
        probabilitySpiderWeb = (float) config.get(category, "Probability of spider webs", 0.15).getDouble(0.15);
        maxGenHeightGlowcapNormal = config.get(category, "Max height at which to generate glowcaps on normal biomes", 30).getInt();
        probabilitySkulls = (float) config.get(category, "Probability of skulls", 0.0001).getDouble(0.0001);
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
        category = Configuration.CATEGORY_GENERAL;
        timesPerChunck = config.get(category, "times to attempt generating per chunk", 40).getInt();
        maxGenHeight = config.get(category, "Max height at which to generate", 80).getInt();
        maxLength = config.get(category, "Max length of structure generation", 8).getInt();
        if(config.hasChanged()){
            config.save();
        }
	}
}

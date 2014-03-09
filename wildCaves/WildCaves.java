package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = WildCaves.modid, name = "Wild Caves 3", useMetadata = true)
public class WildCaves {
	public static final String modid = "wildcaves3";
	public static Block blockFlora, blockDecorations, blockFossils;
	public static Block blockStoneStalactite, blockSandStalactite;
	public static int floraLightLevel;
	public static int chanceForNodeToSpawn;
	public static boolean solidStalactites, damageWhenFallenOn;
	public static Configuration config;
	private EventManager eventmanager;
	public static CreativeTabs tabWildCaves = new CreativeTabs("WildCaves3") {
        @Override
        public Item getTabIconItem() {
            return Items.ender_eye;
        }
    };

	public void initBlocks() {
        blockStoneStalactite = GameRegistry.registerBlock(new BlockStoneStalactite(), ItemStoneStalactite.class, "StoneStalactite");
        blockSandStalactite = GameRegistry.registerBlock(new BlockSandstoneStalactite(), ItemSandstoneStalactite.class, "SandstoneSalactite");
        blockDecorations = GameRegistry.registerBlock(new BlockDecorations(), ItemDecoration.class, "Decorations");
        blockFlora = GameRegistry.registerBlock(new BlockFlora().setLightLevel(floraLightLevel), ItemFlora.class, "Flora");
        blockFossils = GameRegistry.registerBlock(new BlockFossils(), ItemFossil.class, "FossilBlock");
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		WorldGenWildCaves gen = new WorldGenWildCaves(config);
		if (gen.maxLength > 0) {
			GameRegistry.registerWorldGenerator(gen, 5);
		}
		//new itemstack(itemID, stackSize, damage)
		for (String txt : new String[] { "DUNGEON_CHEST", "MINESHAFT_CORRIDOR", "STRONGHOLD_CORRIDOR" }) {
			for (int i = 0; i < 5; i++) {
				if (i != 1) {
					ChestGenHooks.getInfo(txt).addItem(new WeightedRandomChestContent(new ItemStack(Items.skull, 1, i), 1, 2, 50));//skeleton//zombie//steve//creeper
				}
			}
		}
        eventmanager = new EventManager(chanceForNodeToSpawn);
        GameRegistry.registerWorldGenerator(eventmanager, 10);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
        solidStalactites = config.get(Configuration.CATEGORY_GENERAL, "Solid stalactites/stalgmites", false).getBoolean(false);
        damageWhenFallenOn = config.get(Configuration.CATEGORY_GENERAL, "Stalgmites damage entities when fallen on", false).getBoolean(false);
        floraLightLevel = config.get(Configuration.CATEGORY_GENERAL, "Flora light level", 5).getInt(5);
        if (floraLightLevel > 15)
            floraLightLevel = 15;
        chanceForNodeToSpawn = config.get(Configuration.CATEGORY_GENERAL, "Chance for a fossil node to generate", 5).getInt(5);
        config.save();
        initBlocks();
	}
}

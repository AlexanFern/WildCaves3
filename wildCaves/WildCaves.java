package wildCaves;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;

@Mod(modid = WildCaves.modid, name = "Wild Caves 3", version = "${version}")
public final class WildCaves {
	public static final String modid = "wildcaves3";
    public static final ArrayList<String> stalacs = new ArrayList<String>(Arrays.asList("stalactite1", "stalactite2", "stalactite3", "stalactite4", "stalactiteConnection1", "stalactiteConnection2", "stalactiteConnection3",
            "stalactiteConnection4", "stalactiteEnd", "stalacmiteEnd", "stalacmite1", "stalacmite2", "stalacmite3"));
    public static final ArrayList<String> sandStalacs = new ArrayList<String>(Arrays.asList("sandstoneStalactite1", "sandstoneStalactite2", "sandstoneStalactite3", "sandstoneStalactite4", "sandstoneStalactiteConnection1",
            "sandstoneStalactiteConnection2", "sandstoneStalactiteConnection3", "sandstoneStalactiteConnection4", "sandstoneStalactiteEnd", "sandstoneStalacmiteEnd", "sandstoneStalacmite1",
            "sandstoneStalacmite2", "sandstoneStalacmite3"));
    public static final ArrayList<String> icicles = new ArrayList<String>(Arrays.asList("icicle1", "icicle2", "icicle3"));
    public static final ArrayList<String> caps = new ArrayList<String>(Arrays.asList("glowcap1", "glowcap2", "glowcap3", "gloweed1", "glowcap4top", "glowcap4bottom", "bluecap1", "bluecap2", "bluecap3", "bluecap4"));
	public static final ArrayList<String> fossils = new ArrayList<String>(Arrays.asList("fossil1"));
    public static Block blockFlora, blockDecorations, blockFossils;
	public static Block blockStoneStalactite, blockSandStalactite;
	public static int floraLightLevel;
	public static int chanceForNodeToSpawn, chestSkull;
	public static boolean solidStalactites, damageWhenFallenOn;
	public static Configuration config;
	public static final CreativeTabs tabWildCaves = new CreativeTabs("WildCaves3") {
        @Override
        public Item getTabIconItem() {
            return Items.ender_eye;
        }
    };

	public void initBlocks() {
        blockStoneStalactite = GameRegistry.registerBlock(new BlockStoneStalactite(stalacs.size()), ItemStalactite.class, "StoneStalactite", stalacs);
        blockSandStalactite = GameRegistry.registerBlock(new BlockStalactite(sandStalacs.size(), Item.getItemFromBlock(Blocks.sandstone)).
                setBlockName("sandstoneStalactiteBlock").setBlockTextureName(":sandstoneStructure"), ItemStalactite.class, "SandstoneSalactite", sandStalacs);
        blockDecorations = GameRegistry.registerBlock(new BlockDecorations(icicles.size()), MultiItemBlock.class, "Decorations", icicles);
        blockFlora = GameRegistry.registerBlock(new BlockFlora(caps.size()).setLightLevel(floraLightLevel), MultiItemBlock.class, "Flora", caps);
        blockFossils = GameRegistry.registerBlock(new BlockFossils(fossils.size()), MultiItemBlock.class, "FossilBlock", fossils);
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		WorldGenWildCaves gen = new WorldGenWildCaves(config);
		if (gen.maxLength > 0) {
            MinecraftForge.EVENT_BUS.register(gen);
		}
        if(chestSkull > 0)
            for (String txt : new String[] { ChestGenHooks.DUNGEON_CHEST, ChestGenHooks.MINESHAFT_CORRIDOR, ChestGenHooks.STRONGHOLD_CORRIDOR }) {
                for (int i = 0; i < 5; i++) {
                    if (i != 1) {
                        ChestGenHooks.getInfo(txt).addItem(new WeightedRandomChestContent(new ItemStack(Items.skull, 1, i), 1, 2, chestSkull));//skeleton//zombie//steve//creeper
                    }
                }
            }
        if(chanceForNodeToSpawn > 0) {
            EventManager eventmanager = new EventManager(chanceForNodeToSpawn);
            MinecraftForge.ORE_GEN_BUS.register(eventmanager);
        }
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
        chestSkull = config.get(Configuration.CATEGORY_GENERAL, "Chance for a skull to be added in chests", 50).getInt(50);
        config.save();
        initBlocks();
        if(event.getSourceFile().getName().endsWith(".jar") && event.getSide().isClient()){
            try {
                Class.forName("mods.mud.ModUpdateDetector").getDeclaredMethod("registerMod", ModContainer.class, String.class, String.class).invoke(null,
                        FMLCommonHandler.instance().findContainerFor(this),
                        "https://raw.github.com/GotoLink/WildCaves3/master/update.xml",
                        "https://raw.github.com/GotoLink/WildCaves3/master/changelog.md"
                );
            } catch (Throwable e) {
            }
        }
	}
}

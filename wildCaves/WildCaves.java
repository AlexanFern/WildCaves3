package wildCaves;

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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;

@Mod(modid = "wildcaves3", name = "Wild Caves 3", version = "${version}")
public final class WildCaves {
    @SidedProxy(clientSide = "wildCaves.ClientProxy", serverSide = "wildCaves.ServerProxy")
    public static ServerProxy proxy;

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
	public static boolean solidStalactites, damageWhenFallenOn;
	private static Configuration config;
	public static final CreativeTabs tabWildCaves = new CreativeTabs("WildCaves3") {
        @Override
        public Item getTabIconItem() {
            return Items.ender_eye;
        }
    };

	@EventHandler
	public void load(FMLInitializationEvent event) {
		WorldGenWildCaves gen = new WorldGenWildCaves(config);
		if (gen.maxLength > 0) {
            MinecraftForge.EVENT_BUS.register(gen);
		}
        proxy.registerRenders();
	}

    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
        solidStalactites = config.getBoolean("Solid stalactites/stalgmites", Configuration.CATEGORY_GENERAL, false, "Whether stalactites can be collided with.");
        damageWhenFallenOn = config.getBoolean("Stalgmites damage entities when fallen on", Configuration.CATEGORY_GENERAL, false, "Whether living beings would be damaged when falling on the block.");
        int floraLightLevel = config.getInt("Flora light level", Configuration.CATEGORY_GENERAL, 5, 0, 15, "How much light is emitted by the mushrooms.");

        blockStoneStalactite = GameRegistry.registerBlock(new BlockStoneStalactite(), ItemStalactite.class, "StoneStalactite", stalacs);
        blockSandStalactite = GameRegistry.registerBlock(new BlockStalactite(Item.getItemFromBlock(Blocks.sandstone)).
                setUnlocalizedName("sandstoneStalactiteBlock"), ItemStalactite.class, "SandstoneSalactite", sandStalacs);
        blockDecorations = GameRegistry.registerBlock(new BlockDecorations(), MultiItemBlock.class, "Decorations", icicles);
        blockFlora = GameRegistry.registerBlock(new BlockFlora().setLightLevel(floraLightLevel), MultiItemBlock.class, "Flora", caps);
        blockFossils = GameRegistry.registerBlock(new BlockFossils(), MultiItemBlock.class, "FossilBlock", fossils);

        int chanceForNodeToSpawn = config.get(Configuration.CATEGORY_GENERAL, "Chance for a fossil node to generate", 5).getInt();
        if(chanceForNodeToSpawn > 0) {
            MinecraftForge.ORE_GEN_BUS.register(new EventManager(chanceForNodeToSpawn));
        }
        int chestSkull = config.get(Configuration.CATEGORY_GENERAL, "Chance for a skull to be added in chests", 50).getInt();
        if(chestSkull > 0)
            for (String txt : new String[] { ChestGenHooks.DUNGEON_CHEST, ChestGenHooks.MINESHAFT_CORRIDOR, ChestGenHooks.STRONGHOLD_CORRIDOR }) {
                for (int i = 0; i < 5; i++) {
                    if (i != 1) {
                        ChestGenHooks.getInfo(txt).addItem(new WeightedRandomChestContent(new ItemStack(Items.skull, 1, i), 1, 2, chestSkull));//skeleton//zombie//steve//creeper
                    }
                }
            }

        if(event.getSourceFile().getName().endsWith(".jar")){
            proxy.MUD();
        }
	}
}

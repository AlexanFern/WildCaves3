package wildCaves;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
    private static int chestSkull = 50;

	@EventHandler
	public void load(FMLInitializationEvent event) {
		WorldGenWildCaves gen = new WorldGenWildCaves(config);
		if (gen.maxLength > 0)
            MinecraftForge.EVENT_BUS.register(gen);
        if(chestSkull > 0)
            MinecraftForge.EVENT_BUS.register(this);
        proxy.registerRenders();
	}

    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
        solidStalactites = config.getBoolean("Solid stalactites/stalgmites", Configuration.CATEGORY_GENERAL, false, "Whether stalactites can be collided with.");
        damageWhenFallenOn = config.getBoolean("Stalgmites damage entities when fallen on", Configuration.CATEGORY_GENERAL, false, "Whether living beings would be damaged when falling on the block.");
        int floraLightLevel = config.getInt("Flora light level", Configuration.CATEGORY_GENERAL, 5, 0, 15, "How much light is emitted by the mushrooms.");

        CreativeTabs tabWildCaves = new CreativeTabs("WildCaves3") {
            @Override
            public Item getTabIconItem() {
                return Items.ender_eye;
            }
        };
        blockStoneStalactite = GameRegistry.registerBlock(new BlockStoneStalactite().setCreativeTab(tabWildCaves), ItemStalactite.class, "StoneStalactite", stalacs);
        blockSandStalactite = GameRegistry.registerBlock(new BlockStalactite(Item.getItemFromBlock(Blocks.sandstone)).
                setUnlocalizedName("sandstoneStalactiteBlock").setCreativeTab(tabWildCaves), ItemStalactite.class, "SandstoneSalactite", sandStalacs);
        blockDecorations = GameRegistry.registerBlock(new BlockDecorations().setCreativeTab(tabWildCaves), MultiItemBlock.class, "Decorations", icicles);
        blockFlora = GameRegistry.registerBlock(new BlockFlora().setLightLevel(floraLightLevel).setCreativeTab(tabWildCaves), MultiItemBlock.class, "Flora", caps);
        blockFossils = GameRegistry.registerBlock(new BlockFossils().setCreativeTab(tabWildCaves), MultiItemBlock.class, "FossilBlock", fossils);

        int chanceForNodeToSpawn = config.get(Configuration.CATEGORY_GENERAL, "Chance for a fossil node to generate", 5).getInt();
        if(chanceForNodeToSpawn > 0) {
            MinecraftForge.ORE_GEN_BUS.register(new EventManager(chanceForNodeToSpawn));
        }
        chestSkull = config.get(Configuration.CATEGORY_GENERAL, "Chance for a skull to be added in chests", chestSkull).getInt();

        if(event.getSourceFile().getName().endsWith(".jar")){
            proxy.MUD();
        }
	}

    @SubscribeEvent
    public void onLootLoad(LootTableLoadEvent loading){
        if(loading.getName() == LootTableList.CHESTS_SIMPLE_DUNGEON || loading.getName() == LootTableList.CHESTS_ABANDONED_MINESHAFT || loading.getName() == LootTableList.CHESTS_STRONGHOLD_CORRIDOR) {
            loading.getTable().addPool(new LootPool(new LootEntry[]{new LootEntryItem(Items.skull, 1, 0, new LootFunction[]{new SetMetadata(null, new RandomValueRange(0, 4))}, new LootCondition[0], "skull")}, new LootCondition[]{new RandomChance(1 / (float)chestSkull)}, new RandomValueRange(1, 1), new RandomValueRange(0, 0), "skulls"));
        }
    }
}

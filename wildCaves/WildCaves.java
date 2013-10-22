package wildCaves;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = WildCaves.modid, name = "Wild Caves 3", version = "0.4.3.4")
@NetworkMod(clientSideRequired = true)
public class WildCaves {
	public static final String modid = "wildcaves3";
	public static Block blockFlora, blockDecorations, blockFossils;
	public static BlockStoneStalactite blockStoneStalactite;
	public static BlockSandstoneStalactite blockSandStalactite;
	public static int floraLightLevel;
	public static int blockStoneStalactiteID, blockSandStalactiteID, blockFloraID;
	public static int timesPerChunck;
	public static int blockDecorationsID, blockFossilsID;
	public static int chanceForNodeToSpawn;
	private boolean solidStalactites, damageWhenFallenOn;
	public static Configuration config;
	private EventManager eventmanager;
	public static CreativeTabs tabWildCaves = new CreativeTabs("WildCaves3") {
		@Override
		public ItemStack getIconItemStack() {
			return new ItemStack(Item.eyeOfEnder, 1, 0);
		}
	};

	public void initBlocks() {
		if (blockStoneStalactiteID != 0) {
			blockStoneStalactite = new BlockStoneStalactite(blockStoneStalactiteID, solidStalactites, damageWhenFallenOn);
			Item.itemsList[blockStoneStalactiteID] = new ItemStoneStalactite(blockStoneStalactiteID - 256);
			LanguageRegistry.instance().addStringLocalization("stalactite1.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactite2.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactite3.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactite4.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactiteConnection1.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactiteConnection2.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactiteConnection3.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactiteConnection4.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalactiteEnd.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalacmiteEnd.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalacmite1.name", "Stalagmite");
			LanguageRegistry.instance().addStringLocalization("stalacmite2.name", "Stalagmite");
			LanguageRegistry.instance().addStringLocalization("stalacmite3.name", "Stalagmite");
		}
		if (blockSandStalactiteID != 0) {
			blockSandStalactite = new BlockSandstoneStalactite(blockSandStalactiteID, solidStalactites, damageWhenFallenOn);
			Item.itemsList[blockSandStalactiteID] = new ItemSandstoneStalactite(blockSandStalactiteID - 256);
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactite1.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactite2.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactite3.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactite4.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactiteConnection1.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactiteConnection2.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactiteConnection3.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactiteConnection4.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactiteEnd.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmiteEnd.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmite1.name", "Sandstone stalagmite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmite2.name", "Sandstone stalagmite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmite3.name", "Sandstone stalagmite");
		}
		if (blockDecorationsID != 0) {
			blockDecorations = new BlockDecorations(blockDecorationsID);
			Item.itemsList[blockDecorationsID] = new ItemDecoration(blockDecorationsID - 256);
			LanguageRegistry.instance().addStringLocalization("icicle1.name", "Icicle");
			LanguageRegistry.instance().addStringLocalization("icicle2.name", "Icicle");
			LanguageRegistry.instance().addStringLocalization("icicle3.name", "Icicle");
		}
		if (blockFloraID != 0) {
			blockFlora = new BlockFlora(blockFloraID, floraLightLevel);
			Item.itemsList[blockFloraID] = new ItemFlora(blockFloraID - 256);
			LanguageRegistry.instance().addStringLocalization("glowcap1.name", "Glowcap");
			LanguageRegistry.instance().addStringLocalization("glowcap2.name", "Glowcap");
			LanguageRegistry.instance().addStringLocalization("glowcap3.name", "Glowcap");
			LanguageRegistry.instance().addStringLocalization("gloweed1.name", "Glow weed");
			LanguageRegistry.instance().addStringLocalization("glowcap4top.name", "Glowcap");
			LanguageRegistry.instance().addStringLocalization("glowcap4bottom.name", "Glowcap");
			LanguageRegistry.instance().addStringLocalization("bluecap1.name", "Iceshroom");
			LanguageRegistry.instance().addStringLocalization("bluecap2.name", "Iceshroom");
			LanguageRegistry.instance().addStringLocalization("bluecap3.name", "Iceshroom");
			LanguageRegistry.instance().addStringLocalization("bluecap4.name", "Iceshroom");
		}
		if (blockFossilsID != 0) {
			blockFossils = new BlockFossils(blockFossilsID);
			Item.itemsList[blockFossilsID] = new MultiItemBlock(blockFossilsID - 256, "fossil1").setUnlocalizedName("fossilBlock");
			LanguageRegistry.instance().addStringLocalization("fossil1.name", "Bone pile");
		}
		LanguageRegistry.instance().addStringLocalization("itemGroup.WildCaves3", "en_US", "Wild Caves 3");
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		initBlocks();
		WorldGenWildCaves gen = new WorldGenWildCaves(config);
		GameRegistry.registerWorldGenerator(gen);
		//new itemstack(itemID, stackSize, damage)
		ChestGenHooks.getInfo("DUNGEON_CHEST").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 0), 1, 2, 50));//skeleton
		ChestGenHooks.getInfo("DUNGEON_CHEST").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 2), 1, 2, 50));//zombie
		ChestGenHooks.getInfo("DUNGEON_CHEST").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 3), 1, 2, 50));//steve
		ChestGenHooks.getInfo("DUNGEON_CHEST").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 4), 1, 2, 50));//creeper
		ChestGenHooks.getInfo("MINESHAFT_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 0), 1, 2, 50));//skeleton
		ChestGenHooks.getInfo("MINESHAFT_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 2), 1, 2, 50));//zombie
		ChestGenHooks.getInfo("MINESHAFT_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 3), 1, 2, 50));//steve
		ChestGenHooks.getInfo("MINESHAFT_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 4), 1, 2, 50));//creeper
		ChestGenHooks.getInfo("STRONGHOLD_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 0), 1, 2, 50));//skeleton
		ChestGenHooks.getInfo("STRONGHOLD_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 2), 1, 2, 50));//zombie
		ChestGenHooks.getInfo("STRONGHOLD_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 3), 1, 2, 50));//steve
		ChestGenHooks.getInfo("STRONGHOLD_CORRIDOR").addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 4), 1, 2, 50));//creeper
		if (blockFossilsID != 0) {
			eventmanager = new EventManager(chanceForNodeToSpawn);
			GameRegistry.registerWorldGenerator(eventmanager);
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		try {
			config.load();
			//--block IDs--------
			blockStoneStalactiteID = config.get(Configuration.CATEGORY_BLOCK, "Stalactite ID", 600).getInt(600);
			blockSandStalactiteID = config.get(Configuration.CATEGORY_BLOCK, "Sandstone Stalactite ID", 601).getInt(601);
			blockFloraID = config.get(Configuration.CATEGORY_BLOCK, "Flora ID", 602).getInt(602);
			blockDecorationsID = config.get(Configuration.CATEGORY_BLOCK, "Decorations ID", 603).getInt(603);
			blockFossilsID = config.get(Configuration.CATEGORY_BLOCK, "Fossils ID", 604).getInt(604);
			solidStalactites = config.get(Configuration.CATEGORY_GENERAL, "Solid stalactites/stalgmites", false).getBoolean(false);
			damageWhenFallenOn = config.get(Configuration.CATEGORY_GENERAL, "Stalgmites damage entities when fallen on", false).getBoolean(false);
			floraLightLevel = config.get(Configuration.CATEGORY_GENERAL, "Flora light level", 5).getInt(5);
			if (floraLightLevel > 15)
				floraLightLevel = 5;
			floraLightLevel = 15 - floraLightLevel; //light level 5 in-game is level 10 in the code for some reason  :V
			chanceForNodeToSpawn = config.get(Configuration.CATEGORY_GENERAL, "Chance for a fossil node to generate", 5).getInt(5);
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "WildCaves3 had a problem loading it's configuration");
		} finally {
			config.save();
		}
	}
}

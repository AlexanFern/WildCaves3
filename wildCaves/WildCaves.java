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
			for (int i = 1; i < 5; i++) {
				LanguageRegistry.instance().addStringLocalization("stalactite" + i + ".name", "Stalactite");
				LanguageRegistry.instance().addStringLocalization("stalactiteConnection" + i + ".name", "Stalactite");
			}
			LanguageRegistry.instance().addStringLocalization("stalactiteEnd.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalacmiteEnd.name", "Stalactite");
			LanguageRegistry.instance().addStringLocalization("stalacmite1.name", "Stalagmite");
			LanguageRegistry.instance().addStringLocalization("stalacmite2.name", "Stalagmite");
			LanguageRegistry.instance().addStringLocalization("stalacmite3.name", "Stalagmite");
		}
		if (blockSandStalactiteID != 0) {
			blockSandStalactite = new BlockSandstoneStalactite(blockSandStalactiteID, solidStalactites, damageWhenFallenOn);
			Item.itemsList[blockSandStalactiteID] = new ItemSandstoneStalactite(blockSandStalactiteID - 256);
			for (int i = 1; i < 5; i++) {
				LanguageRegistry.instance().addStringLocalization("sandstoneStalactite" + i + ".name", "Sandstone Stalactite");
				LanguageRegistry.instance().addStringLocalization("sandstoneStalactiteConnection" + i + ".name", "Sandstone Stalactite");
			}
			LanguageRegistry.instance().addStringLocalization("sandstoneStalactiteEnd.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmiteEnd.name", "Sandstone Stalactite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmite1.name", "Sandstone stalagmite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmite2.name", "Sandstone stalagmite");
			LanguageRegistry.instance().addStringLocalization("sandstoneStalacmite3.name", "Sandstone stalagmite");
		}
		if (blockDecorationsID != 0) {
			blockDecorations = new BlockDecorations(blockDecorationsID);
			Item.itemsList[blockDecorationsID] = new ItemDecoration(blockDecorationsID - 256);
			for (int i = 1; i < 4; i++) {
				LanguageRegistry.instance().addStringLocalization("icicle" + i + ".name", "Icicle");
			}
		}
		if (blockFloraID != 0) {
			blockFlora = new BlockFlora(blockFloraID, floraLightLevel);
			Item.itemsList[blockFloraID] = new ItemFlora(blockFloraID - 256);
			for (int i = 1; i < 4; i++) {
				LanguageRegistry.instance().addStringLocalization("glowcap" + i + ".name", "Glowcap");
			}
			LanguageRegistry.instance().addStringLocalization("gloweed1.name", "Glow weed");
			LanguageRegistry.instance().addStringLocalization("glowcap4top.name", "Glowcap");
			LanguageRegistry.instance().addStringLocalization("glowcap4bottom.name", "Glowcap");
			for (int i = 1; i < 5; i++) {
				LanguageRegistry.instance().addStringLocalization("bluecap" + i + ".name", "Iceshroom");
			}
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
		for (String txt : new String[] { "DUNGEON_CHEST", "MINESHAFT_CORRIDOR", "STRONGHOLD_CORRIDOR" }) {
			for (int i = 0; i < 5; i++) {
				if (i != 1) {
					ChestGenHooks.getInfo(txt).addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, 0), 1, 2, 50));//skeleton//zombie//steve//creeper
				}
			}
		}
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
			blockStoneStalactiteID = config.getBlock("Stalactite ID", 600).getInt(600);
			blockSandStalactiteID = config.getBlock("Sandstone Stalactite ID", 601).getInt(601);
			blockFloraID = config.getBlock("Flora ID", 602).getInt(602);
			blockDecorationsID = config.getBlock("Decorations ID", 603).getInt(603);
			blockFossilsID = config.getBlock("Fossils ID", 604).getInt(604);
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

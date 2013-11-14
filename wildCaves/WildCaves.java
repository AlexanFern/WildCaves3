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

@Mod(modid = WildCaves.modid, name = "Wild Caves 3", version = "0.4.3.5")
@NetworkMod(clientSideRequired = true)
public class WildCaves {
	public static final String modid = "wildcaves3";
	public static Block blockFlora, blockDecorations, blockFossils;
	public static BlockStoneStalactite blockStoneStalactite;
	public static BlockSandstoneStalactite blockSandStalactite;
	public static int floraLightLevel;
	private static int blockStoneStalactiteID, blockSandStalactiteID, blockFloraID;
	public static int timesPerChunck;
	private static int blockDecorationsID, blockFossilsID;
	public static int chanceForNodeToSpawn;
	public static boolean solidStalactites, damageWhenFallenOn;
	public static Configuration config;
	private EventManager eventmanager;
	public static CreativeTabs tabWildCaves = new CreativeTabs("WildCaves3") {
		@Override
		public ItemStack getIconItemStack() {
			return new ItemStack(Item.eyeOfEnder, 1, 0);
		}
	};

	public void initBlocks() {
		if (blockStoneStalactiteID > 0) {
			blockStoneStalactite = new BlockStoneStalactite(blockStoneStalactiteID);
			GameRegistry.registerBlock(blockStoneStalactite, ItemStoneStalactite.class, "StoneStalactite");
		}
		if (blockSandStalactiteID > 0) {
			blockSandStalactite = new BlockSandstoneStalactite(blockSandStalactiteID);
			GameRegistry.registerBlock(blockSandStalactite, ItemSandstoneStalactite.class, "SandstoneSalactite");
		}
		if (blockDecorationsID > 0) {
			blockDecorations = new BlockDecorations(blockDecorationsID);
			GameRegistry.registerBlock(blockDecorations, ItemDecoration.class, "Decorations");
		}
		if (blockFloraID > 0) {
			blockFlora = new BlockFlora(blockFloraID).setLightValue(floraLightLevel / 15);
			GameRegistry.registerBlock(blockFlora, ItemFlora.class, "Flora");
		}
		if (blockFossilsID > 0) {
			blockFossils = new BlockFossils(blockFossilsID);
			GameRegistry.registerBlock(blockFossils, ItemFossil.class, "FossilBlock");
		}
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		initBlocks();
		WorldGenWildCaves gen = new WorldGenWildCaves(config);
		if (gen.maxLength > 0) {
			GameRegistry.registerWorldGenerator(gen);
		}
		//new itemstack(itemID, stackSize, damage)
		for (String txt : new String[] { "DUNGEON_CHEST", "MINESHAFT_CORRIDOR", "STRONGHOLD_CORRIDOR" }) {
			for (int i = 0; i < 5; i++) {
				if (i != 1) {
					ChestGenHooks.getInfo(txt).addItem(new WeightedRandomChestContent(new ItemStack(Item.skull.itemID, 1, i), 1, 2, 50));//skeleton//zombie//steve//creeper
				}
			}
		}
		if (blockFossilsID > 0) {
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
				floraLightLevel = 15;
			chanceForNodeToSpawn = config.get(Configuration.CATEGORY_GENERAL, "Chance for a fossil node to generate", 5).getInt(5);
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "WildCaves3 had a problem loading it's configuration");
		} finally {
			config.save();
		}
	}
}

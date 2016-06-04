package wildCaves;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;

public class ClientProxy extends ServerProxy{
    private static final String PREFIX = "wildcaves3:";

    @Override
    public void registerRenders() {
        Item item = Item.getItemFromBlock(WildCaves.blockStoneStalactite);
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        for(int i = 0; i < WildCaves.stalacs.size(); i++) {
            ModelLoader.registerItemVariants(item, new ResourceLocation(PREFIX + "stone_" + i));
            mesher.register(item, i, defaultModel("stone_" + i));
        }
        item = Item.getItemFromBlock(WildCaves.blockSandStalactite);
        for(int i = 0; i < WildCaves.sandStalacs.size(); i++) {
            ModelLoader.registerItemVariants(item, new ResourceLocation(PREFIX + "sandstone_" + i));
            mesher.register(item, i, defaultModel("sandstone_" + i));
        }
        item = Item.getItemFromBlock(WildCaves.blockDecorations);
        for(int i = 0; i < WildCaves.icicles.size(); i++) {
            ModelLoader.registerItemVariants(item, new ResourceLocation(PREFIX + "icicle_" + i));
            mesher.register(item, i, defaultModel("icicle_" + i));
        }
        item = Item.getItemFromBlock(WildCaves.blockFlora);
        for(int i = 0; i < WildCaves.caps.size(); i++) {
            ModelLoader.registerItemVariants(item, new ResourceLocation(PREFIX + "flora_" + i));
            mesher.register(item, i, defaultModel("flora_" + i));
        }
        item = Item.getItemFromBlock(WildCaves.blockFossils);
        for(int i = 0; i < WildCaves.fossils.size(); i++) {
            ModelLoader.registerItemVariants(item, new ResourceLocation(PREFIX + "fossil_" + i));
            mesher.register(item, i, defaultModel("fossil_" + i));
        }
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
                if(iBlockState != null){
                    if(WildCaves.blockFlora.getMetaFromState(iBlockState) < 6)
                        return iBlockAccess != null ? BiomeColorHelper.getFoliageColorAtPos(iBlockAccess, blockPos) : ColorizerFoliage.getFoliageColorBasic();
                }
                return -1;
            }
        }, WildCaves.blockFlora);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack itemStack, int i) {
                if(itemStack.getMetadata() < 6)
                    return ColorizerFoliage.getFoliageColorBasic();
                return -1;
            }
        }, WildCaves.blockFlora);
    }

    private ModelResourceLocation defaultModel(String type){
        return new ModelResourceLocation(PREFIX + type, "inventory");
    }

    @Override
    public void MUD(){
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

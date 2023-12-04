package moonfather.goldfish;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModGoldfish.MOD_ID)
public class ModGoldfish
{
    public static final String MOD_ID = "goldfish";

    public ModGoldfish()
    {
        // options
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(EventHandlersForStartup.class);
    }

    public static class Items
    {
        public static Item GoldfishRaw = null;
        public static Item GoldFishCooked = null;
    }
}

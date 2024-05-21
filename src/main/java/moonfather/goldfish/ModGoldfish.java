package moonfather.goldfish;

import moonfather.goldfish.items.Repository;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ModGoldfish.MOD_ID)
public class ModGoldfish
{
    public static final String MOD_ID = "goldfish";

    public ModGoldfish(IEventBus modBus)
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        Repository.Init(modBus);
    }
}

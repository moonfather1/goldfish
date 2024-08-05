package moonfather.goldfish;

import moonfather.goldfish.items.Repository;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ModGoldfish.MOD_ID)
public class ModGoldfish
{
    public static final String MOD_ID = "goldfish";

    public ModGoldfish(IEventBus modBus, ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        Repository.Init(modBus);
    }
}//net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction

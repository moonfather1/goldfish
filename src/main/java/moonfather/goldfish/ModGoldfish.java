package moonfather.goldfish;

import moonfather.goldfish.items.Repository;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ModGoldfish.MOD_ID)
public class ModGoldfish
{
    public static final String MOD_ID = "goldfish";

    public ModGoldfish()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        Repository.Init();
    }
}

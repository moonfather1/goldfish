package moonfather.goldfish;

import moonfather.goldfish.items.Repository;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(ModGoldfish.MODID)
public class ModGoldfish
{
    public static final String MODID = "goldfish";
    public static final Logger LOGGER = LogUtils.getLogger();



    public ModGoldfish(IEventBus modEventBus, ModContainer modContainer)
    {
        Repository.Init(modEventBus);
        modEventBus.addListener(Repository::OnTabContents);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}

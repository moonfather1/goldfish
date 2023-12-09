package moonfather.goldfish;

import moonfather.goldfish.items.Repository;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Goldfish implements ModInitializer
{
	public static final String MOD_ID = "goldfish";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static CommonConfig config = null;

	public static CommonConfig getConfig()
	{
		if (config == null)
		{
			config = CommonConfig.create();
		}
		return config;
	}

	@Override
	public void onInitialize()
	{
		LOGGER.info("Hello Fabric world!");
		getConfig(); // don't want it lazy. would have worked though.
		Repository.init();
	}
}
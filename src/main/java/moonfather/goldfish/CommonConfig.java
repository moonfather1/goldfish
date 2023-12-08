package moonfather.goldfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CommonConfig //todo: ReflectiveConfig in q
{
    public double FishChance;
    public int MaxLuckLevel;
    public int LuckEffectDuration;
	public boolean DropExtraGemsFromOreBlocks;
	public boolean DropExtraLootFromMobs;



    public static CommonConfig create()
    {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("goldfish.json");
        ConfigInner loaded = null;
        if (configPath.toFile().exists())
        {
            try
            {
                Gson gson = new Gson();
                loaded = gson.fromJson(Files.readString(configPath), ConfigInner.class);
            }
            catch (IOException ignored)
            {
            }
        }
        if (loaded == null)
        {
            loaded = new ConfigInner();
        }
        CommonConfig result = new CommonConfig();
        result.FishChance = loaded.fish_chance;
        result.MaxLuckLevel = loaded.max_luck_level;  if (result.MaxLuckLevel > 10 || result.MaxLuckLevel < 1) { result.MaxLuckLevel = 3; }
        result.LuckEffectDuration = loaded.luck_effect_duration * 20;  if (result.LuckEffectDuration <= 0) { result.LuckEffectDuration = 12020; }
		result.DropExtraGemsFromOreBlocks = loaded.drop_extra_gems_from_ore_blocks;
		result.DropExtraLootFromMobs = loaded.drop_extra_loot_from_mobs;
        if (! configPath.toFile().exists())
        {
            try
            {
                Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                String text = gson.toJson(loaded, ConfigInner.class);
                Files.writeString(configPath, text, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
            catch (IOException ignored)
            {
            }
        }
        return result;
    }

    private CommonConfig() { }

    ////////////////////////////////////////////////////////////////

    private static class ConfigInner
    {
        public String fish_chance_comment = "(any number) This is a multiplier for the default chance to pull a goldfish when reeling the fish in. Default value is around 4% which is 15x less common than cod, 6x less common than salmon and 2x more common than clownfish. This value multiplies that base chance. Value of 1.0 leaves it as described above, 0.5 halves the chance, 2.0 doubles it, 25 will cause you to pull pretty much nothing else. Zero means 0% chance. High values will noticeably increase your luck and anything it affects (see options below).";
        public double fish_chance = 1.0;
        public String max_luck_level_comment = "(integer, 1..10)  Max level of luck that can be active on the player. Affects fishing a little, gems and mob drops a lot. Default is 3. Values higher than 3 work fine, but the roman numeral may not be displayed (above the countdown timer).";
        public int max_luck_level = 3;
        public String luck_effect_duration_comment = "(integer in seconds)  Duration of the luck effect. By default, around one half of the minecraft day (day meaning day/night cycle; duration of the full day is 1200 seconds or 24000 ticks). If the player is increasing existing level of the effect, the new duration will be somewhere between this value and the remaining time.";
        public int luck_effect_duration = 12020/20;
        public String drop_extra_gems_from_ore_blocks_comment = "(true/false)  Does luck affect block drops? If yes, every level of good luck increases gem yield (only works on diamonds, emeralds and similar stuff added by mods). Levels in bad luck, well, try it yourself if you are curious. The effect works together nicely with the fortune enchantment.";
		public boolean drop_extra_gems_from_ore_blocks = true;
        public String drop_extra_loot_from_mobs_comment = "(true/false)  Does luck affect mob drops?  If yes, then effect levels 1 to 4 give a level*25% chance to get a +1 to looting level when killing a mob. Then, effect levels 5 to 8 give a (level-4)*25% chance to get another +1 to looting level when killing a mob (guaranteed for levels 8+). For example, if you have a sword with Looting III and Luck VI active, there is 50% chance for you to effectively kill mobs with Looting IV and 50% chance for you to effectively kill mobs with Looting V.";
		public boolean drop_extra_loot_from_mobs = true;
    }
}

package moonfather.goldfish;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OptionsHolder
{
	public static class Common
	{
		private static final Double defaultFishChance = 1.0;
		private static final int defaultMaxLuckLevel = 3;
		private static final int defaultLuckEffectDuration = 12020;
		private static final boolean defaultDropExtraGemsFromOreBlocks = true;
		private static final boolean defaultDropExtraLootFromMobs = true;

		public final ModConfigSpec.ConfigValue<Double> FishChance;
		public final ModConfigSpec.ConfigValue<Integer> MaxLuckLevel;
		public final ModConfigSpec.ConfigValue<Integer> LuckEffectDuration;
		public final ModConfigSpec.ConfigValue<Boolean> DropExtraGemsFromOreBlocks;
		public final ModConfigSpec.ConfigValue<Boolean> DropExtraLootFromMobs;


		public Common(ModConfigSpec.Builder builder)
		{
			builder.push("main");
			this.FishChance = builder.comment("This is a multiplier for the default chance to pull a goldfish when reeling the fish in. Default value is around 4% which is 15x less common than cod, 6x less common than salmon and 2x more common than clownfish. This value multiplies that base chance. Value of 1.0 leaves it as described above, 0.5 halves the chance, 2.0 doubles it, 25 will cause you to pull pretty much nothing else. Zero means 0% chance. High values will noticeably increase your luck and anything it affects (see options below).")
					.defineInRange("Goldfish chance modifier when fishing", defaultFishChance, 0, 50.0);
			this.MaxLuckLevel = builder.comment("Max level of luck that can be active on the player. Affects fishing a little, gems and mob drops a lot. Default is 3. Values higher than 3 work fine, but the roman numeral may not be displayed (above the countdown timer).")
					.defineInRange("Max level of luck effect", defaultMaxLuckLevel, 1, 10);
			this.LuckEffectDuration = builder.comment("Duration of the luck effect. By default, around one half of the minecraft day (day meaning day/night cycle; duration of the full day is 24000 ticks). Value is in ticks. If the player is increasing existing level of the effect, the new duration will be somewhere between this value and the remaining time.")
					.defineInRange("Luck effect duration", defaultLuckEffectDuration, 100, 50000);

			this.DropExtraGemsFromOreBlocks = builder.comment("Does luck affect block drops? If yes, every level of good luck increases gem yield (only works on diamonds, emeralds and similar stuff added by mods). Levels in bad luck, well, try it yourself if you are curious. The effect works together nicely with the fortune enchantment.")
					.define("Drop extra gems from ore blocks", defaultDropExtraGemsFromOreBlocks);
			this.DropExtraLootFromMobs = builder.comment("Does luck affect mob drops?  If yes, then effect levels 1 to 4 give a level*25% chance to get a +1 to looting level when killing a mob. Then, effect levels 5 to 8 give a (level-4)*25% chance to get another +1 to looting level when killing a mob (guaranteed for levels 8+). For example, if you have a sword with Looting III and Luck VI active, there is 50% chance for you to effectively kill mobs with Looting IV and 50% chance for you to effectively kill mobs with Looting V.")
					.define("Drop extra loot from mobs", defaultDropExtraLootFromMobs);
			builder.pop();
		}
	}

	public static final Common COMMON;
	public static final ModConfigSpec COMMON_SPEC;

	static //constructor
	{
		Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}

package moonfather.goldfish;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue FishChance = BUILDER
            .comment("This is a multiplier for the default chance to pull a goldfish when reeling the fish in. Default value is around 4% which is 15x less common than cod, 6x less common than salmon and 2x more common than clownfish. This value multiplies that base chance. Value of 1.0 leaves it as described above, 0.5 halves the chance, 2.0 doubles it, 25 will cause you to pull pretty much nothing else. Zero means 0% chance. High values will noticeably increase your luck and anything it affects (see options below).")
            .defineInRange("Goldfish chance modifier when fishing", 1.0, 0, 50);

    public static final ModConfigSpec.IntValue MaxLuckLevel = BUILDER
            .comment("Max level of luck that can be active on the player. Affects fishing a little, gems and mob drops a lot. Default is 3. Values higher than 3 work fine, but the roman numeral may not be displayed (above the countdown timer).")
            .defineInRange("Max level of luck effect", 3, 1, 10);

    public static final ModConfigSpec.IntValue LuckEffectDuration = BUILDER
            .comment("Duration of the luck effect. By default, around one half of the minecraft day (day meaning day/night cycle; duration of the full day is 24000 ticks). Value is in ticks. If the player is increasing existing level of the effect, the new duration will be somewhere between this value and the remaining time.")
            .defineInRange("Luck effect duration", 12020, 100, 50000);



    public static final ModConfigSpec.BooleanValue DropExtraGemsFromOreBlocks = BUILDER
            .comment("Does luck affect block drops? If yes, every level of good luck increases gem yield (only works on diamonds, emeralds and similar stuff added by mods). Levels in bad luck, well, try it yourself if you are curious. The effect works together nicely with the fortune enchantment.")
            .define("Drop extra gems from ore blocks", true);
    public static final ModConfigSpec.BooleanValue DropExtraLootFromMobs = BUILDER
            .comment("Does luck affect mob drops?  If yes, then positive luck increases chances of multiple different items dropped (so if a zombie would normally drop just flesh, luck may cause other things to drop (carrot, iron) but not another flesh piece).")
            .define("Drop extra loot from mobs", true);



    static final ModConfigSpec SPEC = BUILDER.build();
}

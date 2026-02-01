package moonfather.goldfish;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class BlockFortuneLevelCalculator
{
    public static List<ItemStack> goThroughDrops(List<ItemStack> original, float luck, RandomSource random)
    {
        int percentagePerLevel = Goldfish.getConfig().BlockFortunePercentagePerLevel;
        int count = original.size();
        for (int i = 0; i < count; i++)
        {
            ItemStack drop = original.get(i);
            if (drop == null || drop.isEmpty())
            {
                continue;
            }
            if (drop.is(gemTag))
            {
                double chance = Math.abs(luck) * Math.pow(1.1D,  Math.abs(luck)) * percentagePerLevel;
                if (luck > 0)
                {
                    while (chance >= 100)
                    {
                        chance = chance - 100;
                        original.add(drop.copy()); // loop limit is fixed
                    }
                    if (chance >= random.nextInt(100))
                    {
                        original.add(drop.copy());
                    }
                }
                else
                {
                    if (chance >= random.nextInt(100))
                    {
                        original.set(i, new ItemStack(Items.COAL, drop.getCount()));
                    }
                }
            }
        }
        return  original;
    }
    private static final TagKey<Item> gemTag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c","gems"));



    public static boolean disabled()
    {
        return Goldfish.getConfig().DropExtraGemsFromOreBlocks == false;
    }
}

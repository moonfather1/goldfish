package moonfather.goldfish;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.*;

public class BlockFortuneLevelCalculator
{
    public static List<ItemStack> goThroughDrops(List<ItemStack> original, float luck, Random random)
    {
        TagKey<Item> gemTag = TagKey.of(RegistryKeys.ITEM, new Identifier("c","gems"));
        int percentagePerLevel = Goldfish.getConfig().BlockFortunePercentagePerLevel;
        int count = original.size();
        for (int i = 0; i < count; i++)
        {
            ItemStack drop = original.get(i);
            if (drop == null || drop.isEmpty())
            {
                continue;
            }
            if (drop.isIn(gemTag))
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

    public static boolean disabled()
    {
        return Goldfish.getConfig().DropExtraGemsFromOreBlocks == false;
    }
}

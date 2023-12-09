package moonfather.goldfish;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class LootingLevelCalculator
{
    public static int getLevel(Entity looter, int enchantmentLevel)
    {
        if (Goldfish.getConfig().DropExtraLootFromMobs == false)
        {
            return 0;
        }
        if (! (looter instanceof PlayerEntity player))
        {
            return 0;
        }
        float luck = player.getLuck();
        if (luck == 0 || (luck < 0 && enchantmentLevel < 1))
        {
            return 0; // no luck or negative but nothing to reduce
        }
        if (luck < -4 && enchantmentLevel < 2)
        {
            return -1 * enchantmentLevel;  // should reduce by 1 so this handles levels 0 and 1.
        }

        int bonus = luck > 0 ? 1 : -1;
        luck = Math.abs(luck);
        int result = 0;

        if (player.getWorld().random.nextInt(4) < luck)
        {
            result += bonus;  // 25%*level chance for +1
        }
        luck = luck - 4;  //5to8 moved to 1to4
        if (player.getWorld().random.nextInt(4) < luck)
        {
            result += bonus; // 25%*(level-4) chance for another +1
        }
        return result;
    }
}

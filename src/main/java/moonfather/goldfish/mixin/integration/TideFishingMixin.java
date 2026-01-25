package moonfather.goldfish.mixin.integration;

import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import moonfather.goldfish.Goldfish;
import moonfather.goldfish.items.Repository;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Pseudo
@Mixin(TideFishingHook.class)
public class TideFishingMixin
{
    @ModifyArg(method = "Lcom/li64/tide/registries/entities/misc/fishing/TideFishingHook;retrieve(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/player/Player;)I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;<init> (Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"),
            index = -1,
            remap = false)
    private ItemStack swapFish(Level level, double x, double y, double z, ItemStack loot)
    {
        // just like in vanilla, there is a call:  ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
        // we will change the last arg here (-1 means auto):
        int baseChance = 0;
        Holder<Biome> biome = level.getBiome(BlockPos.containing(x, y, z));
        if (biome.is(Biomes.CHERRY_GROVE))
        {
            baseChance = 8;
        }
        else if (biome.is(Biomes.MUSHROOM_FIELDS))
        {
            baseChance = 6;
        }
        else if (biome.is(BiomeTags.IS_RIVER) || biome.is(BiomeTags.IS_FOREST) || biome.is(BiomeTags.IS_SAVANNA) || biome.is(BiomeTags.IS_HILL) || biome.is(Biomes.PLAINS))
        {
            baseChance = 3;
        }
        int percentageChance = (int) Math.round(baseChance * Goldfish.getConfig().FishChance); // 4% is default
        if (ourRandom.nextInt(100) < percentageChance)
        {
            if (loot.is(ItemTags.FISHES))
            {
                return Repository.ItemFishRaw.getDefaultInstance();
            }
        }
        return loot;
    }

    @Unique
    private static final Random ourRandom = new Random();
}

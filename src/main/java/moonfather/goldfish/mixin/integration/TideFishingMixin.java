package moonfather.goldfish.mixin.integration;

import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import moonfather.goldfish.Goldfish;
import moonfather.goldfish.items.Repository;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
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
    @ModifyArg(method = "Lcom/li64/tide/registries/entities/misc/fishing/TideFishingHook;retrieve(Lnet/minecraft/item/ItemStack;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/player/PlayerEntity;)I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;<init> (Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V"),
            index = -1,
            remap = false)
    private ItemStack swapFish(World level, double x, double y, double z, ItemStack loot)
    {
        // just like in vanilla, there is a call:  ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
        // we will change the last arg here (-1 means auto):
        int baseChance = 0;
        RegistryEntry<Biome> biome = level.getBiome(BlockPos.ofFloored(x, y, z));
        if (biome.matchesKey(BiomeKeys.CHERRY_GROVE))
        {
            baseChance = 8;
        }
        else if (biome.matchesKey(BiomeKeys.MUSHROOM_FIELDS))
        {
            baseChance = 6;
        }
        else if (biome.isIn(BiomeTags.IS_RIVER) || biome.isIn(BiomeTags.IS_FOREST) || biome.isIn(BiomeTags.IS_SAVANNA) || biome.isIn(BiomeTags.IS_HILL) || biome.matchesKey(BiomeKeys.PLAINS))
        {
            baseChance = 3;
        }
        int percentageChance = (int) Math.round(baseChance * Goldfish.getConfig().FishChance); // 4% is default
        if (ourRandom.nextInt(100) < percentageChance)
        {
            if (loot.isIn(ItemTags.FISHES))
            {
                return Repository.ItemFishRaw.getDefaultStack();
            }
        }
        return loot;
    }

    @Unique
    private static final Random ourRandom = new Random();
}

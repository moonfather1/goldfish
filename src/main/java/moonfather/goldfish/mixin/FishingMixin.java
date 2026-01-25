package moonfather.goldfish.mixin;

import moonfather.goldfish.Goldfish;
import moonfather.goldfish.items.Repository;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(FishingHook.class)
public class FishingMixin
{
	@ModifyArg(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;<init> (Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"), index = -1)
	private ItemStack swapFish(ItemStack loot)
	{
		// so in bobber's use method, there is a call:  ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
		// we will change the last arg here (-1 means auto):

		int percentageChance = (int) Math.round(4 * Goldfish.getConfig().FishChance); // 4% is default
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
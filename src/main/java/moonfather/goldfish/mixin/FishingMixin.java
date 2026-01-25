package moonfather.goldfish.mixin;

import moonfather.goldfish.Goldfish;
import moonfather.goldfish.items.Repository;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(FishingBobberEntity.class)
public class FishingMixin
{
	@ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;<init> (Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V"), index = -1)
	private ItemStack swapFish(ItemStack loot)
	{
		// so in bobber's use method, there is a call:  ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
		// we will change the last arg here (-1 means auto):

		int percentageChance = (int) Math.round(4 * Goldfish.getConfig().FishChance); // 4% is default
		if (ourRandom.nextInt(100) < percentageChance)
		{
			if (loot.isFood())
			{
				return Repository.ItemFishRaw.getDefaultStack();
			}
		}
		return loot;
	}
	private static final Random ourRandom = new Random();
}
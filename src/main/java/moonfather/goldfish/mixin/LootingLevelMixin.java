package moonfather.goldfish.mixin;

import moonfather.goldfish.LootingLevelCalculator;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class LootingLevelMixin
{
    @Inject(method = "getEnchantmentLevel", at = @At(value = "RETURN"), cancellable = true)
    private static void addOurLootingValue(Holder<Enchantment> enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir)
    {
        if (enchantment.is(Enchantments.LOOTING))
        {
            int original = cir.getReturnValue();
            cir.setReturnValue(original + LootingLevelCalculator.getLevel(entity, original));
        }
    }
}

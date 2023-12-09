package moonfather.goldfish.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moonfather.goldfish.LootingLevelCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootingEnchantLootFunction.class)
public class LootingLevelMixin3
{
    @ModifyExpressionValue(method = "process", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLooting (Lnet/minecraft/entity/LivingEntity;)I") )
    private int addOurLootingValue(int original, ItemStack stack, LootContext context)
    {
        Entity looter = context.get(LootContextParameters.KILLER_ENTITY);
        return original + LootingLevelCalculator.getLevel(looter, original);
    }
}

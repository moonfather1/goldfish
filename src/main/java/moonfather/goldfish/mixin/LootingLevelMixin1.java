package moonfather.goldfish.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moonfather.goldfish.LootingLevelCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RandomChanceWithLootingLootCondition.class)
public class LootingLevelMixin1
{
    @ModifyExpressionValue(method = "test", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLooting (Lnet/minecraft/entity/LivingEntity;)I") )
    private int addOurLootingValue(int original, LootContext lootContext)
    {
        Entity entity = lootContext.get(LootContextParameters.KILLER_ENTITY);
        return original + LootingLevelCalculator.getLevel(entity, original);
    }
}

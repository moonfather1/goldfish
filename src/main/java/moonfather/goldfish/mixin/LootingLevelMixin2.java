package moonfather.goldfish.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moonfather.goldfish.LootingLevelCalculator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LootingLevelMixin2
{
    @ModifyExpressionValue(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLooting (Lnet/minecraft/entity/LivingEntity;)I") )
    private int addOurLootingValue(int original, DamageSource source)
    {
        return original + LootingLevelCalculator.getLevel(source.getAttacker(), original);  // the check whether it's a player was done in LivingEntity.drop
    }
}

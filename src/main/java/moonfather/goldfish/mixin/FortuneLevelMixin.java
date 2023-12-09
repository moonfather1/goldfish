package moonfather.goldfish.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moonfather.goldfish.BlockFortuneLevelCalculator;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(AbstractBlock.class)
public class FortuneLevelMixin
{
    @ModifyReturnValue(method = "getDroppedStacks", at = @At("RETURN"))
    private List<ItemStack> addExtraGems(List<ItemStack> original, BlockState state, LootContextParameterSet.Builder builder)
    {
        if (BlockFortuneLevelCalculator.disabled())
        {
            return original;
        }
        if (builder.get(LootContextParameters.THIS_ENTITY) instanceof PlayerEntity player && player.getLuck() != 0)
        {
            return BlockFortuneLevelCalculator.goThroughDrops(original, player.getLuck(), player.getWorld().random);
        }
        return original;
    }
}

package moonfather.goldfish.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moonfather.goldfish.BlockFortuneLevelCalculator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(BlockBehaviour.class)
public class FortuneLevelMixin
{
    @ModifyReturnValue(method = "getDrops", at = @At("RETURN"))
    private List<ItemStack> addExtraGems(List<ItemStack> original, BlockState state, LootParams.Builder builder)
    {
        if (BlockFortuneLevelCalculator.disabled())
        {
            return original;
        }
        if (builder.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player player && player.getLuck() != 0)
        {
            return BlockFortuneLevelCalculator.goThroughDrops(original, player.getLuck(), player.level().random);
        }
        Block b;
        return original;
    }
}

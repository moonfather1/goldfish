package moonfather.goldfish.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moonfather.goldfish.items.entities.GoldfishItemEntity;
import moonfather.goldfish.items.Repository;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class TossingMixin
{
    @ModifyExpressionValue(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "NEW", target = "Lnet/minecraft/entity/ItemEntity;"))
    private ItemEntity substituteOurEntity(ItemEntity original/*, World world, double x, double y, double z, ItemStack stack*/)
    {
        if (original.getStack().isOf(Repository.ItemFishRaw))
        {
            return new GoldfishItemEntity(original.getWorld(), original.getX(), original.getY(), original.getZ(), original.getStack());
            // ok so this was tricky.
            // forge has a toss event and more importantly allows me to write entity item update logic inside item. thus, forge mod requires 0 mixins.
            // here we intercept tossing and substitute our derived class. i expect problems with mods that make items lie flat on the ground. but pseudo mixins can take care of those, it's just a waste of time.
        }
        return original;
    }
}

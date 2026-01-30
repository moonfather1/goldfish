package moonfather.goldfish.mixin;

import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public interface EntityAccessor
{
    // protected @Nullable EntityReference<Player> lastHurtByPlayer;
    @Accessor("lastHurtByPlayer")
    @Nullable
    EntityReference<Player> fish$getLastHurtByPlayer();
}

package moonfather.goldfish.items.entities;

import moonfather.goldfish.FishTossHelper;
import moonfather.goldfish.utility.PathFindingHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GoldfishItemEntity extends ItemEntity
{
    public GoldfishItemEntity(Level world, double x, double y, double z, ItemStack stack)
    {
        super(world, x, y, z, stack);
    }



    @Override
    public void tick()
    {
        super.tick();
        if (! this.level().isClientSide())
		{
			if (this.isAlive() && this.getAge() % 40 == 0)
			{
				if (Math.abs(this.getDeltaMovement().x) < 2e-2 && Math.abs(this.getDeltaMovement().y) < 15e-2 && Math.abs(this.getDeltaMovement().z) < 2e-2)
				{
					if (this.isInWaterOrBubble() && PathFindingHelper.IsPartOfASeriousBodyOfWater(this.level(), this.blockPosition()))
					{
						FishTossHelper.changeLuck(this, true);
						FishTossHelper.showStupidParticles(this, ParticleTypes.NOTE);
					}
					else
					{
						FishTossHelper.changeLuck(this, false);
						FishTossHelper.showStupidParticles(this, ParticleTypes.ANGRY_VILLAGER);
					}
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}
    }
}

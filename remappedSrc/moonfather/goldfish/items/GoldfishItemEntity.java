package moonfather.goldfish.items;

import moonfather.goldfish.FishTossHandler;
import moonfather.goldfish.utility.PathFindingHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class GoldfishItemEntity extends ItemEntity
{
    public GoldfishItemEntity(World world, double x, double y, double z, ItemStack stack)
    {
        super(world, x, y, z, stack);
    }



    @Override
    public void tick()
    {
        super.tick();
        if (! this.method_48926().isClient())
		{
			if (this.isAlive() && this.age % 40 == 0)
			{
				if (Math.abs(this.movementMultiplier.x) < 2e-2 && Math.abs(this.movementMultiplier.y) < 15e-2 && Math.abs(this.movementMultiplier.z) < 2e-2)
				{
					if (this.isSubmergedInWater() && PathFindingHelper.IsPartOfASeriousBodyOfWater(this.method_48926(), this.getBlockPos()))
					{
						FishTossHandler.changeLuck(this, true);
						FishTossHandler.showStupidParticles(this, ParticleTypes.NOTE);
					}
					else
					{
						FishTossHandler.changeLuck(this, false);
						FishTossHandler.showStupidParticles(this, ParticleTypes.ANGRY_VILLAGER);
					}
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}
    }
}

package moonfather.goldfish;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FishTossHandler
{
	public static void changeLuck(ItemEntity entityItem, boolean addLuck)
	{
		StatusEffect potionToAdd, potionToRemove;
		if (addLuck)
		{
			potionToAdd = StatusEffects.LUCK;
			potionToRemove = StatusEffects.UNLUCK;
		}
		else
		{
			potionToAdd = StatusEffects.UNLUCK;
			potionToRemove = StatusEffects.LUCK;
		}
		//player
		PlayerEntity player = FishTossHandler.GetTossingPlayer(entityItem); if (player == null) return;
		StatusEffectInstance effect = player.getStatusEffect(potionToAdd);
		player.removeStatusEffect(potionToRemove);
		if (effect == null)
		{
			int level = Math.min(entityItem.getStack().getCount() - 1, Goldfish.getConfig().MaxLuckLevel - 1); // zero based
			effect = new StatusEffectInstance(potionToAdd, Goldfish.getConfig().LuckEffectDuration, level, true, false);
			player.addStatusEffect(effect);
		}
		else
		{
			int level = effect.getAmplifier();
			int time = effect.getDuration();
			level = Math.min(level + entityItem.getStack().getCount(), Goldfish.getConfig().MaxLuckLevel - 1); // zero based
			time = (time + time + Goldfish.getConfig().LuckEffectDuration) / 3;
			effect = new StatusEffectInstance(potionToAdd, time, level, true, false);
			player.addStatusEffect(effect);
		}
	}



	public static void showStupidParticles(ItemEntity entityItem, ParticleEffect particleType)
	{
		if (entityItem.getWorld() instanceof ServerWorld)
		{
			double yPos = entityItem.getY();
			if (entityItem.isSubmergedInWater())
			{
				BlockPos.Mutable pos = new BlockPos.Mutable();
				pos.set(entityItem.getBlockPos());
				while (entityItem.getWorld().getFluidState(pos).isIn(FluidTags.WATER))
				{
					pos.set(pos.getX(), pos.getY() + 1, pos.getZ());
				}
				yPos = pos.getY() - 0.3d;
			}
			ServerWorld world = (ServerWorld) entityItem.getWorld();
			for (int i = 1; i <= 16; i++)
			{
				double xSpeed = (world.random.nextFloat() - 0.5D) * 0.30D;
				double ySpeed = (world.random.nextFloat() + 0.1D) * 0.40D;
				double zSpeed = (world.random.nextFloat() - 0.5D) * 0.30D;
				world.spawnParticles(particleType, entityItem.getX() + xSpeed * 8, yPos + ySpeed * 4, entityItem.getZ() + zSpeed * 8, 1 /*numberOfParticles*/, xSpeed, 0.3 * ySpeed, zSpeed, 0.10d /*particleSpeed*/ /*, int... particleArguments*/);
			}
		}
		else
		{
			//System.out.println("ItemFish.changeLuck() - this message should not have been shown; please report to MoonFather.");
		}
	}

	private static PlayerEntity GetTossingPlayer(ItemEntity entityItem)
	{
		if (entityItem.getOwner() instanceof PlayerEntity p)
		{
			return p; // actually used to be more complicated than this
		}
		return null;
	}
}

package moonfather.goldfish;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class FishTossHandler
{
	public static void changeLuck(ItemEntity entityItem, boolean addLuck)
	{
		MobEffect potionToAdd, potionToRemove;
		if (addLuck)
		{
			potionToAdd = MobEffects.LUCK;
			potionToRemove = MobEffects.UNLUCK;
		}
		else
		{
			potionToAdd = MobEffects.UNLUCK;
			potionToRemove = MobEffects.LUCK;
		}
		//player
		Player player = FishTossHandler.GetTossingPlayer(entityItem); if (player == null) return;
		MobEffectInstance effect = player.getEffect(potionToAdd);
		player.removeEffect(potionToRemove);
		if (effect == null)
		{
			int level = Math.min(entityItem.getItem().getCount() - 1, OptionsHolder.COMMON.MaxLuckLevel.get() - 1); // zero based
			effect = new MobEffectInstance(potionToAdd, OptionsHolder.COMMON.LuckEffectDuration.get(), level, true, false);
			player.addEffect(effect);
		}
		else
		{
			int level = effect.getAmplifier();
			int time = effect.getDuration();
			level = Math.min(level + entityItem.getItem().getCount(), OptionsHolder.COMMON.MaxLuckLevel.get() - 1); // zero based
			time = (time + time + OptionsHolder.COMMON.LuckEffectDuration.get()) / 3;
			effect = new MobEffectInstance(potionToAdd, time, level, true, false);
			player.addEffect(effect);
		}
	}



	public static void showStupidParticles(ItemEntity entityItem, ParticleOptions particleType)
	{
		if (entityItem.level() instanceof ServerLevel)
		{
			double yPos = entityItem.getY();
			if (entityItem.isInWater())
			{
				BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
				pos.set(entityItem.blockPosition());
				while (entityItem.level().getFluidState(pos).is(FluidTags.WATER))
				{
					pos.set(pos.getX(), pos.getY() + 1, pos.getZ());
				}
				yPos = pos.getY() - 0.3d;
			}
			ServerLevel world = (ServerLevel)entityItem.level();
			for (int i = 1; i <= 16; i++)
			{
				double xSpeed = (entityItem.level().random.nextFloat() - 0.5D) * 0.30D;
				double ySpeed = (entityItem.level().random.nextFloat() + 0.1D) * 0.40D;
				double zSpeed = (entityItem.level().random.nextFloat() - 0.5D) * 0.30D;
				world.sendParticles( particleType, entityItem.getX() + xSpeed * 8, yPos + ySpeed * 4, entityItem.getZ() + zSpeed * 8, 1 /*numberOfParticles*/, xSpeed, 0.3 * ySpeed, zSpeed, 0.10d /*particleSpeed*/ /*, int... particleArguments*/);
			}
		}
		else
		{
			//System.out.println("ItemFish.changeLuck() - this message should not have been shown; please report to MoonFather.");
		}
	}

	private static Player GetTossingPlayer(ItemEntity entityItem)
	{
		if (entityItem.getOwner() instanceof Player p)
		{
			return p;
		}
		return null;
	}
}

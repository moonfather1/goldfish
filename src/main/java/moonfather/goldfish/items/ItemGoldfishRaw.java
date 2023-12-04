package moonfather.goldfish.items;

import moonfather.goldfish.FishTossHandler;
import moonfather.goldfish.utility.PathFindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGoldfishRaw extends Item
{
	public ItemGoldfishRaw()
	{
		super(new Properties()
				.tab(CreativeModeTab.TAB_FOOD)
				.food(new FoodProperties.Builder()
						.nutrition(1)
						.saturationMod(0.1f).build()
				)
		);
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> lines, TooltipFlag flag)
	{
		super.appendHoverText(stack, world, lines, flag);
		lines.add(new TranslatableComponent("item.goldfish.goldfish_raw.tooltip").withStyle(ChatFormatting.DARK_GRAY));
	}



	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem)
	{
		if (!entityItem.level.isClientSide())
		{
			if (entityItem.isAlive() && entityItem.getAge() % 40 == 0)
			{
				if (Math.abs(entityItem.getDeltaMovement().x) < 2e-2 && Math.abs(entityItem.getDeltaMovement().y) < 15e-2 && Math.abs(entityItem.getDeltaMovement().z) < 2e-2)
				{
					if (entityItem.isInWater() && PathFindingHelper.IsPartOfASeriousBodyOfWater(entityItem.level, entityItem.blockPosition()))
					{
						FishTossHandler.changeLuck(entityItem, true);
						FishTossHandler.showStupidParticles(entityItem, ParticleTypes.NOTE);
					}
					else
					{
						FishTossHandler.changeLuck(entityItem, false);
						FishTossHandler.showStupidParticles(entityItem, ParticleTypes.ANGRY_VILLAGER);
					}
					entityItem.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}
		return super.onEntityItemUpdate(stack, entityItem);
	}
}

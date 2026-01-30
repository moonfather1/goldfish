package moonfather.goldfish.items;

import moonfather.goldfish.FishTossHandler;
import moonfather.goldfish.utility.PathFindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;

public class ItemGoldfishRaw extends Item
{
	public ItemGoldfishRaw(ResourceKey<Item> key)
	{
		super(new Properties()
				.food(new FoodProperties.Builder()
						.nutrition(1)
						.saturationModifier(0.1f).build()
				)
				.setId(key)
		);
	}


	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag)
	{
		super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
		builder.accept(lore);
	}
	private static final Component lore = Component.translatable("item.goldfish.goldfish_raw.tooltip").withStyle(ChatFormatting.DARK_GRAY);



	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem)
	{
		if (! entityItem.level().isClientSide())
		{
			if (entityItem.isAlive() && entityItem.getAge() % 40 == 0)
			{
				if (Math.abs(entityItem.getDeltaMovement().x) < 2e-2 && Math.abs(entityItem.getDeltaMovement().y) < 15e-2 && Math.abs(entityItem.getDeltaMovement().z) < 2e-2)
				{
					if (entityItem.isInWater() && PathFindingHelper.IsPartOfASeriousBodyOfWater(entityItem.level(), entityItem.blockPosition()))
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

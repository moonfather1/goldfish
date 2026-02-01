package moonfather.goldfish.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class GoldfishCookedItem extends Item
{
	public GoldfishCookedItem(ResourceKey<Item> key)
	{
		super(new Properties()
				.food(new FoodProperties.Builder()
						.nutrition(2)
						.saturationModifier(0.1f)
						.build()
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
	Component lore = Component.translatable("item.goldfish.goldfish_cooked.tooltip").withStyle(ChatFormatting.DARK_GRAY);
}

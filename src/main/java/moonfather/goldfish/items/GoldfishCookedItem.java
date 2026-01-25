package moonfather.goldfish.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class GoldfishCookedItem extends Item
{
	public GoldfishCookedItem()
	{
		super(new Properties()
				.food(new FoodProperties.Builder()
						.nutrition(2)
						.saturationModifier(0.1f)
						.build()
				)
		);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag)
	{
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
		list.add(Component.translatable("item.goldfish.goldfish_cooked.tooltip").withStyle(ChatFormatting.DARK_GRAY));
	}
}

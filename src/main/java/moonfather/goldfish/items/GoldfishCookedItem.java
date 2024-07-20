package moonfather.goldfish.items;

import net.minecraft.client.item.TooltipType;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class GoldfishCookedItem extends Item
{
	public GoldfishCookedItem()
	{
		super(new Settings()
				.food(new FoodComponent.Builder()
						.nutrition(2)
						.saturationModifier(0.1f)
						.build()
				)
		);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> lines, TooltipType type)
	{
		super.appendTooltip(stack, context, lines, type);
		lines.add(Text.translatable("item.goldfish.goldfish_cooked.tooltip").formatted(Formatting.DARK_GRAY));
	}
}

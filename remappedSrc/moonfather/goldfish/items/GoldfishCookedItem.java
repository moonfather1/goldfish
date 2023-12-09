package moonfather.goldfish.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldfishCookedItem extends Item
{
	public GoldfishCookedItem()
	{
		super(new Settings()
				.food(new FoodComponent.Builder()
						.hunger(2)
						.saturationModifier(0.1f).build()
				)
		);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> lines, TooltipContext flag)
	{
		super.appendTooltip(stack, world, lines, flag);
		lines.add(Text.translatable("item.goldfish.goldfish_cooked.tooltip").formatted(Formatting.DARK_GRAY));
	}
}

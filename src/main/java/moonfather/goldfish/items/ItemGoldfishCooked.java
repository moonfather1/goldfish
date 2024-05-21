package moonfather.goldfish.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ItemGoldfishCooked extends Item
{
	public ItemGoldfishCooked()
	{
		super(new Properties()
				.food(new FoodProperties.Builder()
						.nutrition(2)
						.saturationModifier(0.1f).build()
				)
		);
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	@ParametersAreNonnullByDefault
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> lines, TooltipFlag flag)
	{
		super.appendHoverText(stack, context, lines, flag);
		lines.add(Component.translatable("item.goldfish.goldfish_cooked.tooltip").withStyle(ChatFormatting.DARK_GRAY));
	}
}

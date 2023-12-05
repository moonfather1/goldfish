package moonfather.goldfish.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class ItemGoldfishCooked extends Item
{
	public ItemGoldfishCooked()
	{
		super(new Properties()
				.food(new FoodProperties.Builder()
						.nutrition(2)
						.saturationMod(0.1f).build()
				)
		);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> lines, TooltipFlag flag)
	{
		super.appendHoverText(stack, world, lines, flag);
		lines.add(Component.translatable("item.goldfish.goldfish_cooked.tooltip").withStyle(ChatFormatting.DARK_GRAY));
	}
}

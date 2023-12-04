package moonfather.goldfish;

import moonfather.goldfish.items.ItemGoldfishCooked;
import moonfather.goldfish.items.ItemGoldfishRaw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid = ModGoldfish.MOD_ID)
public class EventHandlersForStartup
{
	@SubscribeEvent
	public static void onItemsRegistration(final RegistryEvent.Register<Item> event) {
		ModGoldfish.Items.GoldfishRaw = new ItemGoldfishRaw();
		ModGoldfish.Items.GoldfishRaw.setRegistryName("goldfish_raw");
		event.getRegistry().register(ModGoldfish.Items.GoldfishRaw);
		ModGoldfish.Items.GoldFishCooked = new ItemGoldfishCooked();
		ModGoldfish.Items.GoldFishCooked.setRegistryName("goldfish_cooked");
		event.getRegistry().register(ModGoldfish.Items.GoldFishCooked);
	}



	@SubscribeEvent
	public static void onLootModifierRegistration(final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event)
	{
		event.getRegistry().register(new EventHandlersForDrops.LuckBlockDropsModifier.Serializer().setRegistryName(new ResourceLocation(ModGoldfish.MOD_ID,"luck_modifier_for_gems")));
		event.getRegistry().register(new EventHandlersForFishingLoot.FishingLootModifier.Serializer().setRegistryName(new ResourceLocation(ModGoldfish.MOD_ID,"chance_modifier_for_fishing")));
	}



	@SubscribeEvent
	public static void onCommonSetupEvent(FMLCommonSetupEvent event)
	{
		// not actually required for this example....
	}
}

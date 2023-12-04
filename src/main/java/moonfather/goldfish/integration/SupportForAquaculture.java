package moonfather.goldfish.integration;

import moonfather.goldfish.ModGoldfish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE, modid = ModGoldfish.MOD_ID)
public class SupportForAquaculture
{
	@SubscribeEvent
	public static void OnEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.getWorld().isClientSide() || !(event.getEntity() instanceof ItemEntity))
		{
			return;
		}
		ItemEntity e = (ItemEntity)event.getEntity();
		if (e.getItem().isEmpty())
		{
			return;
		}
		if (e.getItem().getItem().getRegistryName().toString().equals("aquaculture:goldfish"))
		{
			e.setItem(new ItemStack(ModGoldfish.Items.GoldfishRaw, e.getItem().getCount()));
		}
	}
}

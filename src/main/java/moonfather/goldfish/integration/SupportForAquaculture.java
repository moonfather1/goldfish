package moonfather.goldfish.integration;

import moonfather.goldfish.ModGoldfish;
import moonfather.goldfish.items.Repository;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE, modid = ModGoldfish.MOD_ID)
public class SupportForAquaculture
{
	@SubscribeEvent
	public static void OnEntityJoinWorld(EntityJoinLevelEvent event)
	{
		if (event.getLevel().isClientSide() || ! (event.getEntity() instanceof ItemEntity))
		{
			return;
		}
		ItemEntity e = (ItemEntity)event.getEntity();
		if (e.getItem().isEmpty())
		{
			return;
		}
		if (BuiltInRegistries.ITEM.getKey(e.getItem().getItem()).toString().equals("aquaculture:goldfish"))
		{
			e.setItem(new ItemStack(Repository.ItemFishRaw.get(), e.getItem().getCount()));
		}
	}
}

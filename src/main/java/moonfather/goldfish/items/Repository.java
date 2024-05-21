package moonfather.goldfish.items;

import com.mojang.serialization.MapCodec;
import moonfather.goldfish.EventHandlersForDrops;
import moonfather.goldfish.EventHandlersForFishingLoot;
import moonfather.goldfish.ModGoldfish;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@EventBusSubscriber(bus= EventBusSubscriber.Bus.MOD, modid = ModGoldfish.MOD_ID)
public class Repository
{
    @SubscribeEvent
    public static void OnTabContents(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey().equals(CreativeModeTabs.FOOD_AND_DRINKS))
        {
            event.accept(ItemFishRaw.get());
            event.accept(ItemFishCooked.get());
        }
    }

    ///////////////////////////////////////////

    public static void Init(IEventBus modBus)
    {
        Repository.ITEMS.register(modBus);
        Repository.LOOT_MODIFIERS.register(modBus);
    }
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModGoldfish.MOD_ID);
    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModGoldfish.MOD_ID);

    public static final Supplier<Item> ItemFishRaw = ITEMS.register("goldfish_raw", ItemGoldfishRaw::new);
    public static final Supplier<Item> ItemFishCooked = ITEMS.register("goldfish_cooked", ItemGoldfishCooked::new);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<EventHandlersForDrops.LuckBlockDropsModifier>> BlockLootSerializer = LOOT_MODIFIERS.register("luck_modifier_for_gems", ()->EventHandlersForDrops.LuckBlockDropsModifier.CODEC);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> FishingLootSerializer = LOOT_MODIFIERS.register("chance_modifier_for_fishing", ()->EventHandlersForFishingLoot.FishingLootModifier.CODEC);
}

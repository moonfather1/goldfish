package moonfather.goldfish.items;

import com.mojang.serialization.Codec;
import moonfather.goldfish.EventHandlersForDrops;
import moonfather.goldfish.EventHandlersForFishingLoot;
import moonfather.goldfish.ModGoldfish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid = ModGoldfish.MOD_ID)
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

    public static void Init()
    {
        Repository.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Repository.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModGoldfish.MOD_ID);
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModGoldfish.MOD_ID);

    public static final Supplier<Item> ItemFishRaw = ITEMS.register("goldfish_raw", ItemGoldfishRaw::new);
    public static final Supplier<Item> ItemFishCooked = ITEMS.register("goldfish_cooked", ItemGoldfishCooked::new);

    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<EventHandlersForDrops.LuckBlockDropsModifier>> BlockLootSerializer = LOOT_MODIFIERS.register("luck_modifier_for_gems", ()->EventHandlersForDrops.LuckBlockDropsModifier.CODEC);
    public static final Supplier<Codec<? extends IGlobalLootModifier>> FishingLootSerializer = LOOT_MODIFIERS.register("chance_modifier_for_fishing", ()->EventHandlersForFishingLoot.FishingLootModifier.CODEC);
}

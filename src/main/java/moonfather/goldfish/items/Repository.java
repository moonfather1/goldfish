package moonfather.goldfish.items;

import com.mojang.serialization.MapCodec;
import moonfather.goldfish.EventHandlersForDrops;
import moonfather.goldfish.EventHandlersForFishingLoot;
import moonfather.goldfish.ModGoldfish;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class Repository
{
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
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModGoldfish.MODID);
    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModGoldfish.MODID);

    private static final String idShort1 = "goldfish_raw";
    private static final ResourceKey<Item> idFull1 = ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(ModGoldfish.MODID, idShort1));
    public static final Supplier<Item> ItemFishRaw = ITEMS.register(idShort1, () -> new ItemGoldfishRaw(idFull1));
    private static final String idShort2 = "goldfish_cooked";
    private static final ResourceKey<Item> idFull2 = ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(ModGoldfish.MODID, idShort2));
    public static final Supplier<Item> ItemFishCooked = ITEMS.register(idShort2, () -> new ItemGoldfishCooked(idFull2));
    // these should have been locals but i can't deal with this crappy new registration system now.  4 fields will stay in memory.

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<EventHandlersForDrops.LuckBlockDropsModifier>> BlockLootSerializer = LOOT_MODIFIERS.register("luck_modifier_for_gems", ()-> EventHandlersForDrops.LuckBlockDropsModifier.CODEC);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> FishingLootSerializer = LOOT_MODIFIERS.register("chance_modifier_for_fishing", ()-> EventHandlersForFishingLoot.FishingLootModifier.CODEC);
}

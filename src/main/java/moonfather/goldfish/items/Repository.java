package moonfather.goldfish.items;

import com.mojang.serialization.Codec;
import moonfather.goldfish.EventHandlersForDrops;
import moonfather.goldfish.EventHandlersForFishingLoot;
import moonfather.goldfish.ModGoldfish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModGoldfish.MOD_ID);
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModGoldfish.MOD_ID);

    public static final RegistryObject<Item> ItemFishRaw = ITEMS.register("goldfish_raw", ItemGoldfishRaw::new);
    public static final RegistryObject<Item> ItemFishCooked = ITEMS.register("goldfish_cooked", ItemGoldfishCooked::new);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BlockLootSerializer = LOOT_MODIFIERS.register("luck_modifier_for_gems", ()->EventHandlersForDrops.LuckBlockDropsModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> FishingLootSerializer = LOOT_MODIFIERS.register("chance_modifier_for_fishing", ()->EventHandlersForFishingLoot.FishingLootModifier.CODEC);
}

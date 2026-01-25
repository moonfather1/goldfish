package moonfather.goldfish.items;

import moonfather.goldfish.Goldfish;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Repository
{
    public static final Item ItemFishRaw = new GoldfishRawItem();
    public static final Item ItemFishCooked = new GoldfishCookedItem();

    /////////////////////

    public static void init()
    {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Goldfish.MOD_ID, "goldfish_raw"), ItemFishRaw);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Goldfish.MOD_ID, "goldfish_cooked"), ItemFishCooked);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content ->
        {
            content.addAfter(Items.PUFFERFISH, ItemFishCooked);
            content.addAfter(Items.PUFFERFISH, ItemFishRaw);
        });
    }
}

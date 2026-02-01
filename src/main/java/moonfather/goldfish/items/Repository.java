package moonfather.goldfish.items;

import moonfather.goldfish.Goldfish;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Repository
{
    private static final Identifier id1;
    private static final Identifier id2;

    static // constructor
    {
        String shortId1 = "goldfish_raw";
        String shortId2 = "goldfish_cooked";
        id1 = Identifier.fromNamespaceAndPath(Goldfish.MOD_ID, shortId1);
        id2 = Identifier.fromNamespaceAndPath(Goldfish.MOD_ID, shortId2);
        ResourceKey<Item> key1 = ResourceKey.create(BuiltInRegistries.ITEM.key(), id1);
        ResourceKey<Item> key2 = ResourceKey.create(BuiltInRegistries.ITEM.key(), id2);
        ItemFishRaw = new GoldfishRawItem(key1);
        ItemFishCooked = new GoldfishCookedItem(key2);
    }
    public static final Item ItemFishRaw;
    public static final Item ItemFishCooked;

    /////////////////////

    public static void init()
    {
        // moved above part here from static con. this is fabric so no problemo.
        Registry.register(BuiltInRegistries.ITEM, id1, ItemFishRaw);
        Registry.register(BuiltInRegistries.ITEM, id2, ItemFishCooked);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content ->
        {
            content.insertAfter(Items.PUFFERFISH, ItemFishCooked);
            content.insertAfter(Items.PUFFERFISH, ItemFishRaw);
        });
    }
}

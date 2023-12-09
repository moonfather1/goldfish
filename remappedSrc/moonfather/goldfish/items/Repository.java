package moonfather.goldfish.items;

import moonfather.goldfish.Goldfish;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Repository
{
    public static final Item ItemFishRaw = new GoldfishRawItem();
    public static final Item ItemFishCooked = new GoldfishCookedItem();

    /////////////////////

    public static void init()
    {
        Registry.register(Registries.ITEM, new Identifier(Goldfish.MOD_ID, "goldfish_raw"), ItemFishRaw);
        Registry.register(Registries.ITEM, new Identifier(Goldfish.MOD_ID, "goldfish_cooked"), ItemFishCooked);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content ->
        {
            content.addAfter(Items.PUFFERFISH, ItemFishCooked);
            content.addAfter(Items.PUFFERFISH, ItemFishRaw);
        });
    }
}

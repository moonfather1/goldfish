package moonfather.goldfish;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventHandlersForFishingLoot
{
	public static class FishingLootModifier extends LootModifier
	{
		private final int percentageChance;



		public FishingLootModifier(ResourceLocation name, LootItemCondition[] conditionsIn, int percentageChance)
		{
			super(conditionsIn);
			this.percentageChance = percentageChance;
		}



		@NotNull
		@Override
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
		{
			if (context.getQueriedLootTableId().equals(BuiltInLootTables.FISHING))
			{
				if (context.getLevel().random.nextInt(100) < percentageChance && generatedLoot.size() > 0)
				{
					if (generatedLoot.get(0).isEdible())
					{
						generatedLoot.remove(0);
						generatedLoot.add(new ItemStack(ModGoldfish.Items.GoldfishRaw));
					}
				}
			}
			return generatedLoot;
		}


		public static class Serializer extends GlobalLootModifierSerializer<EventHandlersForFishingLoot.FishingLootModifier>
		{
			private final String key = "percentChanceToPullAGoldfish";

			@Override
			public FishingLootModifier read(ResourceLocation location, JsonObject json, LootItemCondition[] conditionsIn)
			{
				int percentage = 4;
				if (json != null && json.has(key))
				{
					percentage = json.get(key).getAsInt();
					percentage = (int) Math.round(percentage * OptionsHolder.COMMON.FishChance.get());
					if (percentage < 0) { percentage = 0; }
					if (percentage > 100) { percentage = 100; }
				}
				return new EventHandlersForFishingLoot.FishingLootModifier(location, conditionsIn, percentage);
			}

			@Override
			public JsonObject write(FishingLootModifier instance)
			{
				JsonObject result = new JsonObject();
				result.add("conditions", new JsonArray());
				result.addProperty(key, instance.percentageChance);
				result.addProperty("hintPt1", "set the howManyPercentPerLuckLevel to desired value. default is 4, meaning 4% chance. ");
				result.addProperty("hintPt2", "for reference, in vanilla, codfish has a 60% chance, salmon 25%, pufferfish 13% and clownfish only 2%. ");
				return result;			}
		}
	}
}

package moonfather.goldfish;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moonfather.goldfish.items.Repository;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventHandlersForFishingLoot
{
	public static class FishingLootModifier extends LootModifier
	{
		public FishingLootModifier(LootItemCondition[] conditionsIn, Integer u)
		{
			super(conditionsIn);
		}



		@NotNull
		@Override
		protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
		{
			if (context.getQueriedLootTableId().equals(BuiltInLootTables.FISHING))
			{
				int percentageChance = (int) Math.round(4 * OptionsHolder.COMMON.FishChance.get()); // 4% is default
				if (context.getLevel().random.nextInt(100) < percentageChance && generatedLoot.size() > 0)
				{
					if (generatedLoot.get(0).isEdible())
					{
						generatedLoot.remove(0);
						generatedLoot.add(new ItemStack(Repository.ItemFishRaw.get()));
					}
				}
			}
			return generatedLoot;
		}

		//////////////////////////////////////////////

		private int unused;

		@Override
		public Codec<? extends IGlobalLootModifier> codec() {
			return CODEC;
		}

		public static final Codec<FishingLootModifier> CODEC =
				RecordCodecBuilder.create(inst -> codecStart(inst)
						.and(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("unused").forGetter((m) -> m.unused))
						.apply(inst, FishingLootModifier::new));
	}
}

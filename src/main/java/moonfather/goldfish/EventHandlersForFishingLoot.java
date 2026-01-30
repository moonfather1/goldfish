package moonfather.goldfish;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moonfather.goldfish.items.Repository;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

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
			int baseChance = 0;
			Identifier tableId = context.getQueriedLootTableId();
			if (tableId.equals(BuiltInLootTables.FISHING.identifier()))   // we used to go for BuiltInLootTables.FISHING_FISH but that doesn't trigger in 26.1
			{
				baseChance = 4; // 4 percent
			}
			else { return generatedLoot; }
			// sup[port for tide 1 removed here. see 1.21.1.    tide 2 handled in data files.
			int percentageChance = (int) Math.round(baseChance * Config.FishChance.get()); // 4% is default
			if (context.getLevel().getRandom().nextInt(100) < percentageChance)
			{
				if (generatedLoot.size() > 0)
				{
					generatedLoot.remove(0);
				}
				generatedLoot.add(new ItemStack(Repository.ItemFishRaw.get()));
			}
			return generatedLoot;
		}

		//////////////////////////////////////////////


		private int unused;

		@Override
		public MapCodec<? extends IGlobalLootModifier> codec() {
			return CODEC;
		}

		public static final MapCodec<FishingLootModifier> CODEC =
				RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
						.and(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("unused").forGetter((m) -> m.unused))
						.apply(inst, FishingLootModifier::new));
	}
}

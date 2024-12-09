package moonfather.goldfish;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moonfather.goldfish.items.Repository;
import net.minecraft.resources.ResourceLocation;
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
			ResourceLocation tableId = context.getQueriedLootTableId();
			if (tableId.equals(BuiltInLootTables.FISHING_FISH.location()))
			{
				baseChance = 4; // 4 percent
			}
			if (tableId.equals(TIDE_FISH1) || tableId.equals(TIDE_FISH2) || tableId.equals(TIDE_FISH3) || tableId.equals(TIDE_FISH4) || tableId.equals(TIDE_FISH5) || tableId.equals(TIDE_FISH6) || tableId.equals(TIDE_FISH7) || tableId.equals(TIDE_FISH8) || tableId.equals(TIDE_FISH9))
			{
				baseChance = 2; // 2 percent because of more fish types
			}
			if (tableId.equals(TIDE_FISH_HIGH_1) || tableId.equals(TIDE_FISH_HIGH_2))
			{
				baseChance = 10; // 2 percent because of more fish types
			}
			int percentageChance = (int) Math.round(baseChance * OptionsHolder.COMMON.FishChance.get()); // 4% is default
			if (context.getLevel().random.nextInt(100) < percentageChance)
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

		private static final ResourceLocation TIDE_FISH1 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/birch");
		private static final ResourceLocation TIDE_FISH2 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/forest");
		private static final ResourceLocation TIDE_FISH3 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/jungle");
		private static final ResourceLocation TIDE_FISH4 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/mountain");
		private static final ResourceLocation TIDE_FISH5 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/plains");
		private static final ResourceLocation TIDE_FISH6 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/savanna");
		private static final ResourceLocation TIDE_FISH7 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/taiga");
		private static final ResourceLocation TIDE_FISH8 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/climates/freshwater_normal");
		private static final ResourceLocation TIDE_FISH9 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/climates/freshwater_cold");
		private static final ResourceLocation TIDE_FISH_HIGH_1 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/mushroom");
		private static final ResourceLocation TIDE_FISH_HIGH_2 = ResourceLocation.fromNamespaceAndPath("tide", "gameplay/fishing/biomes/cherry");

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

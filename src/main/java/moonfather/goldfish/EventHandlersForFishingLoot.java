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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
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
			int baseChance = 0;
			ResourceLocation tableId = context.getQueriedLootTableId();
			if (tableId.equals(BuiltInLootTables.FISHING_FISH))
			{
				baseChance = 4; // 4 percent
			}
			if (tableId.equals(TIDE_FISH1) || tableId.equals(TIDE_FISH2) || tableId.equals(TIDE_FISH3) || tableId.equals(TIDE_FISH4) || tableId.equals(TIDE_FISH5) || tableId.equals(TIDE_FISH6) || tableId.equals(TIDE_FISH7) || tableId.equals(TIDE_FISH8) || tableId.equals(TIDE_FISH9))
			{
				baseChance = 2; // 2 percent because of more fish types
			}
			if (tableId.equals(TIDE_FISH_HIGH_1) || tableId.equals(TIDE_FISH_HIGH_2))
			{
				baseChance = 10; // 10 percent here
			}
			if (tableId.equals(TIDE_FISH8) && context.hasParam(LootContextParams.ORIGIN) && context.getLevel().getBiome(BlockPos.containing(context.getParam(LootContextParams.ORIGIN))).is(Biomes.CHERRY_GROVE))
			{
				baseChance = 10; // 10 percent here
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

		private static final ResourceLocation TIDE_FISH1 = new ResourceLocation("tide", "gameplay/fishing/biomes/birch");
		private static final ResourceLocation TIDE_FISH2 = new ResourceLocation("tide", "gameplay/fishing/biomes/forest");
		private static final ResourceLocation TIDE_FISH3 = new ResourceLocation("tide", "gameplay/fishing/biomes/jungle");
		private static final ResourceLocation TIDE_FISH4 = new ResourceLocation("tide", "gameplay/fishing/biomes/mountain");
		private static final ResourceLocation TIDE_FISH5 = new ResourceLocation("tide", "gameplay/fishing/biomes/plains");
		private static final ResourceLocation TIDE_FISH6 = new ResourceLocation("tide", "gameplay/fishing/biomes/savanna");
		private static final ResourceLocation TIDE_FISH7 = new ResourceLocation("tide", "gameplay/fishing/biomes/taiga");
		private static final ResourceLocation TIDE_FISH8 = new ResourceLocation("tide", "gameplay/fishing/climates/freshwater_normal");
		private static final ResourceLocation TIDE_FISH9 = new ResourceLocation("tide", "gameplay/fishing/climates/freshwater_cold");
		private static final ResourceLocation TIDE_FISH_HIGH_1 = new ResourceLocation("tide", "gameplay/fishing/biomes/mushroom");
		private static final ResourceLocation TIDE_FISH_HIGH_2 = new ResourceLocation("tide", "gameplay/fishing/biomes/cherry");

		//////////////////////////////////////////////////////
		
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

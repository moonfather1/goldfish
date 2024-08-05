package moonfather.goldfish;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.GAME, modid = ModGoldfish.MOD_ID)
public class EventHandlersForDrops
{

	
	
	@SubscribeEvent
	public static void OnLootingLevel(LivingDropsEvent event)
	{
		if (OptionsHolder.COMMON.DropExtraLootFromMobs.get() == false)
		{
			return;
		}
		if (event.getEntity().lastHurtByPlayer == null || event.getEntity().getLootTable() == null)
		{
			return;
		}
		Player player = event.getEntity().lastHurtByPlayer;
		float luck = player.getLuck();
		if (luck <= 0)
		{
			return;
		}
		// copied from dropFromLootTable
		ResourceKey<LootTable> resourceKey = event.getEntity().getLootTable();
		LootTable loottable = event.getEntity().level().getServer().reloadableRegistries().getLootTable(resourceKey);
		net.minecraft.world.level.storage.loot.LootParams.Builder lootparams$builder = (new net.minecraft.world.level.storage.loot.LootParams.Builder((ServerLevel)event.getEntity().level()))
				.withParameter(LootContextParams.THIS_ENTITY, event.getEntity())
				.withParameter(LootContextParams.ORIGIN, event.getEntity().position())
				.withParameter(LootContextParams.DAMAGE_SOURCE, event.getSource())
				.withOptionalParameter(LootContextParams.ATTACKING_ENTITY, event.getSource().getEntity())
				.withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, event.getSource().getDirectEntity())
				.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
				.withLuck(player.getLuck());
		LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
		for (int i = 1; i <= Math.round(luck); i++)
		{
			loottable.getRandomItems(lootparams, (item) -> { if (event.getDrops().stream().noneMatch(original -> ItemStack.isSameItem(original.getItem(), item))) event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY() + 1, event.getEntity().getZ(), item)); });
		}
		///////////////
	}



	public static class LuckBlockDropsModifier extends LootModifier
	{
		private int percentagePerLevel = 10;

		public LuckBlockDropsModifier(LootItemCondition[] conditionsIn, Integer percentagePerLevel)
		{
			super(conditionsIn);
			this.percentagePerLevel = percentagePerLevel;
		}

		@Override
		@ParametersAreNonnullByDefault
		@NotNull
		public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
		{
			if (OptionsHolder.COMMON.DropExtraGemsFromOreBlocks.get() == false)
			{
				return generatedLoot;
			}
			BlockState block = context.getParamOrNull(LootContextParams.BLOCK_STATE);
			Entity playerHopefully = context.getParamOrNull(LootContextParams.THIS_ENTITY);
			Player player = (playerHopefully instanceof Player) ? (Player)playerHopefully : null;
			if (block == null || player == null)
			{
				return generatedLoot;
			}
			if (player.getLuck() == 0)
			{
				return generatedLoot;
			}

			float luckLevel = player.getLuck();
			ObjectArrayList<ItemStack> ret = new ObjectArrayList<ItemStack>();
			for (ItemStack drop : generatedLoot)
			{
				if (drop == null || drop.isEmpty())
				{
					continue;
				}

				if (drop.is(gemTag))
				{
					double chance = Math.abs(luckLevel) * Math.pow(1.1D,  Math.abs(luckLevel)) * this.percentagePerLevel;
					if (luckLevel > 0)
					{
						while (chance >= 100)
						{
							chance = chance - 100;
							ret.add(drop.copy());
						}
						if (chance >= player.level().random.nextInt(100))
						{
							ret.add(drop.copy());
						}
					}
					else
					{
						if (chance >= player.level().random.nextInt(100))
						{
							ret.add(new ItemStack(Items.COAL, drop.getCount()));
							continue;
						}
					}
				}
				ret.add(drop);
			}
			return ret;
		}
		private static final TagKey<Item> gemTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","gems"));



		@Override
		@NotNull
		public MapCodec<? extends IGlobalLootModifier> codec() {
			return CODEC;
		}

		public static final MapCodec<LuckBlockDropsModifier> CODEC =
				RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
						.and(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("howManyPercentPerLuckLevel").forGetter((m) -> m.percentagePerLevel))
						.apply(inst, LuckBlockDropsModifier::new));
	}
}

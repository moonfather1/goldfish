package moonfather.goldfish;

import com.google.common.base.Suppliers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.event.entity.living.LootingLevelEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE, modid = ModGoldfish.MOD_ID)
public class EventHandlersForDrops
{

	
	
	@SubscribeEvent
	public static void OnLootingLevel(LootingLevelEvent event)
	{
		if (OptionsHolder.COMMON.DropExtraLootFromMobs.get() == false)
		{
			return;
		}
		if (event.getDamageSource() == null || !event.getDamageSource().getMsgId().equals("player") || !(event.getDamageSource().getEntity() instanceof Player))
		{
			return;
		}
		Player player = (Player)event.getDamageSource().getEntity();
		if (player == null)
		{
			return;
		}
		float luck = player.getLuck();
		if (luck == 0 || (luck < 0 && event.getLootingLevel() < 1))
		{
			return;
		}
		if (luck < -4 && event.getLootingLevel() < 2)
		{
			event.setLootingLevel(0);
			return;
		}

		int bonus = luck > 0 ? 1 : -1;
		luck = Math.abs(luck);
		if (player.level().random.nextInt(4) < luck)
		{
			event.setLootingLevel(event.getLootingLevel() + bonus); // 25%*level chance for +1
		}
		luck = luck - 4;
		if (player.level().random.nextInt(4) < luck)
		{
			event.setLootingLevel(event.getLootingLevel() + bonus); // 25%*(level-4) chance for another +1
		}
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

			TagKey<Item> gemTag = TagKey.create(Registries.ITEM, new ResourceLocation("forge","gems"));
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



		@Override
		public Codec<? extends IGlobalLootModifier> codec() {
			return CODEC;
		}

		public static final Codec<LuckBlockDropsModifier> CODEC =
				RecordCodecBuilder.create(inst -> codecStart(inst)
						.and(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("howManyPercentPerLuckLevel").forGetter((m) -> m.percentagePerLevel))
						.apply(inst, LuckBlockDropsModifier::new));
	}
}

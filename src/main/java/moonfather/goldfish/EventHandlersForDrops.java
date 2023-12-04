package moonfather.goldfish;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

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
		if (player.level.random.nextInt(4) < luck)
		{
			event.setLootingLevel(event.getLootingLevel() + bonus); // 25%*level chance for +1
		}
		luck = luck - 4;
		if (player.level.random.nextInt(4) < luck)
		{
			event.setLootingLevel(event.getLootingLevel() + bonus); // 25%*(level-4) chance for another +1
		}
	}



	public static class LuckBlockDropsModifier extends LootModifier
	{
		private int percentagePerLevel = 10;

		public LuckBlockDropsModifier(ResourceLocation name, LootItemCondition[] conditionsIn, int percentagePerLevel)
		{
			super(conditionsIn);
			this.percentagePerLevel = percentagePerLevel;
		}

		@Override
		public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
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

			TagKey<Item> gemTag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge","gems"));
			float luckLevel = player.getLuck();
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
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
						if (chance >= player.level.random.nextInt(100))
						{
							ret.add(drop.copy());
						}
					}
					else
					{
						if (chance >= player.level.random.nextInt(100))
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



		public static class Serializer extends GlobalLootModifierSerializer<LuckBlockDropsModifier>
		{
			@Override
			public LuckBlockDropsModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn)
			{
				int percentagePerLevel = 10;
				if (json != null && json.has("howManyPercentPerLuckLevel"))
				{
					percentagePerLevel = json.get("howManyPercentPerLuckLevel").getAsInt();
				}
				return new LuckBlockDropsModifier(name, conditionsIn, percentagePerLevel);
			}

			@Override
			public JsonObject write(LuckBlockDropsModifier luckBlockDropsModifier)
			{
				JsonObject result = new JsonObject();
				result.add("conditions", new JsonArray());
				result.addProperty("howManyPercentPerLuckLevel", luckBlockDropsModifier.percentagePerLevel);
				result.addProperty("hintPt1", "set the howManyPercentPerLuckLevel to desired value. default is 10, meaning 10% chance. ");
				result.addProperty("hintPt2", "for each level of luck currently on the player, there is that much of a chance to double any gem that the player mines (including modded gems). ");
				result.addProperty("hintPt3", "so, for luck III (don't mine diamonds with less than that), there is 3x10% chance do duplicate each separate diamond. luck effect synergizes with fortune as fortune gives us more gems to potentially duplicate. ");
				result.addProperty("hintPt4", "values over 100% are okay (technically, not in terms of balance); for example luck VII (level seven) and 40% in this file means 2 guaranteed extra gems plus 80% chance to get the third.");
				result.addProperty("hintPt5", "oh, and global loot modifiers are annoying.");
				return result;
			}
		}
	}
}

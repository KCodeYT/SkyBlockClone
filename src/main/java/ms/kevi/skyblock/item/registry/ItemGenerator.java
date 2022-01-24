/*
 * Copyright 2022 KCodeYT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ms.kevi.skyblock.item.registry;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.Tag;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.GiftedItemData;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.item.enchantment.Enchantment;
import ms.kevi.skyblock.item.enchantment.EnchantmentRegistry;
import ms.kevi.skyblock.item.registry.set.PieceItem;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.Color;
import ms.kevi.skyblock.util.Utils;

import java.util.*;

@Builder
@Getter
@AllArgsConstructor
public class ItemGenerator {

    private static final Gson GSON = new Gson();

    public static Item create(String identifier, int amount) {
        return create(Item.get(0, 0, amount).setCompoundTag(new CompoundTag().putCompound(ItemHelper.EXTRA_ATTRIBUTES, new CompoundTag().putString(ItemHelper.ATTRIBUTE_ID, identifier))));
    }

    public static Item create(Item item) {
        final String attributeId = ItemHelper.getAttributeId(item);
        if(attributeId.equalsIgnoreCase("UNKNOWN"))
            return item;
        final IGameItem gameItem = Registries.ITEMS.valueOf(attributeId);
        if(gameItem == null)
            return item;
        final IModifier modifier = ItemHelper.getModifier(item);
        final List<Enchantment> enchantments = ItemHelper.getEnchantments(item);
        final int rarityUpgrades = ItemHelper.getRarityUpgrades(item);
        final UUID uniqueId = ItemHelper.getUUID(item);

        final GameRarity rarity = GameRarity.getByLowestId(gameItem.getRarity().getId() + rarityUpgrades);
        final String modifierName = modifier != null ? (gameItem.getDisplayName().toLowerCase().startsWith(modifier.getDisplayName().toLowerCase()) ? modifier.getSameDisplayName() : modifier.getDisplayName()) : null;
        final String itemName = "§r" + rarity.getColorCode() + (modifierName != null ? modifierName + " " : "") + gameItem.getDisplayName();
        final List<String> lore = new ArrayList<>();

        final StatsBooster statsBooster = gameItem.getStatsBooster() != null ? gameItem.getStatsBooster() : StatsBooster.empty();
        final StatsBooster modifierStatsBooster = modifier != null ? modifier.getStatsBooster(rarity) : StatsBooster.empty();
        final Map<GameStats, Integer> gameStatsMap = Collections.unmodifiableMap(StatsBooster.combine(statsBooster, modifierStatsBooster).getGameStatsMap());
        final Map<GameStats, Integer> modifierGameStatsMap = modifierStatsBooster.getGameStatsMap();

        final GameStats[] sortGameStats = GameStats.sortValues();
        GameStats oldStats = null;
        for(GameStats gameStats : sortGameStats) {
            if(gameStatsMap.containsKey(gameStats)) {
                if(oldStats != null && oldStats.getStatsType() != gameStats.getStatsType() && !lore.isEmpty())
                    lore.add("§r");
                final StringBuilder line = new StringBuilder("§r§7" + gameStats.getDisplayName() + ": " + gameStats.getStatsType().getColorCode() + Utils.formatNumber(gameStatsMap.get(gameStats), true) + gameStats.getSuffix());
                if(modifier != null && modifierGameStatsMap.containsKey(gameStats))
                    line.append(" §r§9(").append(modifier.getDisplayName()).append(" ").append(Utils.formatNumber(modifierGameStatsMap.get(gameStats), true)).append(gameStats.getSuffix()).append(")");
                lore.add(line.toString());
                oldStats = gameStats;
            }
        }

        final String[] lorePrefix = gameItem.getLorePrefix();
        if(lorePrefix != null && lorePrefix.length > 0) {
            if(!lore.isEmpty())
                lore.add("§r");
            for(String prefix : lorePrefix)
                lore.add("§r" + prefix);
        }

        final String itemDescription = gameItem.getDescription();
        if(itemDescription != null) {
            if(!lore.isEmpty())
                lore.add("§r");
            ItemHelper.addDescription(lore, itemDescription);
        }

        if(enchantments != null && !enchantments.isEmpty()) {
            if(!lore.isEmpty())
                lore.add("§r");

            if(enchantments.size() < 3) {
                for(Enchantment enchantment : enchantments) {
                    lore.add("§r§9" + enchantment.getName() + " " + Utils.toRoman(enchantment.getLevel()));
                    ItemHelper.addDescription(lore, enchantment.getDescription());
                }
            } else {
                final List<List<Enchantment>> subEnchantments = new ArrayList<>();
                int index = 0;
                while(enchantments.size() > index) {
                    subEnchantments.add(enchantments.subList(index, Math.min(index + 2, enchantments.size())));
                    index += 2;
                }

                for(List<Enchantment> smallEnchantments : subEnchantments) {
                    final StringBuilder line = new StringBuilder("§r§9");
                    for(int i = 0; i < smallEnchantments.size(); i++) {
                        final Enchantment enchantment = smallEnchantments.get(i);
                        line.append(enchantment.getName()).append(" ").append(Utils.toRoman(enchantment.getLevel()));
                        if(i + 1 < smallEnchantments.size())
                            line.append(", ");
                    }

                    lore.add(line.toString());
                }
            }
        }

        for(IAbility ability : new IAbility[]{
                gameItem.getAbility(),
                gameItem instanceof PieceItem ? ((PieceItem) gameItem).getBonus() : null,
                gameItem instanceof PieceItem ? ((PieceItem) gameItem).getExtraBonus() : null
        }) {
            if(ability != null) {
                if(!lore.isEmpty())
                    lore.add("§r");
                final StringBuilder line = new StringBuilder("§r§6" + ability.getType().getDisplayName() + ": " + ability.getDisplayName());
                final String interactionName = ability.getInteractionType().getDisplayName();
                if(!interactionName.isEmpty())
                    line.append(" ").append("§e§l").append(interactionName);
                lore.add(line.toString());
                final String description = ability.getDescription();
                if(!description.isEmpty())
                    ItemHelper.addDescription(lore, description);
                final int intelligenceCost = ability.getIntelligenceCost();
                if(intelligenceCost != 0)
                    lore.add("§r§8Mana Cost: §3" + Utils.formatNumber(intelligenceCost));
                final double coolDown = ability.getCoolDown();
                if(coolDown != 0)
                    lore.add("§r§8Cooldown: §a" + Utils.formatNumber(coolDown) + "s");
            }
        }

        if(gameItem.getRecipes() != null) {
            if(!lore.isEmpty())
                lore.add("§r");
            lore.add("§r§eRight-Click to view recipes!");
        }

        final String[] loreSuffix = gameItem.getLoreSuffix();
        if(loreSuffix != null && loreSuffix.length > 0) {
            lore.add("§r");
            for(String suffix : loreSuffix)
                lore.add("§r" + suffix);
        }

        final GiftedItemData giftedItemData = ItemHelper.getGiftedData(item);
        if(giftedItemData != null) {
            lore.add("§r");
            lore.add("§r§7To: " + giftedItemData.getRecipientName());
            lore.add("§r§7From: " + giftedItemData.getSenderName());
            lore.add("§r");
            lore.add("§r§8Edition #" + Utils.formatNumber(giftedItemData.getEdition()));
            lore.add("§r§8" + Utils.formatDate(giftedItemData.getDate()));
        }

        if(modifier != null && modifier.getModifierBonus() != null) {
            if(!lore.isEmpty())
                lore.add("§r");
            lore.add("§r§9" + modifier.getDisplayName() + " Bonus");
            ItemHelper.addDescription(lore, modifier.getModifierBonus().getDescription());
        }

        if(!lore.isEmpty())
            lore.add("§r");
        if(gameItem.isModifiable() && modifier == null)
            lore.add("§r§8This item can be reforged!");
        lore.add("§r" + rarity.buildName(true, rarityUpgrades > 0, gameItem.isFromDungeon(), gameItem.getItemType()));

        final CompoundTag itemExtraAttributes = new CompoundTag(ItemHelper.EXTRA_ATTRIBUTES).putString(ItemHelper.ATTRIBUTE_ID, gameItem.getAttributeId());
        if(gameItem.getStackSize() != 0 && gameItem.getStackSize() < 2)
            itemExtraAttributes.putString(ItemHelper.ATTRIBUTE_UUID, (uniqueId != null ? uniqueId : UUID.randomUUID()).toString());
        if(modifier != null)
            itemExtraAttributes.putString(ItemHelper.ATTRIBUTE_MODIFIER, modifier.name().toLowerCase());
        if(rarityUpgrades > 0)
            itemExtraAttributes.putInt(ItemHelper.ATTRIBUTE_RARITY_UPGRADES, rarityUpgrades);
        if(enchantments != null && !enchantments.isEmpty()) {
            final CompoundTag enchantmentsTag = new CompoundTag();
            for(Enchantment enchantment : enchantments)
                Arrays.stream(EnchantmentRegistry.values()).filter(enchantmentRegistry -> Objects.requireNonNull(enchantmentRegistry.newInstance(0)).getClass().equals(enchantment.getClass())).findAny().ifPresent(enchantmentRegistry -> enchantmentsTag.putInt(enchantmentRegistry.name().toLowerCase(), enchantment.getLevel()));
            itemExtraAttributes.putCompound(ItemHelper.ATTRIBUTE_ENCHANTMENTS, enchantmentsTag);
        }
        if(giftedItemData != null) {
            itemExtraAttributes.putLong(ItemHelper.ATTRIBUTE_GIFTED_DATE, giftedItemData.getDate());
            itemExtraAttributes.putInt(ItemHelper.ATTRIBUTE_GIFTED_EDITION, giftedItemData.getEdition());
            itemExtraAttributes.putString(ItemHelper.ATTRIBUTE_GIFTED_SENDER, giftedItemData.getSenderName());
            itemExtraAttributes.putString(ItemHelper.ATTRIBUTE_GIFTED_RECIPIENT, giftedItemData.getRecipientName());
        }

        final Item finalItem = Item.get(gameItem.getId(), gameItem.getDamage(), item.getCount());
        final CompoundTag namedTag = finalItem.hasCompoundTag() ? finalItem.getNamedTag() : new CompoundTag();
        final Color itemColor = gameItem.getColor();
        if(itemColor != null) {
            namedTag.putInt("customColor", itemColor.getRed() << 16 | itemColor.getGreen() << 8 | itemColor.getBlue());
            itemExtraAttributes.putString(ItemHelper.ATTRIBUTE_COLOR, itemColor.getRed() + ":" + itemColor.getGreen() + ":" + itemColor.getBlue());
        }

        if(gameItem.isSparkling() || (enchantments != null && !enchantments.isEmpty())) {
            final ListTag<CompoundTag> enchantmentsTag = new ListTag<>("ench");
            if(enchantments != null && !enchantments.isEmpty()) {
                for(Enchantment enchantment : enchantments)
                    if(enchantment.getNormalId() >= 0)
                        enchantmentsTag.add(new CompoundTag().
                                putShort("id", enchantment.getNormalId()).
                                putShort("lvl", enchantment.getLevel()));
            }

            namedTag.putList(enchantmentsTag);
        }

        if(gameItem.getCompoundTag() != null) {
            final Map<String, Tag> tags = gameItem.getCompoundTag().getTags();
            for(String tagName : tags.keySet())
                namedTag.put(tagName, tags.get(tagName));
        }

        namedTag.putCompound(ItemHelper.EXTRA_ATTRIBUTES, itemExtraAttributes);
        return finalItem.setNamedTag(namedTag).setCustomName(itemName).setLore(lore.toArray(new String[0]));
    }

}

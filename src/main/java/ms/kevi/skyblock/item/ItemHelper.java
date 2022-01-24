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

package ms.kevi.skyblock.item;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.item.enchantment.Enchantment;
import ms.kevi.skyblock.item.enchantment.EnchantmentRegistry;
import ms.kevi.skyblock.registry.Registries;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ItemHelper {

    public static final String EXTRA_ATTRIBUTES = "ExtraAttributes";
    public static final String ATTRIBUTE_ID = "Id";
    public static final String ATTRIBUTE_MODIFIER = "Modifier";
    public static final String ATTRIBUTE_COLOR = "Color";
    public static final String ATTRIBUTE_UUID = "uuid";
    public static final String ATTRIBUTE_ENCHANTMENTS = "Enchantments";
    public static final String ATTRIBUTE_RARITY_UPGRADES = "RarityUpgrades";
    public static final String ATTRIBUTE_GIFTED_DATE = "Date";
    public static final String ATTRIBUTE_GIFTED_EDITION = "Edition";
    public static final String ATTRIBUTE_GIFTED_SENDER = "SenderName";
    public static final String ATTRIBUTE_GIFTED_RECIPIENT = "RecipientName";
    private static final Pattern UUID_PATTERN = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");

    public static IGameItem getGameItem(Item item) {
        return ItemValidation.hasExtraAttributes(item) ? Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item)) : null;
    }

    public static String getAttributeId(Item item) {
        final String attributeId = item == null ? "" : ItemHelper.getExtraAttributes(item).getString(ItemHelper.ATTRIBUTE_ID);
        return attributeId.isEmpty() ? "UNKNOWN" : attributeId;
    }

    public static GameRarity getRarity(Item item) {
        final String attributeId = getAttributeId(item);
        return GameRarity.getByLowestId(Registries.ITEMS.valueOfNonNull(attributeId).getRarity().getId() + getRarityUpgrades(item));
    }

    public static IModifier getModifier(Item item) {
        return Registries.MODIFIERS.valueOf(ItemHelper.getExtraAttributes(item).getString(ItemHelper.ATTRIBUTE_MODIFIER));
    }

    public static Item setModifier(Item item, IModifier modifier) {
        return ItemHelper.setExtraAttributes(item, ItemHelper.getExtraAttributes(item).putString(ItemHelper.ATTRIBUTE_MODIFIER, modifier.name().toLowerCase()));
    }

    public static UUID getUUID(Item item) {
        final String uuidString = ItemHelper.getExtraAttributes(item).getString(ItemHelper.ATTRIBUTE_UUID);
        return UUID_PATTERN.matcher(uuidString).matches() ? UUID.fromString(uuidString) : null;
    }

    public static List<Enchantment> getEnchantments(Item item) {
        final CompoundTag compoundTag = ItemHelper.getExtraAttributes(item).getCompound(ItemHelper.ATTRIBUTE_ENCHANTMENTS);
        return compoundTag.isEmpty() ? null : new ArrayList<>(compoundTag.getAllTags().stream().map(tag -> Arrays.stream(EnchantmentRegistry.values()).filter(registry -> registry.name().equalsIgnoreCase(tag.getName())).findAny().map(enchantmentRegistry -> enchantmentRegistry.newInstance((Integer) tag.parseValue())).orElse(null)).collect(Collectors.toList()));
    }

    public static Item addEnchantment(Item item, Enchantment... enchantments) {
        final List<Enchantment> enchantmentList = Optional.ofNullable(ItemHelper.getEnchantments(item)).orElse(new ArrayList<>());
        for(Enchantment enchantment : enchantments) {
            enchantmentList.removeIf(value -> value.isSameType(enchantment));
            enchantmentList.add(enchantment);
        }
        final CompoundTag enchantmentsTag = new CompoundTag();
        for(Enchantment enchantment : enchantmentList)
            enchantmentsTag.putInt(enchantment.getName(), enchantment.getLevel());
        return ItemHelper.setExtraAttributes(item, ItemHelper.getExtraAttributes(item).putCompound(ItemHelper.ATTRIBUTE_ENCHANTMENTS, enchantmentsTag));
    }

    public static Item removeEnchantment(Item item, Enchantment... enchantments) {
        final List<Enchantment> enchantmentList = Optional.ofNullable(ItemHelper.getEnchantments(item)).orElse(new ArrayList<>());
        for(Enchantment enchantment : enchantments)
            enchantmentList.removeIf(value -> value.isSameType(enchantment));
        final CompoundTag enchantmentsTag = new CompoundTag();
        for(Enchantment enchantment : enchantmentList)
            enchantmentsTag.putInt(enchantment.getName(), enchantment.getLevel());
        return ItemHelper.setExtraAttributes(item, ItemHelper.getExtraAttributes(item).putCompound(ItemHelper.ATTRIBUTE_ENCHANTMENTS, enchantmentsTag));
    }

    public static int getRarityUpgrades(Item item) {
        return ItemHelper.getExtraAttributes(item).getInt(ItemHelper.ATTRIBUTE_RARITY_UPGRADES);
    }

    public static Item setRarityUpgrades(Item item, int rarityUpgrades) {
        return ItemHelper.setExtraAttributes(item, ItemHelper.getExtraAttributes(item).putInt(ItemHelper.ATTRIBUTE_RARITY_UPGRADES, rarityUpgrades));
    }

    public static CompoundTag getExtraAttributes(Item item) {
        return ItemValidation.hasExtraAttributes(item) ? item.getNamedTag().getCompound(ItemHelper.EXTRA_ATTRIBUTES) : new CompoundTag(ItemHelper.EXTRA_ATTRIBUTES);
    }

    public static Item setExtraAttributes(Item item, CompoundTag compoundTag) {
        item.getNamedTag().putCompound(ItemHelper.EXTRA_ATTRIBUTES, compoundTag);
        return item;
    }

    public static GiftedItemData getGiftedData(Item item) {
        final CompoundTag extraAttributes = getExtraAttributes(item);
        if(!extraAttributes.contains(ItemHelper.ATTRIBUTE_GIFTED_DATE) ||
                !extraAttributes.contains(ItemHelper.ATTRIBUTE_GIFTED_EDITION) ||
                !extraAttributes.contains(ItemHelper.ATTRIBUTE_GIFTED_SENDER) ||
                !extraAttributes.contains(ItemHelper.ATTRIBUTE_GIFTED_RECIPIENT))
            return null;
        return new GiftedItemData(extraAttributes.getLong(ItemHelper.ATTRIBUTE_GIFTED_DATE),
                extraAttributes.getInt(ItemHelper.ATTRIBUTE_GIFTED_EDITION),
                extraAttributes.getString(ItemHelper.ATTRIBUTE_GIFTED_SENDER),
                extraAttributes.getString(ItemHelper.ATTRIBUTE_GIFTED_RECIPIENT));
    }

    public static Item setGiftedData(Item item, GiftedItemData giftedData) {
        return ItemHelper.setExtraAttributes(item, ItemHelper.getExtraAttributes(item).
                putLong(ItemHelper.ATTRIBUTE_GIFTED_DATE, giftedData.getDate()).
                putInt(ItemHelper.ATTRIBUTE_GIFTED_EDITION, giftedData.getEdition()).
                putString(ItemHelper.ATTRIBUTE_GIFTED_SENDER, giftedData.getSenderName()).
                putString(ItemHelper.ATTRIBUTE_GIFTED_RECIPIENT, giftedData.getRecipientName()));
    }

    public static void addDescription(List<String> lore, String description) {
        final List<String> lines = new ArrayList<>();

        for(String line : description.split("\n")) {
            if(!lines.isEmpty())
                lines.add("");
            final String[] words = line.split(" ");
            StringBuilder currentLine = new StringBuilder();
            String lastColor = "";

            for(String word : words) {
                if(currentLine.length() == 0) {
                    if(!lastColor.isEmpty())
                        currentLine.append(lastColor);
                } else
                    currentLine.append(" ");
                currentLine.append(word);
                if(currentLine.length() < 28)
                    continue;
                final String line0 = currentLine.toString();
                lastColor = TextFormat.getLastColors(line0);
                lines.add("§r" + line0);
                currentLine = new StringBuilder();
            }

            if(currentLine.length() > 0)
                lines.add("§r" + currentLine);
        }

        lore.addAll(lines);
    }

    public static Item convertItem(Item item) {
        if(item.hasCompoundTag() && item.getNamedTag().contains(EXTRA_ATTRIBUTES))
            return item;
        final IGameItem gameItem = Registries.ITEMS.fromVanilla(item.getId(), item.getDamage());
        return gameItem != null ? gameItem.createItem(item.getCount()) : item;
    }

    public static boolean isUnknown(Item item) {
        return !ItemValidation.hasExtraAttributes(item) || Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item)) == null;
    }

}

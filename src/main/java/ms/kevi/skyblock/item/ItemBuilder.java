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
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private final Item item;

    private ItemBuilder(Item item) {
        this.item = item;
    }

    public static ItemBuilder get(int id, int damage, int count) {
        return new ItemBuilder(Item.get(id, damage, count));
    }

    public static ItemBuilder from(Item item) {
        return new ItemBuilder(item);
    }

    public static ItemBuilder fromCopy(Item item) {
        final Item copyItem = Item.get(item.getId(), item.getDamage(), item.getCount());
        copyItem.setNamedTag(item.hasCompoundTag() ? item.getNamedTag().copy() : copyItem.hasCompoundTag() ? copyItem.getNamedTag().copy() : new CompoundTag());
        return ItemBuilder.from(copyItem);
    }

    public ItemBuilder setCustomName(String customName) {
        this.item.setCustomName("§r" + customName);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return this.setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(List<String> lore) {
        final List<String> newLore = new ArrayList<>();
        for(String loreEntry : lore)
            newLore.add("§r" + loreEntry);
        this.item.setLore(newLore.toArray(new String[0]));
        return this;
    }

    public ItemBuilder setLore(String lore) {
        this.item.setLore("§r" + lore);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        return this.addLore(Arrays.asList(lore));
    }

    public ItemBuilder addLore(List<String> lore) {
        final List<String> combinedLore = new ArrayList<>(Arrays.asList(this.item.getLore()));
        combinedLore.addAll(lore);
        return this.setLore(combinedLore);
    }

    public ItemBuilder addLore(String lore) {
        final List<String> combinedLore = new ArrayList<>(Arrays.asList(this.item.getLore()));
        combinedLore.add(lore);
        return this.setLore(combinedLore);
    }

    public ItemBuilder addEnchantment(Enchantment... enchantments) {
        this.item.addEnchantment(enchantments);
        return this;
    }

    public ItemBuilder setSparkle(boolean sparkle) {
        if(sparkle && !this.item.hasEnchantments())
            this.item.setNamedTag((this.item.hasCompoundTag() ? this.item.getNamedTag() : new CompoundTag()).putList(new ListTag<>("ench")));
        return this;
    }

    public ItemBuilder editNamedTag(Consumer<CompoundTag> namedTagConsumer) {
        final CompoundTag namedTag = this.item.hasCompoundTag() ? this.item.getNamedTag() : new CompoundTag();
        namedTagConsumer.accept(namedTag);
        this.item.setNamedTag(namedTag);
        return this;
    }

    public ItemBuilder editCustomBlockData(Consumer<CompoundTag> namedTagConsumer) {
        final CompoundTag compoundTag = this.item.hasCustomBlockData() ? this.item.getCustomBlockData() : new CompoundTag();
        namedTagConsumer.accept(compoundTag);
        this.item.setCustomBlockData(compoundTag);
        return this;
    }

    public ItemBuilder editExtraAttributes(Consumer<CompoundTag> extraAttributesConsumer) {
        return this.editNamedTag(namedTag -> {
            if(namedTag.contains(ItemHelper.EXTRA_ATTRIBUTES) && !(namedTag.get(ItemHelper.EXTRA_ATTRIBUTES) instanceof CompoundTag))
                namedTag.remove(ItemHelper.EXTRA_ATTRIBUTES);
            if(!namedTag.contains(ItemHelper.EXTRA_ATTRIBUTES))
                namedTag.putCompound(ItemHelper.EXTRA_ATTRIBUTES, new CompoundTag());
            extraAttributesConsumer.accept(namedTag.getCompound(ItemHelper.EXTRA_ATTRIBUTES));
        });
    }

    public Item toItem() {
        return this.item;
    }

}

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

package ms.kevi.skyblock.item.custom.potion;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.Tag;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.effect.Effect;
import ms.kevi.skyblock.game.effect.IEffect;
import ms.kevi.skyblock.game.effect.PotionType;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.handler.ItemInteractHandler;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.FunctionInvoker;
import ms.kevi.skyblock.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PotionItem extends AbstractGameItem implements ItemInteractHandler {

    @Override
    public Item createItem(Object... args) {
        final PotionType potion = (PotionType) args[0];
        final int potionLevel = (int) args[1];
        final boolean extended = (boolean) args[2];
        final boolean enhanced = (boolean) args[3];
        final Effect[] effects = (Effect[]) args[4];

        final GameRarity rarity = this.getRarityByLevel(potionLevel);

        final ListTag<Tag> effectsTag = new ListTag<>("effects");

        final List<String> lore = new ArrayList<>();
        lore.add("");
        for(Effect effect : effects) {
            final IEffect type = effect.getType();
            lore.add(type.getColorCode() + type.getDisplayName() + " " + Utils.toRoman(effect.getAmplifier()) + " §r§f(" + Utils.toTimeWithTicks(effect.getDurationTicks()) + ")");
            ItemHelper.addDescription(lore, type.getDescription(effect.getAmplifier()));
            lore.add("");

            effectsTag.add(new CompoundTag().
                    putInt("level", effect.getAmplifier()).
                    putString("effect", effect.getType().name()).
                    putInt("duration_ticks", effect.getDurationTicks()).
                    putCompound("modifiers", new CompoundTag())); // TODO: Implement modifiers
        }

        lore.add(rarity.buildName(true));

        return ItemBuilder.get(this.getId(), 60, 1).
                setCustomName(rarity.getColorCode() + potion.getDisplayName() + " " + Utils.toRoman(potionLevel) + " Potion").
                setLore(lore).
                setSparkle(true).
                editNamedTag(compoundTag -> compoundTag.
                        putCompound(ItemHelper.EXTRA_ATTRIBUTES, new CompoundTag().
                                putString(ItemHelper.ATTRIBUTE_ID, potion.name()).
                                putInt("potion_level", potionLevel).
                                putString("potion", potion.name()).
                                putBoolean("extended", extended).
                                putBoolean("enhanced", enhanced).
                                putList(effectsTag))).
                toItem();
    }

    @Override
    public boolean needsParams() {
        return true;
    }

    @Override
    public List<FunctionInvoker<Item>> possibleInvokers() {
        //TODO: Implement all possible invokers
        return Collections.emptyList();
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if(!player.isUsingItem()) {
            player.setUsingItem(true);
            return;
        }

        event.setCancelled(false);
        player.setUsingItem(false);

        final CompoundTag tag = event.getItem().getNamedTag();
        if(tag.contains(ItemHelper.EXTRA_ATTRIBUTES)) {
            final CompoundTag extra = tag.getCompound(ItemHelper.EXTRA_ATTRIBUTES);

            final ListTag<CompoundTag> effectsTag = extra.getList("effects", CompoundTag.class);

            for(CompoundTag effectTag : effectsTag.getAll()) {
                final int level = effectTag.getInt("level");
                final IEffect effect = Registries.EFFECTS.valueOfNonNull(effectTag.getString("effect"));
                final int durationTicks = effectTag.getInt("duration_ticks");

                GameHolder.getPlayer(player).getEffects().add(effect, durationTicks, level);
            }
        }
    }

    @Override
    public String getDisplayName() {
        return "Empty Potion";
    }

    public GameRarity getRarityByLevel(int level) {
        switch(level) {
            case 1:
            case 2:
                return GameRarity.COMMON;
            case 3:
            case 4:
                return GameRarity.UNCOMMON;
            case 5:
            case 6:
                return GameRarity.RARE;
            case 7:
            case 8:
                return GameRarity.EPIC;
            case 9:
            case 10:
                return GameRarity.LEGENDARY;
            default:
                return GameRarity.MYTHIC;
        }
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.COMMON;
    }

    @Override
    public int getId() {
        return ItemID.POTION;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

}

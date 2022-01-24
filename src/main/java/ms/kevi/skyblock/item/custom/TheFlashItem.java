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

package ms.kevi.skyblock.item.custom;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSkull;
import cn.nukkit.nbt.tag.CompoundTag;
import de.kcodeyt.heads.util.SkullOwner;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.AbilityType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.item.ability.InteractionType;
import ms.kevi.skyblock.item.registry.AbstractGameItem;

public class TheFlashItem extends AbstractGameItem {

    @Override
    public String getDisplayName() {
        return "The Flash";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.SPECIAL;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.HELMET;
    }

    @Override
    public int getId() {
        return Item.SKULL;
    }

    @Override
    public int getDamage() {
        return ItemSkull.HEAD;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }

    @Override
    public CompoundTag getCompoundTag() {
        return new CompoundTag().putCompound("SkullOwner", SkullOwner.buildTag(
                "683d2661-b9c8-428f-a3b0-b4af3a57658d",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTU1Mzg1YTExYmJhMWRhNjJiODZhOTJiM2IyZjA2MThmM2E5NjU5NmZkYWE4OWM0Njg4OTcxYTYyMGJhIn19fQ=="
        ));
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.HEALTH, 60).
                put(GameStats.DEFENSE, 100).
                put(GameStats.SPEED, 3000);
    }

    @Override
    public IAbility getAbility() {
        return new IAbility() {
            @Override
            public String getDisplayName() {
                return "Superhuman Speed";
            }

            @Override
            public String getDescription() {
                return "You are capable of moving at incredible speeds. While running, you can only be perceived as a blur.";
            }

            @Override
            public AbilityType getType() {
                return AbilityType.ITEM;
            }

            @Override
            public InteractionType getInteractionType() {
                return InteractionType.NONE;
            }

            @Override
            public void onTick(Player player, int currentTick) {
                GameHolder.getPlayer(player).getGameAttributes().get(GameStats.SPEED).setForcedMaxValue(player.isSprinting() ? 3000 : 1000);
            }
        };
    }

}

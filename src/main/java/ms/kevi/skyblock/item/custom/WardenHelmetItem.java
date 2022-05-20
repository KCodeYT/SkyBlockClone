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
import lombok.Getter;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.attribute.Attribute;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.game.player.event.Listener;
import ms.kevi.skyblock.game.player.event.UpdateGameAttributesEvent;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.AbilityType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.item.ability.InteractionType;
import ms.kevi.skyblock.item.registry.AbstractGameItem;

public class WardenHelmetItem extends AbstractGameItem {

    @Getter
    private final IAbility ability = new IAbility() {
        private final Listener<UpdateGameAttributesEvent> listener = event -> {
            final SkyBlockPlayer player = event.getPlayer();
            final Attribute speedAttribute = player.getGameAttributes().get(GameStats.SPEED);
            final Attribute damageAttribute = player.getGameAttributes().get(GameStats.DAMAGE);
            for(int s = 25; speedAttribute.getMaxValue() - s > 0; s += 25)
                damageAttribute.setValue(damageAttribute.getValue() + 20);
        };

        @Override
        public String getDisplayName() {
            return "Brute Force";
        }

        @Override
        public String getDescription() {
            return "§7Halves your speed but grants §c+20% §7base weapon damage for every §a25 §7speed";
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
        public void onActivate(Player player) {
            GameHolder.getPlayer(player).getEventManager().on(UpdateGameAttributesEvent.class, this.listener);
        }

        @Override
        public void onDeactivate(Player player) {
            GameHolder.getPlayer(player).getEventManager().remove(this.listener);
        }

        @Override
        public StatsBooster getStatsBooster() {
            return StatsBooster.create().
                    putPercent(GameStats.SPEED, -50);
        }
    };

    @Override
    public String getDisplayName() {
        return "Warden Helmet";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.LEGENDARY;
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
                "4f6eef4a-c9f2-3f84-832b-a94b89a3e167",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVlYjBiZDg1YWFkZGYwZDI5ZWQwODJlYWMwM2ZjYWRlNDNkMGVlODAzYjBlODE2MmFkZDI4YTYzNzlmYjU0ZSJ9fX0="
        ));
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.HEALTH, 300).
                put(GameStats.DEFENSE, 100);
    }

}

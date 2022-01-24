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

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.registry.ItemAbilityRegistry;

public class BonzoStaffItem extends AbstractGameItem {

    @Override
    public String getDisplayName() {
        return "Bonzo's Staff";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.RARE;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SWORD;
    }

    @Override
    public int getId() {
        return Item.BLAZE_ROD;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }

    @Override
    public IAbility getAbility() {
        return ItemAbilityRegistry.SHOWTIME;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.DAMAGE, 160).
                put(GameStats.INTELLIGENCE, 250);
    }

    @Override
    public boolean isFromDungeon() {
        return true;
    }

}

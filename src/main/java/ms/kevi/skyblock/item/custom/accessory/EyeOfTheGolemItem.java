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

package ms.kevi.skyblock.item.custom.accessory;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.IGameItem;

public class EyeOfTheGolemItem extends AbstractAccessoryItem {

    @Override
    public String getDisplayName() {
        return "Eye of the Golem";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.UNCOMMON;
    }

    @Override
    public int getId() {
        return Item.SPIDER_EYE;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.CRITICAL_CHANCE, 2);
    }

    @Override
    public IGameItem[] getFamily() {
        return new IGameItem[]{this};
    }

}

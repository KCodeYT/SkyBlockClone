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

package ms.kevi.skyblock.item.enchantment;

import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;

public class ProtectionEnchantment extends Enchantment {

    ProtectionEnchantment(int level) {
        super(level);
    }

    public String getName() {
        return "Protection";
    }

    public int getMaxLevel() {
        return 5;
    }

    public StatsBooster getStatsBooster() {
        return StatsBooster.create().put(GameStats.DEFENSE, 3 * this.level);
    }

    public String getDescription() {
        return "§7Grants §a+" + (3 * this.level) + " §aDefense§7.";
    }

}
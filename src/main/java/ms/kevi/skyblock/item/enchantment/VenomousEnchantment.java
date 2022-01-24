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

public class VenomousEnchantment extends Enchantment {

    VenomousEnchantment(int level) {
        super(level);
    }

    public String getName() {
        return "Venomous";
    }

    public int getMaxLevel() {
        return 5;
    }

    public StatsBooster getStatsBooster() {
        return StatsBooster.empty();
    }

    public String getDescription() {
        return "Reduces the target's walking speed by 5 per level and deals 5 damage per level every second. This effect lasts for 4 seconds.";
    }

}
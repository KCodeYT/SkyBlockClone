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

package ms.kevi.skyblock.game.effect.defaults;

import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.effect.IEffect;
import ms.kevi.skyblock.game.effect.PotionColor;
import ms.kevi.skyblock.game.stats.GameStats;

public class StrengthEffect implements IEffect {

    public static final int[] VALUES = {5, 12, 20, 30, 40, 50, 60, 75};

    @Override
    public String getDisplayName() {
        return "Strength";
    }

    @Override
    public String getColorCode() {
        return "§4";
    }

    @Override
    public int getMaxLevel() {
        return VALUES.length;
    }

    @Override
    public int getDurationOf(int level, boolean extended) {
        return level + (extended ? 20 : 0);
    }

    @Override
    public int getAmplifierOf(int level, boolean enhanced) {
        return level + (enhanced ? 1 : 0);
    }

    @Override
    public StatsBooster getStatsBooster(int level) {
        return StatsBooster.create().
                put(GameStats.STRENGTH, VALUES[level - 1]);
    }

    @Override
    public String getDescription(int level) {
        return "§7Increases " + GameStats.STRENGTH.toString(true, false) + " by §a" + VALUES[level - 1] + "§7.";
    }

}

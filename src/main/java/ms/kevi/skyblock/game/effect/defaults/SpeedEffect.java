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
import ms.kevi.skyblock.game.stats.GameStats;

public class SpeedEffect implements IEffect {

    @Override
    public String getDisplayName() {
        return "Speed";
    }

    @Override
    public String getColorCode() {
        return "§9";
    }

    @Override
    public int getMaxLevel() {
        return 8;
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
                put(GameStats.SPEED, 5 * level);
    }

    @Override
    public String getDescription(int level) {
        return "§7Grants §a+" + (5 * level) + " " + GameStats.SPEED.toString(true, false) + "§7.";
    }

}

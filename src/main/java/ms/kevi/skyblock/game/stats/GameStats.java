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

package ms.kevi.skyblock.game.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ms.kevi.skyblock.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum GameStats {

    HEALTH("Health", " HP", "§c", 100, -1, false, StatsType.SELF, 0),
    DEFENSE("Defense", "", "§a", 0, -1, true, StatsType.SELF, 1),
    STRENGTH("Strength", "", "§c", 0, -1, true, StatsType.DAMAGE, 1),
    SPEED("Speed", "", "§f", 100, 400, true, StatsType.SELF, 2),
    CRITICAL_CHANCE("Crit Chance", "%", "§9", 20, 100, true, StatsType.DAMAGE, 2),
    CRITICAL_DAMAGE("Crit Damage", "%", "§9", 50, -1, true, StatsType.DAMAGE, 3),
    ATTACK_SPEED("Bonus Attack Speed", "%", "§e", 50, -1, true, StatsType.DAMAGE, 4),
    INTELLIGENCE("Intelligence", "", "§b", 100, -1, false, StatsType.SELF, 3),
    DAMAGE("Damage", "", "§c", 100, -1, true, StatsType.DAMAGE, 0);

    private final String displayName;
    private final String suffix;
    private final String colorCode;
    private final int baseValue;
    private final int maxValue;
    private final boolean iStatic;
    private final StatsType statsType;
    private final int typeIndex;

    public static GameStats[] sortValues() {
        final Map<GameStats, Integer> gameStatsMap = new HashMap<>();
        final GameStats[] values = values();
        final int length = values.length;
        for(GameStats gameStats : values)
            gameStatsMap.put(gameStats, gameStats.statsType.ordinal() * length + gameStats.typeIndex);
        return MapUtil.sortByValue(gameStatsMap).keySet().toArray(new GameStats[0]);
    }

    @Getter
    @AllArgsConstructor
    public enum StatsType {
        DAMAGE("§c"),
        SELF("§a");
        private final String colorCode;
    }

}

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

package ms.kevi.skyblock.game.modifier;

import lombok.Getter;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.IGameItem;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RenownedModifier implements IModifier {

    private static final Map<GameStats, int[]> MAPPING = new HashMap<>();

    static {
        MAPPING.put(GameStats.STRENGTH, new int[]{10, 12});
        MAPPING.put(GameStats.CRITICAL_CHANCE, new int[]{10, 12});
        MAPPING.put(GameStats.CRITICAL_DAMAGE, new int[]{10, 12});
        MAPPING.put(GameStats.ATTACK_SPEED, new int[]{4, 5});
        MAPPING.put(GameStats.HEALTH, new int[]{8, 10});
        MAPPING.put(GameStats.DEFENSE, new int[]{8, 10});
        MAPPING.put(GameStats.INTELLIGENCE, new int[]{10, 12});
    }

    private final String displayName = "Renowned";
    private final boolean fromStone = true;
    private final ModifierBonus modifierBonus = new ModifierBonus("§7Increases most stats by §a+1%§7.", StatsBooster.create().putPercent(GameStats.values(), 1));

    @Override
    public boolean isApplicable(IGameItem gameItem) {
        return gameItem.getItemType() != null && gameItem.getItemType().isArmor();
    }

    @Override
    public StatsBooster getStatsBooster(GameRarity rarity) {
        final StatsBooster booster = StatsBooster.create();
        MAPPING.forEach((gameStats, values) -> booster.put(gameStats, values[Math.min(rarity.getId(), GameRarity.LEGENDARY.getId()) % values.length]));
        return booster.put(GameStats.SPEED, 1);
    }

    @Override
    public long getApplicationCost(GameRarity gameRarity) {
        return 1000000;
    }

}

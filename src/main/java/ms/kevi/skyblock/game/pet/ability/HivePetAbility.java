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

package ms.kevi.skyblock.game.pet.ability;

import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.pet.IPetAbility;
import ms.kevi.skyblock.game.pet.PetData;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.util.Utils;

public class HivePetAbility implements IPetAbility {

    @Override
    public String getDisplayName() {
        return "Hive";
    }

    @Override
    public String getDescription(PetData petData) {
        final StatsBooster statsBooster = this.getStatsBooster(petData);
        return "§7Gain " +
                GameStats.INTELLIGENCE.getColorCode() +
                Utils.formatNumber(statsBooster.get(GameStats.INTELLIGENCE), true) +
                " " + GameStats.INTELLIGENCE.getDisplayName() + "§r§7 and " +
                GameStats.STRENGTH.getColorCode() +
                Utils.formatNumber(statsBooster.get(GameStats.STRENGTH), true) +
                " " + GameStats.STRENGTH.getDisplayName() + " §r§7for each nearby bee.";
    }

    @Override
    public StatsBooster getStatsBooster(PetData petData) {
        final int level = petData.getLevel();
        final double iPerL;
        final double sPerL;
        switch(petData.getRarity()) {
            case COMMON:
                iPerL = 0.02;
                sPerL = 0.02;
                break;
            case UNCOMMON:
                iPerL = 0.04;
                sPerL = 0.04;
                break;
            case RARE:
                iPerL = 0.09;
                sPerL = 0.07;
                break;
            case EPIC:
                iPerL = 0.14;
                sPerL = 0.11;
                break;
            default:
                iPerL = 0.19;
                sPerL = 0.14;
                break;
        }

        return StatsBooster.create().
                put(GameStats.INTELLIGENCE, 1 + (int) (Math.floor(((double) level * iPerL) * 10) / 10D)).
                put(GameStats.STRENGTH, 1 + (int) (Math.floor(((double) level * sPerL) * 10) / 10D));
    }

}

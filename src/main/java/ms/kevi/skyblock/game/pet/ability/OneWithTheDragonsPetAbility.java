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

public class OneWithTheDragonsPetAbility implements IPetAbility {

    @Override
    public String getDisplayName() {
        return "One with the Dragons";
    }

    @Override
    public String getDescription(PetData petData) {
        return "§7Buffs the Aspect of the Dragons sword by §a" +
                Utils.roundNumber(petData.getLevel() * 0.5, 1) + " " +
                GameStats.DAMAGE.getStatsType().getColorCode() + GameStats.DAMAGE.getDisplayName() + "§r§7 and §a" +
                Utils.roundNumber(petData.getLevel() * 0.3, 1) + " " +
                GameStats.STRENGTH.getStatsType().getColorCode() + GameStats.STRENGTH.getDisplayName();
    }

    @Override
    public StatsBooster getStatsBooster(PetData petData) {
        return StatsBooster.empty();
    }

}

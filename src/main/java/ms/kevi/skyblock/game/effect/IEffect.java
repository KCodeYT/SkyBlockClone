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

package ms.kevi.skyblock.game.effect;

import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.registry.Registrable;
import ms.kevi.skyblock.registry.Registries;

public interface IEffect extends Registrable {

    String getDisplayName();

    String getColorCode();

    int getMaxLevel();

    int getDurationOf(int level, boolean extended);

    int getAmplifierOf(int level, boolean enhanced);

    StatsBooster getStatsBooster(int level);

    String getDescription(int level);

    @Override
    default String name() {
        return Registries.EFFECTS.nameOf(this);
    }

}

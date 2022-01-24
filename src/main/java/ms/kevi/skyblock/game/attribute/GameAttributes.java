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

package ms.kevi.skyblock.game.attribute;

import ms.kevi.skyblock.game.stats.GameStats;

import java.util.*;

public class GameAttributes {

    private final Map<GameStats, Attribute> attributes;

    public GameAttributes() {
        final Map<GameStats, Attribute> map = new HashMap<>();
        for(GameStats gameStats : GameStats.values())
            map.put(gameStats, new Attribute(gameStats));
        this.attributes = Collections.unmodifiableMap(map);
    }

    public void reset(boolean onlyMax) {
        for(Attribute attribute : this.attributes.values())
            attribute.reset(onlyMax);
    }

    public Attribute get(GameStats gameStats) {
        return this.attributes.get(gameStats);
    }

    public void applyRegenerations(List<Regeneration> regenerations) {
        final Map<GameStats, Double> regenerationsMap = new HashMap<>();
        final List<Regeneration> lastRegenerations = new ArrayList<>();
        for(Regeneration regeneration : regenerations) {
            if(regeneration.getPercent() >= 100) {
                lastRegenerations.add(regeneration);
                continue;
            }

            final GameStats gameStats = regeneration.getGameStats();
            regenerationsMap.putIfAbsent(gameStats, 0D);
            regenerationsMap.put(gameStats, regenerationsMap.get(gameStats) + regeneration.getPercent());
            regeneration.apply(this);
        }

        for(Regeneration regeneration : lastRegenerations)
            regeneration.apply(this, regenerationsMap.getOrDefault(regeneration.getGameStats(), 0D));
    }

}

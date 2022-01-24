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

package ms.kevi.skyblock.game.booster;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BoosterHolder {

    private final Map<BoosterSlot, Set<StatsBooster>> boosters;

    public BoosterHolder() {
        this.boosters = new LinkedHashMap<>();
        for(BoosterSlot BoosterSlot : BoosterSlot.values())
            this.boosters.put(BoosterSlot, new HashSet<>());
    }

    public StatsBooster currentBooster() {
        final Set<StatsBooster> statsBoosters = new HashSet<>();
        for(Set<StatsBooster> boosters : this.boosters.values())
            statsBoosters.addAll(boosters);
        return StatsBooster.combine(statsBoosters);
    }

    public Map<BoosterSlot, Set<StatsBooster>> getBoosterSnapshot() {
        return this.boosters;
    }

    public boolean hasBooster(BoosterSlot slot) {
        return !this.boosters.get(slot).isEmpty();
    }

    public StatsBooster getBooster(BoosterSlot slot) {
        final Set<StatsBooster> boosters = this.boosters.get(slot);
        if(boosters.size() == 0)
            boosters.add(StatsBooster.empty());
        return boosters.iterator().next();
    }

    public void setBooster(BoosterSlot slot, StatsBooster statsBooster) {
        this.clearBooster(slot).add(statsBooster);
    }

    public void addBooster(BoosterSlot slot, StatsBooster statsBooster) {
        this.boosters.get(slot).add(statsBooster);
    }

    private Set<StatsBooster> clearBooster(BoosterSlot slot) {
        final Set<StatsBooster> boosters = this.boosters.get(slot);
        boosters.clear();
        return boosters;
    }

    public void clear() {
        for(Set<StatsBooster> set : this.boosters.values())
            set.clear();
    }

}

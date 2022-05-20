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

import lombok.Getter;
import ms.kevi.skyblock.game.booster.StatsBooster;

import java.util.*;
import java.util.function.Predicate;

public class Effects {

    @Getter
    private final List<Effect> active = new ArrayList<>();

    public void tick() {
        final Iterator<Effect> iterator = this.active.iterator();
        while(iterator.hasNext()) {
            final Effect effect = iterator.next();
            final int currentDuration = effect.getDurationTicks();

            if(currentDuration > 0)
                effect.setDurationTicks(currentDuration - 1);
            else iterator.remove();
        }
    }

    public StatsBooster apply() {
        final Set<StatsBooster> boosters = new LinkedHashSet<>();
        for(Effect effect : this.active)
            boosters.add(effect.getType().getStatsBooster(effect.getAmplifier()));

        return StatsBooster.combine(boosters);
    }

    public int size() {
        return this.active.size();
    }

    public void add(IEffect type, int durationTicks, int amplifier) {
        if(this.has(type) && !this.removeIf(type, effect -> effect.getAmplifier() <= amplifier)) return;

        this.active.add(new Effect(type, durationTicks, amplifier));
    }

    public boolean has(IEffect type) {
        for(Effect effect : this.active)
            if(effect.getType() == type) return true;
        return false;
    }

    public boolean removeIf(IEffect type, Predicate<Effect> predicate) {
        return this.active.removeIf(effect -> effect.getType() == type && predicate.test(effect));
    }

    public void remove(IEffect type) {
        this.active.removeIf(effect -> effect.getType() == type);
    }

    public void removeAll() {
        this.active.clear();
    }

}

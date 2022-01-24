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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ms.kevi.skyblock.game.attribute.Regeneration;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.game.stats.GameStats;

import java.util.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsBooster {

    private static final StatsBooster EMPTY = new StatsBooster(Collections.emptyMap(), Collections.emptyMap(), Collections.emptyList());
    @Getter
    private final Map<GameStats, Integer> gameStatsMap;
    @Getter
    private final Map<GameStats, Double> gameStatsPercentMap;
    private final List<Regeneration> regenerations;

    public static StatsBooster empty() {
        return EMPTY;
    }

    public static StatsBooster defaultOf(SkyBlockPlayer player) {
        final StatsBooster booster = create();
        for(GameStats stats : GameStats.values())
            booster.put(stats, stats.getBaseValue());
        booster.putRegeneration(GameStats.HEALTH, 3);
        booster.putRegeneration(GameStats.INTELLIGENCE, 2);
        return booster;
    }

    public static StatsBooster create() {
        return new StatsBooster(new LinkedHashMap<>(), new LinkedHashMap<>(), new ArrayList<>());
    }

    public static StatsBooster combine(StatsBooster holder1, StatsBooster holder2) {
        final StatsBooster statsBooster = create();
        holder1.gameStatsMap.forEach(statsBooster::put);
        holder2.gameStatsMap.forEach(statsBooster::put);
        holder1.gameStatsPercentMap.forEach(statsBooster::putPercent);
        holder2.gameStatsPercentMap.forEach(statsBooster::putPercent);
        holder1.regenerations.forEach(regeneration -> statsBooster.putRegeneration(regeneration.getGameStats(), regeneration.getPercent()));
        holder2.regenerations.forEach(regeneration -> statsBooster.putRegeneration(regeneration.getGameStats(), regeneration.getPercent()));
        return statsBooster;
    }

    public static StatsBooster combine(Collection<Set<StatsBooster>> boosters) {
        if(boosters.isEmpty())
            return StatsBooster.empty();
        final StatsBooster statsBooster = create();
        for(Set<StatsBooster> boosters0 : boosters) {
            final StatsBooster booster = StatsBooster.combine(boosters0);
            booster.gameStatsMap.forEach(statsBooster::put);
            booster.gameStatsPercentMap.forEach(statsBooster::putPercent);
            booster.regenerations.forEach(regeneration -> statsBooster.putRegeneration(regeneration.getGameStats(), regeneration.getPercent()));
        }
        return statsBooster;
    }

    public static StatsBooster combine(Set<StatsBooster> boosters) {
        if(boosters.isEmpty())
            return StatsBooster.empty();
        final StatsBooster statsBooster = create();
        for(StatsBooster booster : boosters) {
            booster.gameStatsMap.forEach(statsBooster::put);
            booster.gameStatsPercentMap.forEach(statsBooster::putPercent);
            booster.regenerations.forEach(regeneration -> statsBooster.putRegeneration(regeneration.getGameStats(), regeneration.getPercent()));
        }
        return statsBooster;
    }

    public boolean isEmpty() {
        return this == EMPTY || this.gameStatsMap.isEmpty() && this.gameStatsPercentMap.isEmpty() && this.regenerations.isEmpty();
    }

    public StatsBooster put(GameStats[] gameStatsArray, int value) {
        for(GameStats gameStats : gameStatsArray)
            this.put(gameStats, value);
        return this;
    }

    public StatsBooster put(GameStats gameStats, int value) {
        if(!this.gameStatsMap.containsKey(gameStats))
            this.gameStatsMap.put(gameStats, value);
        else
            this.gameStatsMap.put(gameStats, this.gameStatsMap.get(gameStats) + value);
        return this;
    }

    public StatsBooster putPercent(GameStats[] gameStatsArray, double value) {
        for(GameStats gameStats : gameStatsArray)
            this.putPercent(gameStats, value);
        return this;
    }

    public StatsBooster putPercent(GameStats gameStats, double value) {
        if(!this.gameStatsPercentMap.containsKey(gameStats))
            this.gameStatsPercentMap.put(gameStats, value);
        else
            this.gameStatsPercentMap.put(gameStats, this.gameStatsPercentMap.get(gameStats) + value);
        return this;
    }

    public StatsBooster putRegeneration(GameStats gameStats, double percent) {
        final Optional<Regeneration> optionalRegeneration = this.regenerations.stream().filter(regeneration -> regeneration.getGameStats().equals(gameStats)).findAny();
        if(optionalRegeneration.isPresent()) {
            final Regeneration regeneration = optionalRegeneration.get();
            this.regenerations.remove(regeneration);
            this.regenerations.add(new Regeneration(gameStats, regeneration.getPercent() + percent));
        } else
            this.regenerations.add(new Regeneration(gameStats, percent));
        return this;
    }

    public List<Regeneration> getRegenerations() {
        return Collections.unmodifiableList(this.regenerations);
    }

    public Double getPercent(GameStats gameStats) {
        return this.gameStatsPercentMap.get(gameStats);
    }

    public Set<GameStats> getPercentKeySet() {
        return Collections.unmodifiableSet(this.gameStatsPercentMap.keySet());
    }

    public Integer get(GameStats gameStats) {
        return this.gameStatsMap.get(gameStats);
    }

    public Set<GameStats> getKeySet() {
        return Collections.unmodifiableSet(this.gameStatsMap.keySet());
    }

    @Override
    public int hashCode() {
        return this.gameStatsMap.hashCode() |
                this.gameStatsPercentMap.hashCode() |
                this.regenerations.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StatsBooster &&
                ((StatsBooster) obj).gameStatsMap.equals(this.gameStatsMap) &&
                ((StatsBooster) obj).gameStatsPercentMap.equals(this.gameStatsPercentMap) &&
                ((StatsBooster) obj).regenerations.equals(this.regenerations);
    }

}

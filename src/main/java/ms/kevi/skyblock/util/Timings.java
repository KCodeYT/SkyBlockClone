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

package ms.kevi.skyblock.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Timings {

    private static final Map<String, List<Long>> TIMINGS = new HashMap<>();
    private static final List<Integer> ENTITY_COUNT = new ArrayList<>();

    public static void addEntityCount(int entityCount) {
        synchronized(ENTITY_COUNT) {
            ENTITY_COUNT.add(entityCount);
        }
    }

    public static int getLastEntityCount() {
        synchronized(ENTITY_COUNT) {
            return ENTITY_COUNT.isEmpty() ? 0 : ENTITY_COUNT.get(ENTITY_COUNT.size() - 1);
        }
    }

    public static double getAverageEntityCount() {
        synchronized(ENTITY_COUNT) {
            if(ENTITY_COUNT.size() > 0) {
                long averageTiming = 0;
                for(int entityCount : ENTITY_COUNT)
                    averageTiming += entityCount;
                return Math.round(((double) averageTiming / ENTITY_COUNT.size()) * 100D) / 100D;
            }
            return 0;
        }
    }

    public static void addTimingSpec(String specName) {
        synchronized(TIMINGS) {
            TIMINGS.putIfAbsent(specName, new ArrayList<>());
        }
    }

    public static void addTiming(String specName, Long timeTook) {
        addTimingSpec(specName);
        synchronized(TIMINGS) {
            TIMINGS.get(specName).add(timeTook);
        }
    }

    public static long getTiming(String specName) {
        addTimingSpec(specName);
        synchronized(TIMINGS) {
            final List<Long> timings = TIMINGS.get(specName);
            if(!timings.isEmpty())
                return timings.get(timings.size() - 1);
            return 0;
        }
    }

    public static double getTimingAverage(String specName) {
        addTimingSpec(specName);
        synchronized(TIMINGS) {
            final List<Long> timings = TIMINGS.get(specName);
            if(!timings.isEmpty()) {
                long averageTiming = 0;
                for(long timing : timings)
                    averageTiming += timing;
                return Math.round(((double) averageTiming / timings.size()) * 100D) / 100D;
            }
            return 0;
        }
    }

    public static String[] getTimingSpecs() {
        synchronized(TIMINGS) {
            return TIMINGS.keySet().toArray(new String[0]);
        }
    }

}

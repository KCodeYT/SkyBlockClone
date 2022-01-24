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

import java.util.HashMap;
import java.util.Map;

public class Language {

    private static final Map<String, String> LANGUAGE_MAP = new HashMap<>();

    public static void init() {
        LANGUAGE_MAP.put("MINION_DESCRIPTION_START", "Place this minion and it will start");
        LANGUAGE_MAP.put("MINION_DESCRIPTION_END", "Minions also work when you are offline!");
        LANGUAGE_MAP.put("MINION_TYPE_COAL_INNER", "generating and mining coal ore! Requires an open area to place coal ore.");
        LANGUAGE_MAP.put("MINION_TYPE_COBBLESTONE_INNER", "generating and mining cobblestone! Requires an open area to place cobblestone.");
        LANGUAGE_MAP.put("MINION_TYPE_DIAMOND_INNER", "generating and mining diamond ore! Requires an open area to place diamond ore.");
        LANGUAGE_MAP.put("MINION_TYPE_EMPTY_INNER", "making nothing! Requires nothing.");
        LANGUAGE_MAP.put("MINION_TYPE_GOLD_INNER", "generating and mining gold ore! Requires an open area to place gold ore.");
        LANGUAGE_MAP.put("MINION_TYPE_IRON_INNER", "generating and mining iron ore! Requires an open area to place iron ore.");
        LANGUAGE_MAP.put("MINION_TYPE_LAPIS_INNER", "generating and mining lapis ore! Requires an open area to place lapis ore.");
        LANGUAGE_MAP.put("MINION_TYPE_REDSTONE_INNER", "generating and mining redstone ore! Requires an open area to place redstone ore.");
        LANGUAGE_MAP.put("MINION_TYPE_WHEAT_INNER", "generating and harvesting wheat! Requires dirt or soil nearby so wheat can be planted.");
        LANGUAGE_MAP.put("MINION_TYPE_OAK_INNER", "generating and chopping oak logs! Requires an open area to place trees.");

        LANGUAGE_MAP.put("LOCATION_NOT_PERFECT", "§cThis location isn't perfect! :(");
        LANGUAGE_MAP.put("MINION_NEED_SPACE", "§cI need space to generate blocks");

        LANGUAGE_MAP.put("NPC_WAIT_LITTLE_BIT", "§cWait a little bit before doing that again!");
    }

    public static String get(String key) {
        if(LANGUAGE_MAP.isEmpty())
            Language.init();
        return LANGUAGE_MAP.getOrDefault(key, "N/A");
    }

}

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

package ms.kevi.skyblock.game.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServerType {

    DYNAMIC("Private Island"),
    HUB("Hub"),
    FORAGING_1("The Park"),
    FARMING_1("The Barn"),
    FARMING_2("Mushroom Desert"),
    MINING_1("Gold Mine"),
    MINING_2("Deep Caverns"),
    MINING_3("Dwarven Mines"),
    COMBAT_1("Spider's Den"),
    COMBAT_2("Blazing Fortress"),
    COMBAT_3("The End"),
    NONE("Unknown");

    private final String displayName;

}

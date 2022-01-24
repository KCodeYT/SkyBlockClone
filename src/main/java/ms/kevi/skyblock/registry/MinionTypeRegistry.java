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

package ms.kevi.skyblock.registry;

import ms.kevi.skyblock.game.minion.IMinionType;
import ms.kevi.skyblock.game.minion.type.*;

public class MinionTypeRegistry extends Registry<IMinionType> {

    public static final IMinionType WHEAT = new WheatType();
    public static final IMinionType DIAMOND = new DiamondType();
    public static final IMinionType GOLD = new GoldType();
    public static final IMinionType IRON = new IronType();
    public static final IMinionType LAPIS = new LapisType();
    public static final IMinionType REDSTONE = new RedstoneType();
    public static final IMinionType COAL = new CoalType();
    public static final IMinionType COBBLESTONE = new CobblestoneType();
    public static final IMinionType OAK = new OakType();

}

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

package ms.kevi.skyblock.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemType {

    HELMET(true, true),
    CHESTPLATE(true, true),
    LEGGINGS(true, true),
    BOOTS(true, true),
    SWORD(false, false),
    BOW(false, false),
    PICKAXE(false, false),
    AXE(false, false),
    SHOVEL(false, false),
    HOE(false, false),
    FISHING_ROD(false, false),
    SHEARS(false, false),
    ACCESSORY(false, true),
    PET_ITEM(false, false),
    REFORGE_STONE(false, false);

    private final boolean armor;
    private final boolean wearable;

}

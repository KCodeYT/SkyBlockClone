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

package ms.kevi.skyblock.game.player;

import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.game.booster.BoosterSlot;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum AbilitySlot {

    ITEM(PlayerInventory::getItemInHand, BoosterSlot.HAND),

    FULL_SET(inventory -> Item.get(0, 0, 0), BoosterSlot.FULL_SET_BONUS),

    HELMET(PlayerInventory::getHelmet, BoosterSlot.HELMET),
    HELMET_SPECIAL(PlayerInventory::getHelmet, BoosterSlot.HELMET),

    CHESTPLATE(PlayerInventory::getChestplate, BoosterSlot.CHESTPLATE),
    CHESTPLATE_SPECIAL(PlayerInventory::getChestplate, BoosterSlot.CHESTPLATE),

    LEGGINGS(PlayerInventory::getLeggings, BoosterSlot.LEGGINGS),
    LEGGINGS_SPECIAL(PlayerInventory::getLeggings, BoosterSlot.LEGGINGS),

    BOOTS(PlayerInventory::getBoots, BoosterSlot.BOOTS),
    BOOTS_SPECIAL(PlayerInventory::getBoots, BoosterSlot.BOOTS);

    private final Function<PlayerInventory, Item> selector;
    private final BoosterSlot boosterSlot;

}

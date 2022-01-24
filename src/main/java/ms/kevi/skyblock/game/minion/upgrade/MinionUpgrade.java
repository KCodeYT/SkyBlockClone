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

package ms.kevi.skyblock.game.minion.upgrade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.registry.Registries;

@Getter
@RequiredArgsConstructor
public enum MinionUpgrade {

    LAVA_BUCKET(UpgradeType.TIME),
    COAL(UpgradeType.TIME),
    ENCHANTED_COAL(UpgradeType.TIME),
    ENCHANTED_CHAR_COAL(UpgradeType.TIME),
    CATALYST(UpgradeType.TIME),

    BUDGET_HOPPER(UpgradeType.AUTOMATIC_SHIPPING),
    ENCHANTED_HOPPER(UpgradeType.AUTOMATIC_SHIPPING),

    DIAMOND_SPREADING(UpgradeType.INVENTORY),

    FLINT_SHOVEL(UpgradeType.INVENTORY),

    COMPACTOR(UpgradeType.INVENTORY),
    SUPER_COMPACTOR_3000(UpgradeType.INVENTORY);

    private final UpgradeType upgradeType;

    public IGameItem getItem() {
        return Registries.ITEMS.valueOfNonNull(this.name());
    }

    public enum UpgradeType {
        TIME,
        AUTOMATIC_SHIPPING,
        INVENTORY
    }

    public static boolean isMinionUpgrade(IGameItem gameItem) {
        for(MinionUpgrade upgrade : values())
            if(upgrade.name().equalsIgnoreCase(gameItem.getAttributeId()))
                return true;
        return false;
    }

}

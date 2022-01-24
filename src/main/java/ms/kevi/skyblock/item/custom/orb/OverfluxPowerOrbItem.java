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

package ms.kevi.skyblock.item.custom.orb;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.registry.OrbTypeRegistry;

public class OverfluxPowerOrbItem extends AbstractPowerOrbItem {

    public OverfluxPowerOrbItem() {
        super(OrbTypeRegistry.OVERFLUX);
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.EPIC;
    }

    @Override
    public int getId() {
        return Item.REDSTONE_DUST;
    }

    @Override
    public int getDamage() {
        return 0;
    }

}
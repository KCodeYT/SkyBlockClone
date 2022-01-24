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

package ms.kevi.skyblock.item.custom.reforge;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.registry.ModifierRegistry;

public class DragonHornItem extends AbstractReforgeStoneItem {

    @Override
    public String getDisplayName() {
        return "Dragon Horn";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.EPIC;
    }

    @Override
    public int getId() {
        return Item.GOLD_INGOT;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public String getRequirement() {
        return "Mining Skill Level XXV";
    }

    @Override
    public String requiredItemType() {
        return "armor";
    }

    @Override
    public IModifier getReforge() {
        return ModifierRegistry.RENOWNED;
    }

}

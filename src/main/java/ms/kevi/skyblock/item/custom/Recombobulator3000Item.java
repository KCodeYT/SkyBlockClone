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

package ms.kevi.skyblock.item.custom;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.item.registry.AbstractGameItem;

public class Recombobulator3000Item extends AbstractGameItem {

    @Override
    public String getDisplayName() {
        return "Recombobulator 3000";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.LEGENDARY;
    }

    @Override
    public int getId() {
        return Item.GOLD_NUGGET;
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
    public String getDescription() {
        return "§7Use in a §aReforge Anvil §7or at the Dungeon Blacksmith to permanently increase the rarity of an item. An item's rarity can only be upgraded once!";
    }

    @Override
    public boolean isFromDungeon() {
        return true;
    }

}

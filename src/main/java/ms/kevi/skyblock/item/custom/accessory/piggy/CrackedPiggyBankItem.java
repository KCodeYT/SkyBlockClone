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

package ms.kevi.skyblock.item.custom.accessory.piggy;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.custom.accessory.AbstractAccessoryItem;
import ms.kevi.skyblock.item.custom.accessory.AccessoryFamily;

public class CrackedPiggyBankItem extends AbstractAccessoryItem {

    public String getDisplayName() {
        return "Cracked Piggy Bank";
    }

    public GameRarity getRarity() {
        return GameRarity.UNCOMMON;
    }

    public int getId() {
        return Item.RAW_PORKCHOP;
    }

    public int getDamage() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "§7Saves §c75% §7of your coins on death. Only when in player inventory. §cVery fragile!";
    }

    public String[] getLoreSuffix() {
        return new String[]{"§8Triggers when losing 20k+ coins."};
    }

    public IGameItem[] getFamily() {
        return AccessoryFamily.PIGGY_BANK;
    }

}

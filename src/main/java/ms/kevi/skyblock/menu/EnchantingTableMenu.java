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

package ms.kevi.skyblock.menu;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.DyeColor;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;

public class EnchantingTableMenu extends FakeDoubleChestMenu {

    private static final Item YELLOW_GLASS_PANE = ItemBuilder.
            get(Item.STAINED_GLASS_PANE, DyeColor.YELLOW.getWoolData(), 1).
            setCustomName(" ").
            toItem();
    private static final Item RED_GLASS_PANE = ItemBuilder.
            get(Item.STAINED_GLASS_PANE, DyeColor.RED.getWoolData(), 1).
            setCustomName(" ").
            toItem();
    private static final Item GRAY_GLASS_PANE = ItemBuilder.
            get(Item.STAINED_GLASS_PANE, DyeColor.RED.getWoolData(), 1).
            setCustomName(" ").
            toItem();

    public EnchantingTableMenu(Player player) {
        super(player, "Enchant Item");
        this.setItem(49, CLOSE_BARRIER, CLOSING);
    }

}

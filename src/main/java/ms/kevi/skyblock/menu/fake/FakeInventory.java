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

package ms.kevi.skyblock.menu.fake;

import cn.nukkit.block.Block;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.DyeColor;

public interface FakeInventory extends Inventory {

    Item EMPTY_GRAY_GLASS_PANE = Item.get(Block.STAINED_GLASS_PANE, DyeColor.GRAY.getWoolData()).setCustomName(" ");
    Item EMPTY_RED_GLASS_PANE = Item.get(Item.STAINED_GLASS_PANE, DyeColor.RED.getWoolData()).setCustomName(" ");
    Item EMPTY_GREEN_GLASS_PANE = Item.get(Block.STAINED_GLASS_PANE, DyeColor.GREEN.getWoolData()).setCustomName(" ");

    Item CLOSE_BARRIER = new Item(-161).setCustomName("§r§cClose");

    FakeTransactionListener CANCELLED = FakeTransactionEvent::setCancelled;
    FakeTransactionListener CLOSING = CANCELLED.andThen(event -> {
        if(event.getInventory() != null) event.getInventory().close(event.getPlayer());
    });

}

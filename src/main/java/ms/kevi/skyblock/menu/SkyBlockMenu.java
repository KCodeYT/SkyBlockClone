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
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.menu.crafting.CraftingMenu;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;
import ms.kevi.skyblock.menu.reforge.AdvancedReforgeMenu;

public class SkyBlockMenu extends FakeDoubleChestMenu {

    private static final Item CRAFTING_TABLE = Item.get(Item.CRAFTING_TABLE).setCustomName("§r§aCrafting Table").setLore("§r§7Opens the crafting grid.", " ", "§r§eClick to open!");

    public SkyBlockMenu(Player player) {
        super(player, "SkyBlock Menu");
        this.disablePlayerInventory();
        this.fillItems();
        this.fillEmpty();
    }

    private void fillItems() {
        final Player player = this.getPlayer();
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
        this.setItem(30, PetsMenu.getMenuItem(skyBlockPlayer.getPets().getActive().getPetData(), true), this.cancelAndOpen(() -> new PetsMenu(player)));
        this.setItem(31, CRAFTING_TABLE, this.cancelAndOpen(() -> new CraftingMenu(player)));
        this.setItem(32, Item.get(Item.ANVIL), this.cancelAndOpen(() -> new AdvancedReforgeMenu(player)));
        this.setItem(23, Item.get(Item.ANVIL), this.cancelAndOpen(() -> new AdvancedReforgeMenu(player, true)));
        this.setItem(53, Item.get(Item.CHEST), this.cancelAndOpen(() -> new AdvancedReforgeMenu(player, true)));
    }

    private void fillEmpty() {
        for(int i = 0; i < this.getSize(); i++)
            if(this.getItem(i).isNull())
                this.setItem(i, EMPTY_GRAY_GLASS_PANE, CANCELLED);
    }

}

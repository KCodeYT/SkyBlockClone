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

import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.handler.ItemInteractHandler;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.menu.SkyBlockMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuItem extends AbstractGameItem implements ItemInteractHandler {

    @Override
    public Item createItem(Object... args) {
        final List<String> lore = new ArrayList<>();
        ItemHelper.addDescription(lore, "§7View all of your SkyBlock progress, including your Skills, Collections, Recipes, and more!");
        lore.add("§r");
        lore.add("§r§eClick to open!");
        return ItemBuilder.get(this.getId(), this.getDamage(), 1).
                setCustomName(this.getDisplayName()).
                setLore(lore).
                editExtraAttributes(extraAttributes -> extraAttributes.putString(ItemHelper.ATTRIBUTE_ID, this.getAttributeId())).toItem();
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        new SkyBlockMenu(event.getPlayer());
    }

    @Override
    public String getDisplayName() {
        return "§aSkyBlock Menu";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.COMMON;
    }

    @Override
    public int getId() {
        return Item.NETHER_STAR;
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
    public boolean isUpgradable() {
        return false;
    }

}

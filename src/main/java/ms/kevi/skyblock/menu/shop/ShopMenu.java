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

package ms.kevi.skyblock.menu.shop;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import lombok.Getter;
import ms.kevi.skyblock.entity.npc.EntityNPC;
import ms.kevi.skyblock.item.ItemHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ShopMenu {

    private final String title;
    private final EntityNPC holder;
    private final List<ShopItem> items;
    private final boolean canSell;

    public ShopMenu(String title, EntityNPC entityNPC, boolean canSell) {
        this.title = title;
        this.holder = entityNPC;
        this.items = new ArrayList<>();
        this.canSell = canSell;
    }

    public void addItem(long buyPrice, long sellPrice, Item item, boolean canBeStacked) {
        this.items.add(new ShopItem(ItemHelper.getAttributeId(item), buyPrice, sellPrice, item, canBeStacked));
    }

    Optional<ShopItem> searchItem(String attributeId) {
        return this.items.stream().filter(shopItem -> shopItem.getIdentifier().equalsIgnoreCase(attributeId)).findAny();
    }

    public boolean open(Player player) {
        new ShopInventory(this, player);
        return true;
    }

}

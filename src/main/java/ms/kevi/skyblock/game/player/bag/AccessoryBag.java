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

package ms.kevi.skyblock.game.player.bag;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.custom.accessory.AbstractAccessoryItem;
import ms.kevi.skyblock.registry.Registries;

import java.util.*;
import java.util.function.Function;

public class AccessoryBag {

    private final List<Item> list = new ArrayList<>();

    public List<Item> filterHighest() {
        return AccessoryBag.filterHighest(this.list);
    }

    public void add(Item item) {
        this.list.add(item);
    }

    public void remove(Item item) {
        this.list.remove(item);
    }

    public static List<Item> filterHighest(Collection<Item> items) {
        final Map<Item, IGameItem[]> familyMap = new LinkedHashMap<>();
        for(Item item : items) {
            if(ItemHelper.isUnknown(item))
                continue;
            final IGameItem gameItem = Registries.ITEMS.valueOfNonNull(ItemHelper.getAttributeId(item));
            if(gameItem instanceof AbstractAccessoryItem)
                familyMap.put(item, ((AbstractAccessoryItem) gameItem).getFamily());
        }

        final Function<IGameItem, Item> searchFunction = gameItem -> {
            for(Item item : familyMap.keySet())
                if(ItemHelper.getAttributeId(item).equals(gameItem.getAttributeId()))
                    return item;
            return null;
        };
        final List<Item> list = new ArrayList<>();
        for(IGameItem[] family : familyMap.values()) {
            for(int i = family.length - 1; i >= 0; i--) {
                final Item item = searchFunction.apply(family[i]);
                if(item == null)
                    continue;
                list.add(item);
            }
        }
        return list;
    }

}

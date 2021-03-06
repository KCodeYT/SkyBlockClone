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
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.menu.basic.BasicMenu;
import ms.kevi.skyblock.registry.GameItemRegistry;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.registry.VanillaItemRegistry;
import ms.kevi.skyblock.util.FunctionInvoker;

import java.util.*;
import java.util.stream.Collectors;

public class CreativeMenu extends BasicMenu {

    private static final List<IGameItem> VANILLA_ITEMS;
    private static final List<IGameItem> GAME_ITEMS;

    static {
        final List<IGameItem> vanillaItems = new ArrayList<>();
        final List<IGameItem> gameItems = new ArrayList<>();
        final GameItemRegistry itemRegistry = Registries.ITEMS;
        for(IGameItem gameItem : itemRegistry.values()) {
            if(itemRegistry.registrarOf(gameItem) == VanillaItemRegistry.class)
                vanillaItems.add(gameItem);
            else
                gameItems.add(gameItem);
        }

        vanillaItems.sort(Comparator.comparingInt(IGameItem::getId).thenComparingInt(IGameItem::getDamage));
        VANILLA_ITEMS = Collections.unmodifiableList(vanillaItems);
        GAME_ITEMS = Collections.unmodifiableList(gameItems);
    }

    private final Map<Item, FunctionInvoker<Item>> mapping = new HashMap<>();

    public CreativeMenu(Player player) {
        super(player, "Creative");
        this.fill();
    }

    private void fill() {
        this.mapping.clear();
        this.menuItems.clear();
        final Map<IGameItem, List<FunctionInvoker<Item>>> functions = new LinkedHashMap<>();
        for(IGameItem item : VANILLA_ITEMS)
            functions.computeIfAbsent(item, s -> new ArrayList<>()).addAll(item.possibleInvokers());
        for(IGameItem item : GAME_ITEMS)
            functions.computeIfAbsent(item, s -> new ArrayList<>()).addAll(item.possibleInvokers());

        all_items:
        for(Map.Entry<IGameItem, List<FunctionInvoker<Item>>> entry : functions.entrySet()) {
            for(FunctionInvoker<Item> functionInvoker : entry.getValue()) {
                try {
                    final Item item = functionInvoker.invoke();
                    this.mapping.put(item, functionInvoker);
                    this.menuItems.add(item);
                } catch(Throwable throwable) {
                    throwable.printStackTrace();
                    break all_items;
                }
            }
        }

        this.fillInventory(false, true);
    }

    @Override
    public void onDrop(Item item) {
    }

    @Override
    public void onClick(Item item) {
        this.getPlayer().getInventory().addItem(this.mapping.get(item).invoke());
    }

}

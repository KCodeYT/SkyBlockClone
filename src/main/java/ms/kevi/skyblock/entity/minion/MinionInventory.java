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

package ms.kevi.skyblock.entity.minion;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.menu.fake.FakeChestMenu;
import ms.kevi.skyblock.menu.fake.FakeInventory;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.Timings;
import ms.kevi.skyblock.util.Utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MinionInventory extends PlayerInventory {

    private final EntityMinion entityMinion;
    private Item itemInHand;

    MinionInventory(EntityMinion entityMinion, Map<Integer, Item> contents, Item itemInHand) {
        super(entityMinion);
        this.entityMinion = entityMinion;
        this.setContents(contents);
        this.itemInHand = itemInHand;
    }

    @Override
    public boolean setItemInHand(Item itemInHand) {
        this.itemInHand = itemInHand;
        this.sendHeldItem(this.entityMinion.getViewers().values());
        return true;
    }

    @Override
    public Item getItemInHand() {
        return this.itemInHand;
    }

    @Override
    public void onOpen(Player player) {
        final FakeChestMenu chestMenu = new FakeChestMenu(player, this.entityMinion.getType().getDisplayName() + " Minion " + Utils.toRoman(this.entityMinion.getTierLevel()));
        chestMenu.addFakeListener(event -> {
            final int inventorySlots = this.entityMinion.getInventorySlots();
            final int actionSlot = event.getSlot();
            event.setCancelled(true);
            if(actionSlot >= inventorySlots)
                return;
            final Item item = this.getItem(actionSlot);
            if(!item.isNull()) {
                player.getInventory().addItem(item);
                chestMenu.setItem(actionSlot, Item.get(Item.AIR));
                for(int i = 0; i < inventorySlots; i++)
                    this.setItem(i, chestMenu.getItem(i));
                for(int i = 0; i < inventorySlots; i++)
                    if(this.getItem(i).isNull() && this.getItem(i + 1).isNull() && i + 1 < inventorySlots) {
                        this.setItem(i, this.getItem(i + 1));
                        this.setItem(i + 1, Item.get(Item.AIR));
                    }

                chestMenu.sendContents(event.getPlayer());
            }
        });

        Timings.addTimingSpec("Inventory Update Task Creation");
        Timings.addTimingSpec("Inventory Update Task");

        final long start = System.currentTimeMillis();
        final AtomicInteger tickCounter = new AtomicInteger();
        TaskExecutor.repeating(() -> {
            if(chestMenu.getViewers().size() <= 0 && tickCounter.getAndIncrement() > 10)
                TaskExecutor.cancel();
            final int inventorySlots = this.entityMinion.getInventorySlots();
            for(int i = 0; i < inventorySlots; i++)
                chestMenu.setItem(i, this.getItem(i));
            for(int i = inventorySlots; i < this.getSize(); i++)
                chestMenu.setItem(i, FakeInventory.EMPTY_GRAY_GLASS_PANE);
        }, 1);

        Timings.addTiming("Inventory Update Task Creation", System.currentTimeMillis() - start);
    }

}

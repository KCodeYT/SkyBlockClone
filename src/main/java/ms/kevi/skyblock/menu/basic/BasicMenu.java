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

package ms.kevi.skyblock.menu.basic;

import cn.nukkit.Player;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.EventUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicMenu extends FakeDoubleChestMenu {

    private static final int PAGE_SIZE = 28;

    protected final List<Item> menuItems;
    private int currentSite;
    private int nextSite;
    private int previousSite;

    public BasicMenu(Player player, String title) {
        super(player, title);
        this.disablePlayerInventory();
        this.menuItems = new ArrayList<>();
        this.currentSite = 0;
        this.nextSite = 0;
        this.previousSite = 0;

        EventUtil.registerEventHandler(InventoryTransactionEvent.class, EventPriority.LOWEST, event -> {
            final Player source = event.getTransaction().getSource();
            if(!source.equals(player))
                return true;
            if(!this.getViewers().contains(source))
                return false;

            for(InventoryAction action : event.getTransaction().getActions()) {
                if(action instanceof SlotChangeAction) {
                    final SlotChangeAction slotChangeAction = (SlotChangeAction) action;
                    final Inventory inventory = slotChangeAction.getInventory();
                    if(inventory instanceof PlayerInventory)
                        this.onPlayerInventoryAction(slotChangeAction);
                }
            }

            return true;
        });
    }

    @Override
    public void onOpen(Player who) {
        super.onOpen(who);
        TaskExecutor.delayed(() -> this.sendContents(who), 5);
    }

    private boolean isInWindow(int slot) {
        return slot > 8 && slot < 45 && slot % 9 != 0 && (slot - 8) % 9 != 0;
    }

    public void fillInventory(boolean next, boolean current) {
        final int startIndex = next ? this.nextSite : current ? this.currentSite * PAGE_SIZE : this.previousSite;
        if(!current) {
            this.previousSite = startIndex >= PAGE_SIZE ? startIndex - PAGE_SIZE : startIndex;
            this.currentSite = startIndex / PAGE_SIZE;
        }

        for(int i = 0; i < 54; i++)
            if(!this.isInWindow(i) && this.getItem(i).isNull())
                this.setItem(i, EMPTY_GRAY_GLASS_PANE, CANCELLED);
            else if(this.isInWindow(i))
                this.clear(i);

        if(this.currentSite > 0)
            this.setItem(45, Item.get(Item.ARROW, 0, 1).setCustomName("§r§8« §7Page " + (1 + (this.previousSite / PAGE_SIZE))), fakeEvent -> {
                fakeEvent.setCancelled(true);
                this.fillInventory(false, false);
            });
        else
            this.setItem(45, EMPTY_GRAY_GLASS_PANE, CANCELLED);

        int currentSlot = 10;
        for(int i = startIndex; i < this.menuItems.size(); i++) {
            if(!this.isInWindow(currentSlot)) {
                this.nextSite = i;
                this.setItem(53, Item.get(Item.ARROW, 0, 1).setCustomName("§r§8» §7Page " + (1 + (this.nextSite / PAGE_SIZE))), fakeEvent -> {
                    fakeEvent.setCancelled(true);
                    this.fillInventory(true, false);
                });
                return;
            }

            final Item item = this.menuItems.get(i);
            this.setItem(currentSlot, item, fakeEvent -> {
                fakeEvent.setCancelled(true);
                if(fakeEvent.isDropped()) {
                    this.onDrop(item);
                    return;
                }

                this.onClick(item);
            });

            currentSlot++;
            if(currentSlot == 17 || currentSlot == 26 || currentSlot == 35 || currentSlot == 44)
                currentSlot += 2;
        }

        this.setItem(53, EMPTY_GRAY_GLASS_PANE, CANCELLED);
    }

    public void onPlayerInventoryAction(SlotChangeAction slotChangeAction) {

    }

    public abstract void onDrop(Item item);

    public abstract void onClick(Item item);

}

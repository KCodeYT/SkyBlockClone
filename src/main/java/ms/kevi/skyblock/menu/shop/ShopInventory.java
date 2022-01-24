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
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.menu.basic.BasicMenu;
import ms.kevi.skyblock.util.api.MoneyAPI;

import java.util.*;

public class ShopInventory extends BasicMenu {

    private static final Map<Player, List<ShopItem>> SOLD_ITEMS = new HashMap<>();
    private static final int SOLD_ITEMS_MAX = 11;
    private static final int[] STACK_AMOUNTS = new int[]{1, 5, 10, 32, 64};
    private static final Item SELL_ITEM_HOPPER = ItemBuilder.get(Item.HOPPER, 0, 1).setCustomName("§r§cSell item").toItem();
    private static final int SHOP_PAGE_DEFAULT = 0;
    private static final int SHOP_PAGE_STACKED = 1;

    private final ShopMenu shopMenu;
    private final Map<Item, ShopItem> shopItemMap;
    private int currentPage;

    ShopInventory(ShopMenu shopMenu, Player player) {
        super(player, shopMenu.getTitle());
        this.shopMenu = shopMenu;
        this.shopItemMap = new HashMap<>();
        for(ShopItem shopItem : this.shopMenu.getItems()) {
            final Item item = this.getItem(shopItem, 0, false, true);
            this.menuItems.add(item);
            this.shopItemMap.put(item, shopItem);
        }
        this.fillInventory(false, true);
    }

    @Override
    public void onPlayerInventoryAction(SlotChangeAction slotChangeAction) {
        if(this.currentPage == SHOP_PAGE_DEFAULT && this.trySell(slotChangeAction.getSourceItem()))
            slotChangeAction.getInventory().clear(slotChangeAction.getSlot());
    }

    private boolean trySell(Item item) {
        final Optional<ShopItem> optionalShopItem = this.shopMenu.searchItem(ItemHelper.getAttributeId(item));
        if(!optionalShopItem.isPresent())
            return false;
        final ShopItem shopItem = optionalShopItem.get();
        if(shopItem.getSellPrice() <= 0)
            return false;
        final String itemName = (item.hasCustomName() ? item.getCustomName() : item.getName());
        final long sellPrice = (shopItem.getSellPrice() / shopItem.getItem().getCount()) * item.getCount();
        MoneyAPI.addCoins(this.getPlayer().getUniqueId(), sellPrice);

        this.getPlayer().getUIInventory().clearAll();
        this.getPlayer().sendMessage("§aYou sold §r§f" + itemName + "§r§8 x" + item.getCount() + "§a for §6" + Long.valueOf(sellPrice).toString() + " Coins§a!");

        final ShopItem sellItem = shopItem.clone(sellPrice, item);
        final List<ShopItem> soldItems = this.getSoldItems();
        if(soldItems.size() > SOLD_ITEMS_MAX)
            soldItems.remove(0);
        this.getSoldItems().add(sellItem);
        this.refreshSellField();
        return true;
    }

    private boolean tryBuy(ShopItem shopItem, Item item) {
        final long playerCoins = MoneyAPI.getCoins(this.getPlayer().getUniqueId());
        final long buyPrice = (shopItem.getBuyPrice() / shopItem.getItem().getCount()) * item.getCount();
        if(playerCoins >= buyPrice) {
            MoneyAPI.removeCoins(this.getPlayer().getUniqueId(), buyPrice);
            final Item sItem = ItemBuilder.from(this.getItem(shopItem, item.getCount(), false, false)).
                    editExtraAttributes(extraAttributes -> {
                        if(!shopItem.isCanBeStacked() && !extraAttributes.contains(ItemHelper.ATTRIBUTE_UUID))
                            extraAttributes.putString(ItemHelper.ATTRIBUTE_UUID, UUID.randomUUID().toString());
                    }).toItem();
            if(this.getPlayer().getInventory().canAddItem(sItem)) {
                this.getPlayer().getInventory().addItem(sItem);
                return true;
            } else
                this.getPlayer().sendMessage("§r§cYou dont have any inventory space!");
        } else
            this.getPlayer().sendMessage("§r§cYou dont have enough coins!");
        return false;
    }

    private List<ShopItem> getSoldItems() {
        SOLD_ITEMS.putIfAbsent(this.getPlayer(), new ArrayList<>());
        return SOLD_ITEMS.get(this.getPlayer());
    }

    private Item getItem(ShopItem shopItem, int count, boolean stackSite, boolean modify) {
        final Item sItem = shopItem.getItem();
        final Item item = Item.get(sItem.getId(), sItem.getDamage(), stackSite || !modify ? count : sItem.getCount());
        item.setNamedTag(sItem.hasCompoundTag() ? sItem.getNamedTag().clone() : new CompoundTag());

        if(modify) {
            final long buyPrice = stackSite ? (shopItem.getBuyPrice() / sItem.getCount()) * count : shopItem.getBuyPrice();
            List<String> itemLore = new ArrayList<>(Arrays.asList(item.getLore()));
            itemLore.addAll(Arrays.asList(" ", "§r§7Costs",
                    "§r§6" + buyPrice + " Coins", " ",
                    "§r§eClick to buy!"));
            if(shopItem.isCanBeStacked() && !stackSite)
                itemLore.add("§r§eDrop for more buying options!");
            item.setLore(itemLore.toArray(new String[0]));
        }
        return item;
    }

    private void fillInventoryStackSite(ShopItem shopItem) {
        this.currentPage = SHOP_PAGE_STACKED;
        for(int i = 0; i < 54; i++)
            if(i <= 19 || i >= 25)
                this.setItem(i, EMPTY_GRAY_GLASS_PANE, CANCELLED);
        for(int i = 0; i < STACK_AMOUNTS.length; i++) {
            final Item item = this.getItem(shopItem, STACK_AMOUNTS[i], true, true);
            this.setItem(20 + i, item, fakeEvent -> {
                fakeEvent.setCancelled(true);
                this.tryBuy(shopItem, item);
            });
        }
        this.setItem(48, Item.get(Item.ARROW, 0, 1).setCustomName("§r§aGo Back"), fakeEvent -> {
            fakeEvent.setCancelled(true);
            this.fillInventory(false, true);
            this.setItem(48, EMPTY_GRAY_GLASS_PANE, CANCELLED);
        });
        this.setItem(49, CLOSE_BARRIER, fakeEvent -> {
            fakeEvent.setCancelled(true);
            this.close(fakeEvent.getPlayer());
        });
    }

    public void fillInventory(boolean next, boolean current) {
        this.currentPage = SHOP_PAGE_DEFAULT;
        super.fillInventory(next, current);
        if(this.shopMenu.isCanSell())
            this.refreshSellField();
    }

    private void refreshSellField() {
        final List<ShopItem> soldItems = this.getSoldItems();
        final ShopItem sellItem = soldItems.isEmpty() ? null : soldItems.get(soldItems.size() - 1);
        this.setItem(49, sellItem != null ? this.getItem(sellItem, sellItem.getItem().getCount(), true, true) : SELL_ITEM_HOPPER, fakeEvent -> {
            fakeEvent.setCancelled(true);
            if(fakeEvent.isDropped())
                return;

            final Item targetItem = fakeEvent.getTargetItem();
            if(targetItem.isNull() && sellItem != null) {
                if(this.tryBuy(sellItem, sellItem.getItem())) {
                    soldItems.remove(soldItems.size() - 1);
                    this.refreshSellField();
                }
            }
        });
    }

    @Override
    public void onClick(Item item) {
        this.tryBuy(this.shopItemMap.get(item), item);
    }

    @Override
    public void onDrop(Item item) {
        final ShopItem shopItem = this.shopItemMap.get(item);
        if(shopItem.isCanBeStacked())
            this.fillInventoryStackSite(shopItem);
        else
            this.onClick(item);
    }

}

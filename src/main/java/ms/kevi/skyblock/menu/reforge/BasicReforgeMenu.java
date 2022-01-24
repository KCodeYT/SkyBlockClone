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

package ms.kevi.skyblock.menu.reforge;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.registry.ItemGenerator;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;
import ms.kevi.skyblock.menu.fake.FakeTransactionEvent;
import ms.kevi.skyblock.registry.Registries;

public class BasicReforgeMenu extends FakeDoubleChestMenu {

    private static final Item DEFAULT_REFORGE_ITEM = Item.get(Item.ANVIL).setCustomName("§r§eReforge Item").setLore("§r§7Place an item above to reforge", "§r§7it! Reforging items adds a", "§r§7random modifier to the item that", "§r§7grants stat boosts.");
    private static final Item ERROR_REFORGE_ITEM = new Item(-161).setCustomName("§r§cError!").setLore("§r§7You cannot reforge this item!");

    public BasicReforgeMenu(Player player) {
        super(player, "Reforge Item");
        this.setItem(49, CLOSE_BARRIER, CLOSING);
        this.setItem(22, DEFAULT_REFORGE_ITEM, CANCELLED);
        for(int i = 0; i < this.getSize(); i++) {
            if(i != 13 && this.getItem(i).isNull())
                this.setItem(i, (i % 9 == 0 || (i + 1) % 9 == 0) ? EMPTY_RED_GLASS_PANE : EMPTY_GRAY_GLASS_PANE, CANCELLED);
        }

        this.addFakeListener(event -> {
            if(event.getSlot() != 13)
                return;
            final Item targetItem = event.getTargetItem();
            final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(targetItem));

            if(targetItem.isNull()) {
                for(int i = 0; i < this.getSize(); i += 9) {
                    if(i != 0)
                        this.setItem(i - 1, EMPTY_RED_GLASS_PANE, CANCELLED);
                    this.setItem(i, EMPTY_RED_GLASS_PANE, CANCELLED);
                }

                this.setItem(22, DEFAULT_REFORGE_ITEM, CANCELLED);
                return;
            }

            if(gameItem == null || !gameItem.isModifiable()) {
                for(int i = 0; i < this.getSize(); i += 9) {
                    if(i != 0)
                        this.setItem(i - 1, EMPTY_RED_GLASS_PANE, CANCELLED);
                    this.setItem(i, EMPTY_RED_GLASS_PANE, CANCELLED);
                }

                this.setItem(22, ERROR_REFORGE_ITEM, CANCELLED);
            } else {
                for(int i = 0; i < this.getSize(); i += 9) {
                    if(i != 0)
                        this.setItem(i - 1, EMPTY_GREEN_GLASS_PANE, CANCELLED);
                    this.setItem(i, EMPTY_GREEN_GLASS_PANE, CANCELLED);
                }

                this.setItem(22, Item.get(Item.ANVIL).setCustomName("§r§aReforge Item").setLore("§r§7Reforges the above item, giving", "§r§7it a random item modifier that", "§r§7boosts its stats", " ", "§r§7Cost", "§r§6" + (this.calculatePrice(ItemHelper.getRarity(targetItem))) + " Coins", " ", "§r§eClick to reforge!"), this::handleReforge);
            }
        });
    }

    private void handleReforge(FakeTransactionEvent fakeEvent) {
        fakeEvent.setCancelled(true);

        final Player player = this.getPlayer();
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
        final Item item = this.getItem(13);
        final int reforgePrice = this.calculatePrice(ItemHelper.getRarity(item));

        final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item));
        final IModifier[] itemModifiers = Registries.MODIFIERS.findUsable(gameItem);

        if(itemModifiers.length == 0) {
            player.sendMessage("§r§cSeems like there is no modifier for this kind of item!");
            return;
        }

        this.setItem(13, ItemGenerator.create(ItemHelper.setModifier(item, itemModifiers[GameHolder.getGameServer().getRandom().nextInt(itemModifiers.length)])));
    }

    private int calculatePrice(GameRarity rarity) {
        switch(rarity) {
            case COMMON:
                return 250;
            case UNCOMMON:
                return 500;
            case RARE:
                return 1000;
            case EPIC:
                return 2500;
            case LEGENDARY:
                return 5000;
            case MYTHIC:
                return 10000;
            case SPECIAL:
                return 25000;
            case VERY_SPECIAL:
                return 50000;
            default:
                return Integer.MAX_VALUE;
        }
    }

}

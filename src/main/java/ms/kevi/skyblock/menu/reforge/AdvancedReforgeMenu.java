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
import cn.nukkit.utils.DyeColor;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.custom.reforge.AbstractReforgeStoneItem;
import ms.kevi.skyblock.item.registry.ItemGenerator;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;
import ms.kevi.skyblock.menu.fake.FakeTransactionEvent;
import ms.kevi.skyblock.registry.GameItemRegistry;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.Utils;

public class AdvancedReforgeMenu extends FakeDoubleChestMenu {

    private static final Item DEFAULT_REFORGE_ITEM = new Item(-161).setCustomName("§r§cReforge Item").setLore("§r§7Place a weapon, armor piece, or", "§r§7talisman in the left slot and a", "§r§aReforge Stone §7in the right slot", "§r§7to reforge!");
    private static final Item DEFAULT_REFORGE_ANVIL = Item.get(Item.ANVIL).setCustomName("§r§cReforge Item").setLore("§r§7Apply a §aReforge Stone §7to the", "§r§7target item to reforge it,", "§r§7giving it boosted stats and even", "§r§7passive abilities.", "§r§7", "§r§7Place a weapon, armor piece, or", "§r§7talisman in the left slot and a", "§r§7Reforge Stone in the right slot", "§r§7to reforge!");

    private static final Item ERROR_CANNOT_APPLIED = new Item(-161).setCustomName("§r§cError").setLore("§r§7The reforge associated with the", "§r§aReforge Stone §7cannot be", "§r§7applied to the target item!");
    private static final Item ERROR_CANNOT_REFORGED = new Item(-161).setCustomName("§r§cError").setLore("§r§7You cannot reforge this item!");
    private static final Item ERROR_ONLY_REFORGE_ONE = new Item(-161).setCustomName("§r§cError").setLore("§r§7You can only reforge 1 at a", "§r§7time!");
    private static final Item ERROR_ALREADY_UPGRADED = new Item(-161).setCustomName("§r§cError").setLore("§r§7This item's rarity has already", "§r§7been upgraded!");
    private static final Item ERROR_CANNOT_UPGRADED = new Item(-161).setCustomName("§r§cError").setLore("§r§7This item cannot have its raity", "§r§7upgraded!");

    private static final Item RED_ITEM_TO_REFORGE = Item.get(Item.STAINED_GLASS_PANE, DyeColor.RED.getWoolData()).setCustomName("§r§6Item To Reforge").setLore("§r§7A weapon, armor, or", "§r§7talisman you want to", "§r§7reforge should be placed in", "§r§7the slot below");
    private static final Item GREEN_ITEM_TO_REFORGE = Item.get(Item.STAINED_GLASS_PANE, DyeColor.GREEN.getWoolData()).setCustomName("§r§6Item To Reforge").setLore("§r§7A weapon, armor, or", "§r§7talisman you want to", "§r§7reforge should be placed in", "§r§7the slot below");

    private static final Item RED_REFORGE_STONE = Item.get(Item.STAINED_GLASS_PANE, DyeColor.RED.getWoolData()).setCustomName("§r§6Reforge Stone").setLore("§r§7A valid §aReforge Stone", "§r§7you want to sacrifice", "§r§7should be placed in the", "§r§7slot below. This item", "§r§7determines the type of", "§r§7reforge you will apply to", "§r§7the item on the left");
    private static final Item GREEN_REFORGE_STONE = Item.get(Item.STAINED_GLASS_PANE, DyeColor.GREEN.getWoolData()).setCustomName("§r§6Reforge Stone").setLore("§r§7A valid §aReforge Stone", "§r§7you want to sacrifice", "§r§7should be placed in the", "§r§7slot below. This item", "§r§7determines the type of", "§r§7reforge you will apply to", "§r§7the item on the left");

    private static final int REFORGED_ITEM_SLOT = 13;
    private static final int REFORGE_ANVIL_SLOT = 22;
    private static final int ITEM_TO_REFORGE_SLOT = 29;
    private static final int REFORGE_STONE_SLOT = 33;

    public AdvancedReforgeMenu(Player player) {
        this(player, false);
    }

    public AdvancedReforgeMenu(Player player, boolean admin) {
        super(player, admin ? "Reforge Item (Admin Advanced)" : "Reforge Item (Advanced)");
        this.setItem(49, CLOSE_BARRIER, CLOSING);
        this.resetMenu();

        for(int i = 0; i < 45; i++)
            if(i != ITEM_TO_REFORGE_SLOT && i != REFORGE_STONE_SLOT && this.getItem(i).isNull())
                this.setItem(i, EMPTY_GRAY_GLASS_PANE, CANCELLED);
        for(int i = 45; i < 54; i++)
            if(this.getItem(i).isNull())
                this.setItem(i, EMPTY_RED_GLASS_PANE, CANCELLED);

        this.addFakeListener(fakeEvent -> {
            final int slot = fakeEvent.getSlot();
            if(slot != ITEM_TO_REFORGE_SLOT && slot != REFORGE_STONE_SLOT)
                return;

            final Item targetItem = fakeEvent.getTargetItem();
            if(this.getItem(slot == ITEM_TO_REFORGE_SLOT ? REFORGE_STONE_SLOT : ITEM_TO_REFORGE_SLOT).isNull() || targetItem.isNull()) {
                final Item itemToReforge = (slot == ITEM_TO_REFORGE_SLOT && !targetItem.isNull()) || (slot != ITEM_TO_REFORGE_SLOT && !this.getItem(ITEM_TO_REFORGE_SLOT).isNull()) ? GREEN_ITEM_TO_REFORGE : RED_ITEM_TO_REFORGE;
                this.setItem(11, itemToReforge, CANCELLED);
                this.setItem(12, itemToReforge, CANCELLED);
                this.setItem(20, itemToReforge, CANCELLED);

                final Item reforgeStone = (slot == REFORGE_STONE_SLOT && !targetItem.isNull()) || (slot != REFORGE_STONE_SLOT && !this.getItem(REFORGE_STONE_SLOT).isNull()) ? GREEN_REFORGE_STONE : RED_REFORGE_STONE;
                this.setItem(14, reforgeStone, CANCELLED);
                this.setItem(15, reforgeStone, CANCELLED);
                this.setItem(24, reforgeStone, CANCELLED);
                this.setItem(REFORGED_ITEM_SLOT, DEFAULT_REFORGE_ITEM, CANCELLED);
                this.setItem(REFORGE_ANVIL_SLOT, DEFAULT_REFORGE_ANVIL, CANCELLED);
                return;
            }

            final Item item = slot == ITEM_TO_REFORGE_SLOT ? targetItem : this.getItem(ITEM_TO_REFORGE_SLOT);
            final Item stoneItem = slot == REFORGE_STONE_SLOT ? targetItem : this.getItem(REFORGE_STONE_SLOT);

            final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item));
            final IGameItem stoneGameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(stoneItem));

            if(gameItem == null || stoneGameItem == null) {
                this.resetMenu();
                return;
            }

            if(item.getCount() != 1 && !admin) {
                this.resetMenu(ERROR_ONLY_REFORGE_ONE);
                return;
            }

            if(!ItemType.REFORGE_STONE.equals(stoneGameItem.getItemType())) {
                if(stoneGameItem.equals(GameItemRegistry.RECOMBOBULATOR_3000)) {
                    final boolean condition0 = gameItem.isUpgradable();
                    final int rarityUpgrades = ItemHelper.getRarityUpgrades(item);

                    if((!condition0 || rarityUpgrades > 0) && !admin) {
                        this.resetMenu(!condition0 ? ERROR_CANNOT_UPGRADED : ERROR_ALREADY_UPGRADED);
                    } else {
                        final GameRarity upgradedRarity = GameRarity.getByLowestId(gameItem.getRarity().getId() + rarityUpgrades + 1);

                        this.setItem(REFORGE_ANVIL_SLOT, ItemBuilder.get(Item.ANVIL, 0, 1).setCustomName("§aReforge Item").setLore("§7Increases the rarity of this", "§7item to " + upgradedRarity.getColorCode() + upgradedRarity.getName() + "§r§7!", "", "§eClick to reforge!").setSparkle(true).toItem(), this::handleRarityUpgrade);
                        this.setItem(REFORGED_ITEM_SLOT, ItemBuilder.from(ItemGenerator.create(ItemHelper.setRarityUpgrades(item.clone(), rarityUpgrades + 1))).addLore("", "§eClick the anvil to apply!").toItem(), CANCELLED);

                        this.setItem(11, GREEN_ITEM_TO_REFORGE, CANCELLED);
                        this.setItem(12, GREEN_ITEM_TO_REFORGE, CANCELLED);
                        this.setItem(20, GREEN_ITEM_TO_REFORGE, CANCELLED);

                        this.setItem(14, GREEN_REFORGE_STONE, CANCELLED);
                        this.setItem(15, GREEN_REFORGE_STONE, CANCELLED);
                        this.setItem(24, GREEN_REFORGE_STONE, CANCELLED);
                    }
                } else {
                    this.resetMenu();
                }

                return;
            }

            final IModifier modifier = ((AbstractReforgeStoneItem) stoneGameItem).getReforge();
            final boolean condition0 = gameItem.isModifiable();

            if((!condition0 || !modifier.isApplicable(gameItem)) && !admin) {
                this.resetMenu(!condition0 ? ERROR_CANNOT_REFORGED : ERROR_CANNOT_APPLIED);
            } else {
                this.setItem(REFORGE_ANVIL_SLOT, ItemBuilder.get(Item.ANVIL, 0, 1).setCustomName("§aReforge Item").setLore("§7Applies the §9" + modifier.getDisplayName() + "§r§7 reforge", "§7to the target item.", "", "§7Cost", "§6" + Utils.formatNumber(modifier.getApplicationCost(ItemHelper.getRarity(item))) + " Coins", "", "§eClick to reforge!").setSparkle(true).toItem(), this::handleReforge);
                this.setItem(REFORGED_ITEM_SLOT, ItemBuilder.from(ItemGenerator.create(ItemHelper.setModifier(item.clone(), modifier))).addLore("", "§7Cost", "§6" + Utils.formatNumber(modifier.getApplicationCost(ItemHelper.getRarity(item))) + " Coins", "", "§eClick the anvil to reforge!").toItem(), CANCELLED);

                this.setItem(11, GREEN_ITEM_TO_REFORGE, CANCELLED);
                this.setItem(12, GREEN_ITEM_TO_REFORGE, CANCELLED);
                this.setItem(20, GREEN_ITEM_TO_REFORGE, CANCELLED);

                this.setItem(14, GREEN_REFORGE_STONE, CANCELLED);
                this.setItem(15, GREEN_REFORGE_STONE, CANCELLED);
                this.setItem(24, GREEN_REFORGE_STONE, CANCELLED);
            }
        });
    }

    private void resetMenu() {
        this.resetMenu(DEFAULT_REFORGE_ITEM);
    }

    private void resetMenu(Item reforgeItem) {
        this.setItem(REFORGED_ITEM_SLOT, reforgeItem, CANCELLED);
        this.setItem(REFORGE_ANVIL_SLOT, DEFAULT_REFORGE_ANVIL, CANCELLED);

        this.setItem(11, RED_ITEM_TO_REFORGE, CANCELLED);
        this.setItem(12, RED_ITEM_TO_REFORGE, CANCELLED);
        this.setItem(20, RED_ITEM_TO_REFORGE, CANCELLED);

        this.setItem(14, RED_REFORGE_STONE, CANCELLED);
        this.setItem(15, RED_REFORGE_STONE, CANCELLED);
        this.setItem(24, RED_REFORGE_STONE, CANCELLED);
    }

    private void handleRarityUpgrade(FakeTransactionEvent fakeEvent) {
        fakeEvent.setCancelled();
        this.setItem(REFORGED_ITEM_SLOT, ItemGenerator.create(this.getItem(13)), this::handleItem);
        this.setItem(22, this.getItem(22), CANCELLED);
        this.setItem(ITEM_TO_REFORGE_SLOT, Item.get(Item.AIR));
        this.setItem(REFORGE_STONE_SLOT, Item.get(Item.AIR));
    }

    private void handleReforge(FakeTransactionEvent fakeEvent) {
        fakeEvent.setCancelled();

        final Item item = this.getItem(13);
        final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item));
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(this.getPlayer());
        final long cost = ItemHelper.getModifier(item).getApplicationCost(ItemHelper.getRarity(item));
        /*if(skyBlockPlayer.getBank().getCoins() < cost) {
            fakeEvent.setCancelled();
            return;
        }

        skyBlockPlayer.getBank().setCoins(skyBlockPlayer.getBank().getCoins() - cost);*/

        this.setItem(REFORGED_ITEM_SLOT, ItemGenerator.create(item), this::handleItem);
        this.setItem(22, this.getItem(22), CANCELLED);
        this.setItem(ITEM_TO_REFORGE_SLOT, Item.get(Item.AIR));
        this.setItem(REFORGE_STONE_SLOT, Item.get(Item.AIR));
    }

    private void handleItem(FakeTransactionEvent fakeEvent) {
        if(this.getItem(13).isNull()) {
            fakeEvent.setCancelled();
            return;
        }

        TaskExecutor.delayed(this::resetMenu, 1);
    }

    @Override
    public void onClose(Player who) {
        super.onClose(who);
        if(!this.getItem(REFORGED_ITEM_SLOT).isNull() && !this.getItem(REFORGED_ITEM_SLOT).equals(DEFAULT_REFORGE_ITEM))
            who.getInventory().addItem(this.getItem(13));
        if(!this.getItem(ITEM_TO_REFORGE_SLOT).isNull())
            who.getInventory().addItem(this.getItem(ITEM_TO_REFORGE_SLOT));
        if(!this.getItem(REFORGE_STONE_SLOT).isNull())
            who.getInventory().addItem(this.getItem(REFORGE_STONE_SLOT));
    }

}

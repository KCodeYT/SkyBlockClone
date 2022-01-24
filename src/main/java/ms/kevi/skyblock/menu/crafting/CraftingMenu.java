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

package ms.kevi.skyblock.menu.crafting;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerCursorInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.DyeColor;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.crafting.CraftingManager;
import ms.kevi.skyblock.game.crafting.RecipeItem;
import ms.kevi.skyblock.game.crafting.SelectedRecipe;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.menu.SkyBlockMenu;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.registry.VanillaItemRegistry;
import ms.kevi.skyblock.scheduler.TaskExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CraftingMenu extends FakeDoubleChestMenu {

    private static final Item NO_RECIPE_FOUND = Item.get(-161).setCustomName("§r§cRecipe Required").setLore("§r§7Add the items for a valid", "§r§7recipe in the crafting grid", "§r§7to the left!");
    private static final Item QUICK_CRAFTING_SLOT = Item.get(Item.STAINED_GLASS_PANE, DyeColor.LIGHT_GRAY.getWoolData()).setCustomName("§r§cQuick Crafting Slot").setLore("§r§7Quick crafting allows you to", "§r§7craft items without assembling", "§r§7the recipe.");

    private static final List<Integer> RECIPE_SLOTS = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    private static final Integer RESULT_SLOT = 23;
    private static final List<Integer> QUICK_SLOTS = Arrays.asList(16, 25, 34);

    private SelectedRecipe currentRecipe;

    public CraftingMenu(Player player) {
        super(player, "Craft item");
        this.fillItems();
        this.fillEmpty();
    }

    private void fillItems() {
        this.setItem(RESULT_SLOT, NO_RECIPE_FOUND, CANCELLED);
        this.setItem(49, Item.get(Item.ARROW, 0, 1).setCustomName("§r§aGo Back"), this.cancelAndOpen(() -> new SkyBlockMenu(this.getPlayer())));
        for(int quickSlot : QUICK_SLOTS)
            this.setItem(quickSlot, QUICK_CRAFTING_SLOT, CANCELLED);

        this.addFakeListener(event -> {
            final int slot = event.getSlot();
            if(slot == RESULT_SLOT) {
                final PlayerCursorInventory cursorInventory = this.getPlayer().getCursorInventory();
                final Item targetItem = event.getTargetItem();
                final Item sourceItem = event.getSourceItem();
                final Item cursorItem = cursorInventory.getItem(0);

                if((!targetItem.isNull() && ItemHelper.isUnknown(targetItem)) || (!sourceItem.isNull() && ItemHelper.isUnknown(sourceItem))) {
                    event.setCancelled(true);
                    return;
                }

                final IGameItem targetGameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(targetItem).replace("UNKNOWN", "AIR"));
                final IGameItem sourceGameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(sourceItem).replace("UNKNOWN", "AIR"));

                if(!targetGameItem.equals(VanillaItemRegistry.AIR) && !targetGameItem.equals(sourceGameItem)) {
                    event.setCancelled(true);
                    return;
                }

                if(!targetItem.isNull() && (!targetItem.equals(cursorItem) || cursorItem.getCount() + sourceItem.getCount() > targetItem.getMaxStackSize())) {
                    event.setCancelled(true);
                    return;
                }

                if(this.currentRecipe == null) {
                    event.setCancelled(true);
                    return;
                }

                if(this.currentRecipe.getIngredientMap() != null) {
                    for(Integer slotIndex : this.currentRecipe.getIngredientMap().keySet()) {
                        final RecipeItem recipeItem = this.currentRecipe.getIngredientMap().get(slotIndex);
                        if(recipeItem.isNull())
                            continue;

                        final Integer recipeSlot = RECIPE_SLOTS.get(slotIndex);
                        final Item item = this.getItem(recipeSlot);
                        if(recipeItem.isInvalid(item)) {
                            event.setCancelled(true);
                            return;
                        }

                        item.setCount(item.getCount() - recipeItem.getCount());
                        if(item.getCount() == 0)
                            this.setItem(recipeSlot, Item.get(Item.AIR));
                        else if(item.getCount() > 0)
                            this.setItem(recipeSlot, item);
                        else {
                            event.setCancelled(true);
                            return;
                        }
                    }
                } else {
                    final List<Integer> usedSlots = new ArrayList<>();
                    for(RecipeItem recipeItem : this.currentRecipe.getIngredientList()) {
                        if(recipeItem.isNull())
                            continue;

                        boolean found = false;
                        for(Integer recipeSlot : RECIPE_SLOTS) {
                            if(usedSlots.contains(recipeSlot))
                                continue;

                            final Item item = this.getItem(recipeSlot);
                            if(recipeItem.isInvalid(item)) {
                                usedSlots.add(recipeSlot);
                                found = true;
                                item.setCount(item.getCount() - recipeItem.getCount());
                                if(item.getCount() == 0)
                                    this.setItem(recipeSlot, Item.get(Item.AIR));
                                else if(item.getCount() > 0)
                                    this.setItem(recipeSlot, item);
                                else {
                                    event.setCancelled(true);
                                    return;
                                }
                                break;
                            }
                        }

                        if(!found) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }

                if(!targetItem.isNull()) {
                    event.setCancelled(true);
                    cursorInventory.setItem(0, targetItem, true);
                } else
                    event.setCancelled(false);
            }

            final Runnable findRecipeTask = () -> {
                final Map<Integer, RecipeItem> currentShape = CraftingManager.emptyShape();
                for(int i = 0; i < RECIPE_SLOTS.size(); i++) {
                    final int recipeSlot = RECIPE_SLOTS.get(i);
                    final Item ingredient = recipeSlot == slot ? event.getTargetItem() : this.getItem(recipeSlot);
                    if(!ingredient.isNull())
                        currentShape.put(i, RecipeItem.of(ingredient));
                }

                this.currentRecipe = null;
                this.setItem(RESULT_SLOT, NO_RECIPE_FOUND, CANCELLED);

                GameHolder.getGameServer().getCraftingManager().findRecipe(currentShape).ifPresent(selectedRecipe -> {
                    this.currentRecipe = selectedRecipe;
                    this.setItem(RESULT_SLOT, this.currentRecipe.createResult());
                    final Player player = this.getPlayer();
                    for(Item dropItem : player.getInventory().addItem(this.currentRecipe.createExtraResults().toArray(new Item[0])))
                        player.getLevel().dropItem(player, dropItem);
                });
            };

            if(slot == RESULT_SLOT)
                TaskExecutor.delayed(findRecipeTask, 0);
            else
                findRecipeTask.run();
        });
    }

    private void fillEmpty() {
        for(int i = 0; i < this.getSize(); i++)
            if(this.getItem(i).isNull() && !RECIPE_SLOTS.contains(i) && RESULT_SLOT != i && !QUICK_SLOTS.contains(i))
                this.setItem(i, i > 44 ? EMPTY_RED_GLASS_PANE : EMPTY_GRAY_GLASS_PANE, CANCELLED);
    }

    @Override
    public void onClose(Player player) {
        super.onClose(player);
        for(int slot : RECIPE_SLOTS) {
            final Item item = this.getItem(slot);
            if(!item.isNull())
                for(Item dropItem : player.getInventory().addItem(item))
                    player.getLevel().dropItem(player, dropItem);
        }
    }

}

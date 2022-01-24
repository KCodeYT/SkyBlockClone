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
import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.crafting.RecipeItem;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.game.crafting.recipe.ShapedRecipe;
import ms.kevi.skyblock.game.crafting.recipe.ShapelessRecipe;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ViewRecipeMenu extends FakeDoubleChestMenu {

    private static final Item CRAFTING_TABLE = Item.get(Item.CRAFTING_TABLE, 0, 1).setCustomName("§r§aCrafting Table").setLore("§r§7Craft this recipe by using", "§r§7a crafting table.");
    private static final Item NEXT_RECIPE = Item.get(Item.ARROW, 0, 1).setCustomName("§r§aNext Recipe");
    private static final Item PREVIOUS_RECIPE = Item.get(Item.ARROW, 0, 1).setCustomName("§r§aPrevious Recipe");

    private static final List<Integer> RECIPE_SLOTS = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    private static final Integer RESULT_SLOT = 25;

    private final List<Recipe> recipes;
    private int recipeIndex;

    public ViewRecipeMenu(Player player, List<Recipe> recipes) {
        super(player, "Craft item");
        this.recipes = recipes;
        this.recipeIndex = 0;
        this.disablePlayerInventory();
        this.fillItems();
        this.fillEmpty();
    }

    private void fillItems() {
        this.setItem(49, new Item(-161, 0, 1).setCustomName("§r§cClose"), CLOSING);
        this.setItem(23, CRAFTING_TABLE, CANCELLED);
        if(this.recipes.size() - 1 > this.recipeIndex)
            this.setItem(50, NEXT_RECIPE, fakeEvent -> {
                this.recipeIndex++;
                fakeEvent.setCancelled(true);
                this.fillItems();
            });
        else
            this.setItem(50, EMPTY_GRAY_GLASS_PANE, CANCELLED);
        if(0 < this.recipeIndex)
            this.setItem(48, PREVIOUS_RECIPE, fakeEvent -> {
                this.recipeIndex--;
                fakeEvent.setCancelled(true);
                this.fillItems();
            });
        else
            this.setItem(48, EMPTY_GRAY_GLASS_PANE, CANCELLED);

        final Recipe recipe = this.recipes.get(this.recipeIndex);
        this.setItem(RESULT_SLOT, recipe.getResultItem().create(), CANCELLED);

        for(int slot : RECIPE_SLOTS)
            this.clear(slot);
        if(recipe instanceof ShapelessRecipe) {
            final List<RecipeItem> ingredients = ((ShapelessRecipe) recipe).getIngredients();
            for(int i = 0; i < ingredients.size(); i++)
                this.setItem(RECIPE_SLOTS.get(i), ingredients.get(i).getPreview(), CANCELLED);
        } else if(recipe instanceof ShapedRecipe) {
            final Map<Integer, RecipeItem> ingredientMap = ((ShapedRecipe) recipe).getPossibleShapes().get(0);
            for(Integer i : ingredientMap.keySet())
                this.setItem(RECIPE_SLOTS.get(i), ingredientMap.get(i).getPreview(), CANCELLED);
        }
    }

    private void fillEmpty() {
        for(int i = 0; i < this.getSize(); i++)
            if(this.getItem(i).isNull() && !RECIPE_SLOTS.contains(i) && RESULT_SLOT != i)
                this.setItem(i, EMPTY_GRAY_GLASS_PANE, CANCELLED);
    }

}

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

package ms.kevi.skyblock.game.crafting;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.game.crafting.recipe.ShapedRecipe;
import ms.kevi.skyblock.game.crafting.recipe.ShapelessRecipe;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.registry.Registries;

import java.util.*;

public class CraftingManager {

    public static final int GRID_HEIGHT = 3;
    public static final int GRID_WIDTH = 3;

    public static Map<Integer, RecipeItem> emptyShape() {
        final Map<Integer, RecipeItem> shape = new HashMap<>();
        for(int i = 0; i < GRID_WIDTH * GRID_HEIGHT; i++)
            shape.put(i, RecipeItem.EMPTY);
        return shape;
    }

    private final List<Recipe> recipes;

    public CraftingManager() {
        this.recipes = new ArrayList<>();

        loop:
        for(cn.nukkit.inventory.Recipe vanillaRecipe : Server.getInstance().getCraftingManager().getRecipes()) {
            final IGameItem resultGameItem = Registries.ITEMS.fromVanilla(vanillaRecipe.getResult());
            if(resultGameItem == null)
                continue;
            final ResultItem resultItem = new VanillaResultItem(resultGameItem, vanillaRecipe.getResult().getCount());

            if(vanillaRecipe instanceof cn.nukkit.inventory.ShapelessRecipe) {
                final List<RecipeItem> ingredients = new ArrayList<>();
                for(Item ingredient : ((cn.nukkit.inventory.ShapelessRecipe) vanillaRecipe).getIngredientList()) {
                    final IGameItem gameItem = Registries.ITEMS.fromVanilla(ingredient);
                    if(gameItem == null)
                        continue loop;
                    ingredients.add(RecipeItem.of(gameItem, ingredient.getCount()));
                }

                final List<ResultItem> extraResults = new ArrayList<>();
                for(Item extraResultItem : ((cn.nukkit.inventory.ShapelessRecipe) vanillaRecipe).getExtraResults()) {
                    final IGameItem gameItem = Registries.ITEMS.fromVanilla(extraResultItem);
                    if(gameItem == null)
                        continue loop;
                    extraResults.add(new VanillaResultItem(gameItem, extraResultItem.getCount()));
                }

                this.addRecipe(new ShapelessRecipe(resultItem, extraResults, ingredients));
            } else if(vanillaRecipe instanceof cn.nukkit.inventory.ShapedRecipe) {
                final int height = ((cn.nukkit.inventory.ShapedRecipe) vanillaRecipe).getHeight();
                final int width = ((cn.nukkit.inventory.ShapedRecipe) vanillaRecipe).getWidth();

                final List<Map<Integer, RecipeItem>> possibleShapes = new ArrayList<>();
                for(int y = 0; y <= GRID_HEIGHT - height; y++) {
                    for(int x = 0; x <= GRID_WIDTH - width; x++) {
                        final Map<Integer, RecipeItem> possibleShape = emptyShape();
                        for(int y0 = 0; y0 < height; y0++) {
                            for(int x0 = 0; x0 < width; x0++) {
                                final Item ingredient = ((cn.nukkit.inventory.ShapedRecipe) vanillaRecipe).getIngredient(x0, y0);
                                final IGameItem gameItem = Registries.ITEMS.fromVanilla(ingredient);
                                if(gameItem == null)
                                    continue loop;
                                possibleShape.put((y0 + y) * GRID_HEIGHT + (x0 + x), RecipeItem.of(gameItem, ingredient.getCount()));
                            }
                        }

                        possibleShapes.add(possibleShape);
                    }
                }

                final List<ResultItem> extraResults = new ArrayList<>();
                for(Item extraResultItem : ((cn.nukkit.inventory.ShapedRecipe) vanillaRecipe).getExtraResults()) {
                    final IGameItem gameItem = Registries.ITEMS.fromVanilla(extraResultItem);
                    if(gameItem == null)
                        continue loop;
                    extraResults.add(new VanillaResultItem(gameItem, extraResultItem.getCount()));
                }

                this.addRecipe(new ShapedRecipe(resultItem, extraResults, possibleShapes));
            }
        }
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public Optional<SelectedRecipe> findRecipe(Map<Integer, RecipeItem> shape) {
        final List<RecipeItem> ingredients = new ArrayList<>(shape.values());
        ingredients.removeIf(RecipeItem::isNull);
        for(Recipe recipe : this.recipes)
            if(recipe instanceof ShapedRecipe) {
                for(Map<Integer, RecipeItem> possibleShape : ((ShapedRecipe) recipe).getPossibleShapes()) {
                    if(possibleShape.equals(shape))
                        return Optional.of(new SelectedRecipe(possibleShape, null, recipe));
                }
            } else if(recipe instanceof ShapelessRecipe) {
                if(((ShapelessRecipe) recipe).getIngredients().containsAll(ingredients) && ingredients.size() == ((ShapelessRecipe) recipe).getIngredients().size())
                    return Optional.of(new SelectedRecipe(null, ((ShapelessRecipe) recipe).getIngredients(), recipe));
            }
        return Optional.empty();
    }

}

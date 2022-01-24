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

package ms.kevi.skyblock.game.crafting.recipe;

import lombok.Getter;
import ms.kevi.skyblock.game.crafting.CraftingManager;
import ms.kevi.skyblock.game.crafting.RecipeItem;
import ms.kevi.skyblock.game.crafting.ResultItem;

import java.util.*;

@Getter
public class ShapedRecipe extends Recipe {

    private final List<Map<Integer, RecipeItem>> possibleShapes;

    public ShapedRecipe(ResultItem result, List<ResultItem> extraResults, List<Map<Integer, RecipeItem>> possibleShapes) {
        super(result, extraResults);
        this.possibleShapes = possibleShapes;
    }

    public static ShapedRecipeBuilder builder() {
        return new ShapedRecipeBuilder();
    }

    public static class ShapedRecipeBuilder {

        private String[] shape;
        private final Map<Character, RecipeItem> ingredients = new HashMap<>();
        private ResultItem result;
        private final List<ResultItem> extraResults = new ArrayList<>();

        public ShapedRecipeBuilder shape(String... shape) {
            this.shape = shape;
            return this;
        }

        public ShapedRecipeBuilder ingredient(Character character, RecipeItem recipeItem) {
            this.ingredients.put(character, recipeItem);
            return this;
        }

        public ShapedRecipeBuilder result(ResultItem result) {
            this.result = result;
            return this;
        }

        public ShapedRecipeBuilder extraResults(ResultItem... resultItems) {
            this.extraResults.addAll(Arrays.asList(resultItems));
            return this;
        }

        public ShapedRecipe build() {
            final List<Map<Integer, RecipeItem>> possibleShapes = new ArrayList<>();

            final int height = this.shape.length;
            final int width = this.shape[0].length();
            for(String line : this.shape) {
                if(line.length() != width)
                    throw new IllegalArgumentException("Shape is not a valid shape");
                for(char aChar : line.toCharArray()) {
                    if(aChar == ' ')
                        continue;
                    if(!this.ingredients.containsKey(aChar))
                        throw new IllegalArgumentException("Shape does not contain item for char " + aChar);
                }
            }

            if(this.result == null)
                throw new IllegalArgumentException("Recipe does not has result");

            for(int y = 0; y <= CraftingManager.GRID_HEIGHT - height; y++) {
                for(int x = 0; x <= CraftingManager.GRID_WIDTH - width; x++) {
                    final Map<Integer, RecipeItem> possibleShape = CraftingManager.emptyShape();
                    for(int y0 = 0; y0 < height; y0++) {
                        for(int x0 = 0; x0 < width; x0++) {
                            final Character aChar = this.shape[y0].charAt(x0);
                            if(aChar.equals(' ') || !this.ingredients.containsKey(aChar))
                                continue;
                            possibleShape.put((y0 + y) * CraftingManager.GRID_HEIGHT + (x0 + x), this.ingredients.get(aChar));
                        }
                    }

                    possibleShapes.add(possibleShape);
                }
            }

            return new ShapedRecipe(this.result, this.extraResults, possibleShapes);
        }

    }

}

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
import ms.kevi.skyblock.game.crafting.RecipeItem;
import ms.kevi.skyblock.game.crafting.ResultItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ShapelessRecipe extends Recipe {

    private final List<RecipeItem> ingredients;

    public ShapelessRecipe(ResultItem resultItem, List<ResultItem> extraResultItems, List<RecipeItem> ingredients) {
        super(resultItem, extraResultItems);
        this.ingredients = ingredients;
    }

    public static ShapelessRecipeBuilder builder() {
        return new ShapelessRecipeBuilder();
    }

    public static class ShapelessRecipeBuilder {

        private final List<RecipeItem> ingredients = new ArrayList<>();
        private ResultItem result;
        private final List<ResultItem> extraResults = new ArrayList<>();

        public ShapelessRecipeBuilder ingredients(RecipeItem... recipeItems) {
            this.ingredients.addAll(Arrays.asList(recipeItems));
            return this;
        }

        public ShapelessRecipeBuilder result(ResultItem result) {
            this.result = result;
            return this;
        }

        public ShapelessRecipeBuilder extraResults(ResultItem... resultItems) {
            this.extraResults.addAll(Arrays.asList(resultItems));
            return this;
        }

        public ShapelessRecipe build() {
            return new ShapelessRecipe(this.result, this.extraResults, this.ingredients);
        }

    }

}

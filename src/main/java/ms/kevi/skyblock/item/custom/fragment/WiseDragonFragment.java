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

package ms.kevi.skyblock.item.custom.fragment;

import cn.nukkit.item.Item;
import cn.nukkit.utils.DyeColor;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.registry.RecipeRegistry;

import java.util.Arrays;
import java.util.List;

public class WiseDragonFragment extends AbstractGameItem {

    @Override
    public String getDisplayName() {
        return "Wise Dragon Fragment";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.EPIC;
    }

    @Override
    public int getId() {
        return Item.DYE;
    }

    @Override
    public int getDamage() {
        return DyeColor.LIGHT_BLUE.getDyeData();
    }

    @Override
    public int getStackSize() {
        return 64;
    }

    @Override
    public List<Recipe> getRecipes() {
        return Arrays.asList(
                RecipeRegistry.WISE_DRAGON_HELMET,
                RecipeRegistry.WISE_DRAGON_CHESTPLATE,
                RecipeRegistry.WISE_DRAGON_LEGGINGS,
                RecipeRegistry.WISE_DRAGON_BOOTS
        );
    }

}

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
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.registry.RecipeRegistry;

import java.util.Arrays;
import java.util.List;

public class GiantFragmentDiamondItem extends AbstractGameItem {

    @Override
    public String getDisplayName() {
        return "Diamante's Handle";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.EPIC;
    }

    @Override
    public int getId() {
        return Item.DIAMOND_HORSE_ARMOR;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public String[] getLorePrefix() {
        return new String[]{"§8Precursor Relic"};
    }

    @Override
    public String getDescription() {
        return "§7The hilt of the largest sword known to humankind.";
    }

    @Override
    public List<Recipe> getRecipes() {
        return Arrays.asList(
                RecipeRegistry.POWER_WITHER_HELMET,
                RecipeRegistry.POWER_WITHER_CHESTPLATE,
                RecipeRegistry.POWER_WITHER_LEGGINGS,
                RecipeRegistry.POWER_WITHER_BOOTS
        );
    }

}

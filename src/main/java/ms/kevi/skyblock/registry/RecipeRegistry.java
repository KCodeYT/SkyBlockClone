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

package ms.kevi.skyblock.registry;

import ms.kevi.skyblock.game.crafting.GameResultItem;
import ms.kevi.skyblock.game.crafting.RecipeItem;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.game.crafting.recipe.ShapedRecipe;
import ms.kevi.skyblock.item.IGameItem;

public class RecipeRegistry extends Registry<Recipe> {

    private static final GameResultItem.Creator DEFAULT = IGameItem::createItem;

    public static final Recipe SUPERIOR_DRAGON_HELMET = ShapedRecipe.builder().
            shape("AAA", "A A").
            ingredient('A', RecipeItem.of(GameItemRegistry.SUPERIOR_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.SUPERIOR_DRAGON_HELMET, DEFAULT)).build();
    public static final Recipe SUPERIOR_DRAGON_CHESTPLATE = ShapedRecipe.builder().
            shape("A A", "AAA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.SUPERIOR_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.SUPERIOR_DRAGON_CHESTPLATE, DEFAULT)).build();
    public static final Recipe SUPERIOR_DRAGON_LEGGINGS = ShapedRecipe.builder().
            shape("AAA", "A A", "A A").
            ingredient('A', RecipeItem.of(GameItemRegistry.SUPERIOR_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.SUPERIOR_DRAGON_LEGGINGS, DEFAULT)).build();
    public static final Recipe SUPERIOR_DRAGON_BOOTS = ShapedRecipe.builder().
            shape("A A", "A A").
            ingredient('A', RecipeItem.of(GameItemRegistry.SUPERIOR_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.SUPERIOR_DRAGON_BOOTS, DEFAULT)).build();

    public static final Recipe WISE_DRAGON_HELMET = ShapedRecipe.builder().
            shape("AAA", "A A").
            ingredient('A', RecipeItem.of(GameItemRegistry.WISE_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.WISE_DRAGON_HELMET, DEFAULT)).build();
    public static final Recipe WISE_DRAGON_CHESTPLATE = ShapedRecipe.builder().
            shape("A A", "AAA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.WISE_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.WISE_DRAGON_CHESTPLATE, DEFAULT)).build();
    public static final Recipe WISE_DRAGON_LEGGINGS = ShapedRecipe.builder().
            shape("AAA", "A A", "A A").
            ingredient('A', RecipeItem.of(GameItemRegistry.WISE_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.WISE_DRAGON_LEGGINGS, DEFAULT)).build();
    public static final Recipe WISE_DRAGON_BOOTS = ShapedRecipe.builder().
            shape("A A", "A A").
            ingredient('A', RecipeItem.of(GameItemRegistry.WISE_DRAGON_FRAGMENT, 10)).
            result(new GameResultItem(GameItemRegistry.WISE_DRAGON_BOOTS, DEFAULT)).build();

    public static final Recipe POWER_WITHER_HELMET = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.GIANT_FRAGMENT_DIAMOND, 1)).
            ingredient('B', RecipeItem.of(GameItemRegistry.WITHER_HELMET, 1)).
            result(new GameResultItem(GameItemRegistry.POWER_WITHER_HELMET, DEFAULT)).build();
    public static final Recipe POWER_WITHER_CHESTPLATE = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.GIANT_FRAGMENT_DIAMOND, 1)).
            ingredient('B', RecipeItem.of(GameItemRegistry.WITHER_CHESTPLATE, 1)).
            result(new GameResultItem(GameItemRegistry.POWER_WITHER_CHESTPLATE, DEFAULT)).build();
    public static final Recipe POWER_WITHER_LEGGINGS = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.GIANT_FRAGMENT_DIAMOND, 1)).
            ingredient('B', RecipeItem.of(GameItemRegistry.WITHER_LEGGINGS, 1)).
            result(new GameResultItem(GameItemRegistry.POWER_WITHER_LEGGINGS, DEFAULT)).build();
    public static final Recipe POWER_WITHER_BOOTS = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.GIANT_FRAGMENT_DIAMOND, 1)).
            ingredient('B', RecipeItem.of(GameItemRegistry.WITHER_BOOTS, 1)).
            result(new GameResultItem(GameItemRegistry.POWER_WITHER_BOOTS, DEFAULT)).build();

    public static final Recipe ENCHANTED_COBBLESTONE_0 = ShapedRecipe.builder().
            shape(" A ", "AAA", " A ").
            ingredient('A', RecipeItem.of(VanillaItemRegistry.COBBLESTONE, 32)).
            result(new GameResultItem(GameItemRegistry.ENCHANTED_COBBLESTONE, DEFAULT)).build();
    public static final Recipe ENCHANTED_COBBLESTONE_1 = ShapedRecipe.builder().
            shape("AAA", "AA ").
            ingredient('A', RecipeItem.of(VanillaItemRegistry.COBBLESTONE, 32)).
            result(new GameResultItem(GameItemRegistry.ENCHANTED_COBBLESTONE, DEFAULT)).build();

    public static final Recipe COBBLESTONE_MINION_1 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(VanillaItemRegistry.COBBLESTONE, 10)).
            ingredient('B', RecipeItem.of(VanillaItemRegistry.WOODEN_PICKAXE, 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 1))).build();
    public static final Recipe COBBLESTONE_MINION_2 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(VanillaItemRegistry.COBBLESTONE, 20)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 1), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 2))).build();
    public static final Recipe COBBLESTONE_MINION_3 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(VanillaItemRegistry.COBBLESTONE, 40)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 2), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 3))).build();
    public static final Recipe COBBLESTONE_MINION_4 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(VanillaItemRegistry.COBBLESTONE, 64)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 3), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 4))).build();
    public static final Recipe COBBLESTONE_MINION_5 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_COBBLESTONE, 1)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 4), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 5))).build();
    public static final Recipe COBBLESTONE_MINION_6 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_COBBLESTONE, 2)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 5), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 6))).build();
    public static final Recipe COBBLESTONE_MINION_7 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_COBBLESTONE, 4)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 6), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 7))).build();
    public static final Recipe COBBLESTONE_MINION_8 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_COBBLESTONE, 8)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 7), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 8))).build();
    public static final Recipe COBBLESTONE_MINION_9 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_COBBLESTONE, 16)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 8), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 9))).build();
    public static final Recipe COBBLESTONE_MINION_10 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_COBBLESTONE, 32)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 9), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 10))).build();
    public static final Recipe COBBLESTONE_MINION_11 = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_COBBLESTONE, 64)).
            ingredient('B', RecipeItem.of(GameItemRegistry.MINION.specific(MinionTypeRegistry.COBBLESTONE, 10), 1)).
            result(new GameResultItem(GameItemRegistry.MINION, gameItem -> gameItem.createItem(MinionTypeRegistry.COBBLESTONE, 11))).build();

    public static final Recipe PIGGY_BANK = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_PORK, 5)).
            ingredient('B', RecipeItem.of(VanillaItemRegistry.CHEST, 1)).
            result(new GameResultItem(GameItemRegistry.PIGGY_BANK, DEFAULT)).build();
    public static final Recipe REPAIR_CRACKED_PIGGY_BANK = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_PORK, 1)).
            ingredient('B', RecipeItem.of(GameItemRegistry.CRACKED_PIGGY_BANK, 1)).
            result(new GameResultItem(GameItemRegistry.PIGGY_BANK, DEFAULT)).build();
    public static final Recipe REPAIR_BROKEN_PIGGY_BANK = ShapedRecipe.builder().
            shape("AAA", "ABA", "AAA").
            ingredient('A', RecipeItem.of(GameItemRegistry.ENCHANTED_PORK, 1)).
            ingredient('B', RecipeItem.of(GameItemRegistry.BROKEN_PIGGY_BANK, 1)).
            result(new GameResultItem(GameItemRegistry.PIGGY_BANK, DEFAULT)).build();

}

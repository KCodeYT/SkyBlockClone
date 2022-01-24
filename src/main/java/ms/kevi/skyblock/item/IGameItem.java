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

package ms.kevi.skyblock.item;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.registry.Registrable;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.Color;
import ms.kevi.skyblock.util.FunctionInvoker;

import java.util.Collections;
import java.util.List;

public interface IGameItem extends Registrable {

    Item createItem(Object... args);

    default boolean needsParams() {
        return false;
    }

    default List<FunctionInvoker<Item>> possibleInvokers() {
        if(!this.needsParams())
            return Collections.singletonList(FunctionInvoker.of(this::createItem));
        throw new UnsupportedOperationException();
    }

    default String getAttributeId() {
        return this.name();
    }

    String getDisplayName();

    GameRarity getRarity();

    ItemType getItemType();

    int getId();

    int getDamage();

    CompoundTag getCompoundTag();

    int getStackSize();

    boolean isSparkling();

    boolean isModifiable();

    boolean isUpgradable();

    List<Recipe> getRecipes();

    Color getColor();

    IAbility getAbility();

    StatsBooster getStatsBooster();

    String getDescription();

    String[] getLorePrefix();

    String[] getLoreSuffix();

    boolean isFromDungeon();

    boolean isVanilla();

    @Override
    default String name() {
        return Registries.ITEMS.nameOf(this);
    }

    default IGameItem specific(Object... args) {
        throw new UnsupportedOperationException("This operation is not implemented in class " + this.getClass().getName() + "!");
    }

}

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

package ms.kevi.skyblock.item.registry;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.util.Color;

import java.util.List;

public abstract class AbstractGameItem implements IGameItem {

    @Override
    public Item createItem(Object... args) {
        return ItemGenerator.create(this.getAttributeId(), 1);
    }

    @Override
    public ItemType getItemType() {
        return null;
    }

    @Override
    public CompoundTag getCompoundTag() {
        return null;
    }

    @Override
    public int getStackSize() {
        return 0;
    }

    @Override
    public boolean isSparkling() {
        return false;
    }

    @Override
    public boolean isModifiable() {
        return false;
    }

    @Override
    public boolean isUpgradable() {
        return true;
    }

    @Override
    public List<Recipe> getRecipes() {
        return null;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public IAbility getAbility() {
        return null;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String[] getLorePrefix() {
        return null;
    }

    @Override
    public String[] getLoreSuffix() {
        return null;
    }

    @Override
    public boolean isFromDungeon() {
        return false;
    }

    @Override
    public boolean isVanilla() {
        return false;
    }

}

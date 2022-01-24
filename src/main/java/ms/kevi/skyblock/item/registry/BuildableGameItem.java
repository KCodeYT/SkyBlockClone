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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.util.Color;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public final class BuildableGameItem implements IGameItem {

    private final String attributeId;
    private final String displayName;
    private final GameRarity rarity;
    private final ItemType itemType;
    private final int id;
    private final int damage;
    private final CompoundTag compoundTag;
    private final int stackSize;
    private final boolean sparkling;
    private final boolean modifiable;
    private final boolean upgradable;
    private final List<Recipe> recipes;
    private final Color color;
    private final IAbility ability;
    private final StatsBooster statsBooster;
    private final String description;
    private final String[] lorePrefix;
    private final String[] loreSuffix;
    private final boolean fromDungeon;
    private final boolean vanilla;

    @Override
    public Item createItem(Object... args) {
        return ItemGenerator.create(this.getAttributeId(), args.length > 0 ? (int) args[0] : 1);
    }

}

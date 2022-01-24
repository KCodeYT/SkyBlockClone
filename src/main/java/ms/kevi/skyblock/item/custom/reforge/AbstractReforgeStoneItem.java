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

package ms.kevi.skyblock.item.custom.reforge;

import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.registry.AbstractGameItem;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReforgeStoneItem extends AbstractGameItem {

    @Override
    public final ItemType getItemType() {
        return ItemType.REFORGE_STONE;
    }

    @Override
    public final boolean isModifiable() {
        return false;
    }

    @Override
    public String[] getLorePrefix() {
        final List<String> lore = new ArrayList<>();
        lore.add("§8Reforge Stone");
        lore.add("");
        ItemHelper.addDescription(lore,
                "§7Can be used in a Reforge Anvil or with the Dungeon Blacksmith to apply the §9" +
                        this.getReforge().getDisplayName() + " §7reforge to " + this.requiredItemType() + "."
        );
        final String requirement = this.getRequirement();
        if(requirement != null)
            ItemHelper.addDescription(lore, "§7Requires §a" + requirement + "§7!");
        return lore.toArray(new String[0]);
    }

    public String getRequirement() {
        return null;
    }

    public abstract String requiredItemType();

    public abstract IModifier getReforge();

}

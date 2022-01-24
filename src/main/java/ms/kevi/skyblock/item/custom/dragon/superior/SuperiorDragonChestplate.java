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

package ms.kevi.skyblock.item.custom.dragon.superior;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.item.custom.dragon.DragonArmor;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.item.registry.set.PieceItem;
import ms.kevi.skyblock.registry.GameItemRegistry;
import ms.kevi.skyblock.registry.ItemAbilityRegistry;
import ms.kevi.skyblock.util.Color;

public class SuperiorDragonChestplate extends AbstractGameItem implements DragonArmor {

    @Override
    public String getDisplayName() {
        return "Superior Dragon Chestplate";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.LEGENDARY;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.CHESTPLATE;
    }

    @Override
    public int getId() {
        return Item.LEATHER_TUNIC;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }

    @Override
    public Color getColor() {
        return new Color(242, 223, 17);
    }

    @Override
    public IAbility getAbility() {
        return ItemAbilityRegistry.SUPERIOR_BLOOD;
    }

    @Override
    public Type getType() {
        return Type.SUPERIOR;
    }

    @Override
    public PieceItem getHelmet() {
        return (PieceItem) GameItemRegistry.SUPERIOR_DRAGON_HELMET;
    }

    @Override
    public PieceItem getChestplate() {
        return (PieceItem) GameItemRegistry.SUPERIOR_DRAGON_CHESTPLATE;
    }

    @Override
    public PieceItem getLeggings() {
        return (PieceItem) GameItemRegistry.SUPERIOR_DRAGON_LEGGINGS;
    }

    @Override
    public PieceItem getBoots() {
        return (PieceItem) GameItemRegistry.SUPERIOR_DRAGON_BOOTS;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.STRENGTH, 10).
                put(GameStats.CRITICAL_CHANCE, 2).
                put(GameStats.CRITICAL_DAMAGE, 10).
                put(GameStats.HEALTH, 150).
                put(GameStats.DEFENSE, 190).
                put(GameStats.SPEED, 3).
                put(GameStats.INTELLIGENCE, 25);
    }

}

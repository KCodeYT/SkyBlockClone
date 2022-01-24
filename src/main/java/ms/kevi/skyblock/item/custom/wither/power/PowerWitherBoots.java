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

package ms.kevi.skyblock.item.custom.wither.power;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.item.custom.wither.WitherArmor;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.item.registry.set.PieceItem;
import ms.kevi.skyblock.registry.GameItemRegistry;
import ms.kevi.skyblock.registry.ItemAbilityRegistry;
import ms.kevi.skyblock.util.Color;

public class PowerWitherBoots extends AbstractGameItem implements WitherArmor {

    @Override
    public String getDisplayName() {
        return "Necron's Boots";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.LEGENDARY;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.BOOTS;
    }

    @Override
    public int getId() {
        return Item.LEATHER_BOOTS;
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
        return new Color(231, 110, 60);
    }

    @Override
    public IAbility getAbility() {
        return ItemAbilityRegistry.WITHERBORN;
    }

    @Override
    public Type getType() {
        return Type.POWER;
    }

    @Override
    public PieceItem getHelmet() {
        return (PieceItem) GameItemRegistry.POWER_WITHER_HELMET;
    }

    @Override
    public PieceItem getChestplate() {
        return (PieceItem) GameItemRegistry.POWER_WITHER_CHESTPLATE;
    }

    @Override
    public PieceItem getLeggings() {
        return (PieceItem) GameItemRegistry.POWER_WITHER_LEGGINGS;
    }

    @Override
    public PieceItem getBoots() {
        return (PieceItem) GameItemRegistry.POWER_WITHER_BOOTS;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.STRENGTH, 40).
                put(GameStats.CRITICAL_CHANCE, 30).
                put(GameStats.HEALTH, 145).
                put(GameStats.DEFENSE, 85).
                put(GameStats.INTELLIGENCE, 10);
    }

    @Override
    public boolean isFromDungeon() {
        return true;
    }

}

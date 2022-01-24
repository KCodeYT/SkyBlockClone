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

package ms.kevi.skyblock.item.custom.dragon.wise;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSkull;
import cn.nukkit.nbt.tag.CompoundTag;
import de.kcodeyt.heads.util.SkullOwner;
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

public class WiseDragonHelmet extends AbstractGameItem implements DragonArmor {

    @Override
    public String getDisplayName() {
        return "Wise Dragon Helmet";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.LEGENDARY;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.HELMET;
    }

    @Override
    public int getId() {
        return Item.SKULL;
    }

    @Override
    public int getDamage() {
        return ItemSkull.HEAD;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }

    @Override
    public IAbility getAbility() {
        return ItemAbilityRegistry.WISE_BLOOD;
    }

    @Override
    public Type getType() {
        return Type.WISE;
    }

    @Override
    public PieceItem getHelmet() {
        return (PieceItem) GameItemRegistry.WISE_DRAGON_HELMET;
    }

    @Override
    public PieceItem getChestplate() {
        return (PieceItem) GameItemRegistry.WISE_DRAGON_CHESTPLATE;
    }

    @Override
    public PieceItem getLeggings() {
        return (PieceItem) GameItemRegistry.WISE_DRAGON_LEGGINGS;
    }

    @Override
    public PieceItem getBoots() {
        return (PieceItem) GameItemRegistry.WISE_DRAGON_BOOTS;
    }

    @Override
    public CompoundTag getCompoundTag() {
        return new CompoundTag().putCompound("SkullOwner", SkullOwner.buildTag(
                "b3c79a6a-103a-33cf-8ae2-579722654939",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHBzOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU3OTdjNzdjY2VlM2MwNGMzZGVlZDc3MDU5NWVhODNlYTFlODRjOWE0NjI5Yjc1M2FmNWQzNTFjMGNjMTFhNzQifX19"
        ));
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.HEALTH, 70).
                put(GameStats.DEFENSE, 110).
                put(GameStats.INTELLIGENCE, 125);
    }

}

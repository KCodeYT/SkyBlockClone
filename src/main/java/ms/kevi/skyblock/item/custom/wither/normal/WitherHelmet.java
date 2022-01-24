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

package ms.kevi.skyblock.item.custom.wither.normal;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSkull;
import cn.nukkit.nbt.tag.CompoundTag;
import de.kcodeyt.heads.util.SkullOwner;
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

public class WitherHelmet extends AbstractGameItem implements WitherArmor {

    @Override
    public String getDisplayName() {
        return "Wither Helmet";
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
        return ItemAbilityRegistry.WITHERBORN;
    }

    @Override
    public Type getType() {
        return Type.DEFAULT;
    }

    @Override
    public PieceItem getHelmet() {
        return (PieceItem) GameItemRegistry.WITHER_HELMET;
    }

    @Override
    public PieceItem getChestplate() {
        return (PieceItem) GameItemRegistry.WITHER_CHESTPLATE;
    }

    @Override
    public PieceItem getLeggings() {
        return (PieceItem) GameItemRegistry.WITHER_LEGGINGS;
    }

    @Override
    public PieceItem getBoots() {
        return (PieceItem) GameItemRegistry.WITHER_BOOTS;
    }

    @Override
    public CompoundTag getCompoundTag() {
        return new CompoundTag().putCompound("SkullOwner", SkullOwner.buildTag(
                "cf225c71-a676-30a0-90da-75907b9a99d0",
                "ewogICJ0aW1lc3RhbXAiIDogMTYwNTU0MzM0ODIwNiwKICAicHJvZmlsZUlkIiA6ICJhMmY4MzQ1OTVjODk0YTI3YWRkMzA0OTcxNmNhOTEwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJiUHVuY2giLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkxMTI5NDA5Nzg5NTg5YWUxYjQzZTFkODI4YzA2ZmM5YTIxM2I3ODM0OGE2OGI3MzEzYmRkNDAxZDc0ODVjYyIKICAgIH0KICB9Cn0="
        ));
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.HEALTH, 200).
                put(GameStats.DEFENSE, 100).
                put(GameStats.INTELLIGENCE, 30);
    }

    @Override
    public boolean isFromDungeon() {
        return true;
    }

}

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

package ms.kevi.skyblock.item.custom;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.handler.ItemInteractHandler;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.registry.AbstractGameItem;

public class DebugItem extends AbstractGameItem implements ItemInteractHandler {

    @Override
    public String getDisplayName() {
        return "Debug Stick";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.SPECIAL;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SWORD;
    }

    @Override
    public int getId() {
        return Item.STICK;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public boolean isModifiable() {
        return false;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public StatsBooster getStatsBooster() {
        return StatsBooster.create().
                put(GameStats.HEALTH, 1000).
                put(GameStats.DEFENSE, 1000).
                put(GameStats.INTELLIGENCE, 1000);
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final Level level = player.getLevel();

        if(block != null) {
            event.setCancelled(true);
            player.sendMessage("Debug:");
            player.sendMessage("Found Block: " + block.getLocation() + ", BlockType=" + block.getClass().getName() + ", Id=" + block.getId() + ", Meta=" + block.getDamage());

            final BlockEntity blockEntity = level.getBlockEntity(block);
            if(blockEntity != null)
                player.sendMessage("Found Block Entity -> " + blockEntity.namedTag.toString().replace("\t", "  "));
        }

    }

}

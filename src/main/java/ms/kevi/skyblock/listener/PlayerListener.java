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

package ms.kevi.skyblock.listener;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockFromToEvent;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.block.LeavesDecayEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.pet.IPetType;
import ms.kevi.skyblock.registry.GameItemRegistry;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.api.ScoreboardAPI;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.setJoinMessage("");

        GameHolder.addPlayer(player);
        for(IPetType petType : Registries.PETS.values()) {
            for(GameRarity gameRarity : petType.getAvailableRarities()) {
                GameHolder.getPlayer(player).getPets().addPet(petType, gameRarity, 0);
                GameHolder.getPlayer(player).getPets().addPet(petType, gameRarity, 50);
                GameHolder.getPlayer(player).getPets().addPet(petType, gameRarity, 100);
            }
        }

        player.getInventory().setItem(8, GameItemRegistry.MENU.createItem());
        ScoreboardAPI.update(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        GameHolder.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        if(event.getBlock().getId() == BlockID.FARMLAND)
            event.setCancelled(true);
    }

    @EventHandler
    public void onUpdate(BlockUpdateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }

}

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
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerToggleSneakEvent;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.ItemValidation;
import ms.kevi.skyblock.item.handler.ItemInteractHandler;
import ms.kevi.skyblock.menu.crafting.ViewRecipeMenu;
import ms.kevi.skyblock.registry.Registries;

import java.util.HashMap;
import java.util.Map;

public class ItemHandlerListener implements Listener {

    private final Map<Player, Long> spamFilter;

    public ItemHandlerListener() {
        this.spamFilter = new HashMap<>();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Item item = event.getItem();
        final Player player = event.getPlayer();

        if(event.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK &&
                event.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
            return;

        if(item == null || item.isNull() || !ItemValidation.hasExtraAttributes(item))
            return;

        final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item));

        if(gameItem == null)
            return;

        final long timeMillis = System.currentTimeMillis();
        if(this.spamFilter.getOrDefault(player, 0L) + 50 >= timeMillis) {
            event.setCancelled(true);
            return;
        }
        this.spamFilter.put(player, timeMillis);

        if(gameItem instanceof ItemInteractHandler) {
            event.setCancelled(true);
            ((ItemInteractHandler) gameItem).handle(event);
        }

        if(gameItem.getRecipes() != null) {
            event.setCancelled(true);
            new ViewRecipeMenu(player, gameItem.getRecipes());
            return;
        }

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
        if(skyBlockPlayer != null && gameItem.getAbility() != null)
            skyBlockPlayer.useAbility(gameItem.getAbility(), event);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();
        final Item item = player.getInventory().getItemInHand();

        if(event.isSneaking() || item == null || item.isNull() || !ItemValidation.hasExtraAttributes(item))
            return;

        final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item));

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
        if(skyBlockPlayer != null && gameItem != null && gameItem.getAbility() != null)
            skyBlockPlayer.useAbility(gameItem.getAbility(), event);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final Item item = player.getInventory().getItemInHand();

        final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item));

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
        if(skyBlockPlayer != null && gameItem != null && gameItem.getAbility() != null)
            skyBlockPlayer.useAbility(gameItem.getAbility(), event);
    }

}

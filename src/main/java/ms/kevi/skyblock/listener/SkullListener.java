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
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerChangeSkinEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.SkinUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SkullListener implements Listener {

    private static final Queue<Runnable> SKIN_TASKS = new ConcurrentLinkedDeque<>();

    public SkullListener() {
        TaskExecutor.repeatingAsync(() -> {
            while(!SKIN_TASKS.isEmpty())
                SKIN_TASKS.poll().run();
        }, 1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Item helmetItem = player.getInventory().getHelmet();
        final String helmetSkullOwner = this.getSkullOwner(helmetItem);
        if(helmetSkullOwner != null)
            SKIN_TASKS.offer(() -> SkinUtil.addSkull(player, SkinUtil.base64Texture(helmetSkullOwner)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTransaction(InventoryTransactionEvent event) {
        if(event.isCancelled())
            return;
        final InventoryTransaction transaction = event.getTransaction();
        final Player player = transaction.getSource();
        for(InventoryAction action0 : transaction.getActions()) {
            if(action0 instanceof SlotChangeAction) {
                final SlotChangeAction action = (SlotChangeAction) action0;
                final int slot = action.getSlot();
                final Item sourceItem = action.getSourceItem();
                final Item targetItem = action.getTargetItem();

                if(action.getInventory().equals(player.getInventory())) {
                    if(slot == player.getInventory().getSize()) {
                        if(this.getSkullOwner(sourceItem) != null)
                            SKIN_TASKS.offer(() -> SkinUtil.removeSkull(player));
                        final String targetSkullOwner = this.getSkullOwner(targetItem);
                        if(targetSkullOwner != null)
                            SKIN_TASKS.offer(() -> SkinUtil.addSkull(player, SkinUtil.base64Texture(targetSkullOwner)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChangeSkin(PlayerChangeSkinEvent event) {
        event.setCancelled(true);
    }

    private String getSkullOwner(Item item) {
        if(item == null || !item.hasCompoundTag())
            return null;
        final CompoundTag skullOwner = item.getNamedTag().getCompound("SkullOwner");
        if(!skullOwner.isEmpty()) {
            final ListTag<CompoundTag> textures = skullOwner.getCompound("Properties").getList("textures", CompoundTag.class);
            if(textures.size() > 0)
                return textures.get(0).getString("Value");
        }
        return null;
    }

}

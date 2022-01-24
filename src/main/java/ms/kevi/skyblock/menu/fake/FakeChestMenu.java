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

package ms.kevi.skyblock.menu.fake;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.DropItemAction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import lombok.AccessLevel;
import lombok.Getter;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.EventUtil;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.Supplier;

public class FakeChestMenu extends ContainerInventory implements Listener, FakeInventory {

    private static final Map<Player, FakeChestMenu> MENU_MAP = new HashMap<>();

    @Getter
    private final Player player;
    private final String name;
    @Getter(AccessLevel.PACKAGE)
    private final List<BlockVector3> blockVectors;
    private final Map<Integer, FakeTransactionListener> slotListeners;
    private final List<FakeTransactionListener> listeners;

    public FakeChestMenu(Player player, String name) {
        this(InventoryType.CHEST, player, name);
    }

    FakeChestMenu(InventoryType type, Player player, String name) {
        super(new FakeInventoryHolder(), type);
        this.player = player;
        this.name = name;
        this.blockVectors = this.onOpenBlock(player);
        this.slotListeners = new HashMap<>();
        this.listeners = new ArrayList<>();

        EventUtil.registerEventHandler(InventoryTransactionEvent.class, EventPriority.NORMAL, this::onTransaction);
        player.addWindow(this);
        MENU_MAP.put(player, this);
        ((FakeInventoryHolder) this.holder).init(this);
    }

    @Override
    public void onOpen(Player who) {
        TaskExecutor.delayed(() -> {
            if(this.blockVectors.size() > 1)
                this.placeChest(who, this.blockVectors.get(1), this.blockVectors.get(0));
            this.placeChest(who, this.blockVectors.get(0), this.blockVectors.size() == 1 ? null : this.blockVectors.get(1));
            TaskExecutor.delayed(() -> super.onOpen(who), 3);
        }, 4);
    }

    List<BlockVector3> onOpenBlock(Player who) {
        return Collections.singletonList(this.player.add(this.player.getDirectionVector().multiply(-3)).asBlockVector3());
    }

    private void placeChest(Player who, BlockVector3 pos, BlockVector3 pairPos) {
        final UpdateBlockPacket updateBlock = new UpdateBlockPacket();
        updateBlock.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(Block.CHEST, 0);
        updateBlock.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
        updateBlock.x = pos.x;
        updateBlock.y = pos.y;
        updateBlock.z = pos.z;
        who.dataPacket(updateBlock);
        who.dataPacket(this.createDataPacket(pos, pairPos));
    }

    private BlockEntityDataPacket createDataPacket(BlockVector3 pos, BlockVector3 pairPos) {
        try {
            final BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
            blockEntityDataPacket.x = pos.x;
            blockEntityDataPacket.y = pos.y;
            blockEntityDataPacket.z = pos.z;
            blockEntityDataPacket.namedTag = NBTIO.write(
                    (pairPos == null ? new CompoundTag() : new CompoundTag().
                            putInt("pairx", pairPos.x).
                            putInt("pairz", pairPos.z)).
                            putString("id", BlockEntity.CHEST).
                            putInt("x", pos.x).
                            putInt("y", pos.y).
                            putInt("z", pos.z).
                            putString("CustomName", this.name),
                    ByteOrder.LITTLE_ENDIAN, true);
            return blockEntityDataPacket;
        } catch(IOException var4) {
            throw new RuntimeException("Unable to create NBT for chest");
        }
    }

    protected void close() {
        for(BlockVector3 blockPos : this.blockVectors) {
            final UpdateBlockPacket updateBlock = new UpdateBlockPacket();
            updateBlock.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(this.player.getLevel().getFullBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
            updateBlock.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
            updateBlock.x = blockPos.getX();
            updateBlock.y = blockPos.getY();
            updateBlock.z = blockPos.getZ();
            this.player.dataPacket(updateBlock);
        }
    }

    @Override
    public void onClose(Player who) {
        this.close();
        super.onClose(who);
    }

    public void addFakeListener(FakeTransactionListener listener) {
        this.listeners.add(listener);
    }

    public void setItem(int index, Item item, FakeTransactionListener listener) {
        this.slotListeners.put(index, listener);
        super.setItem(index, item);
    }

    @Override
    public boolean clear(int index, boolean send) {
        this.slotListeners.remove(index);
        return super.clear(index, send);
    }

    protected FakeTransactionListener cancelAndOpen(Supplier<FakeInventory> supplier) {
        return CLOSING.andThen(event -> supplier.get());
    }

    protected void disablePlayerInventory() {
        EventUtil.registerEventHandler(InventoryTransactionEvent.class, EventPriority.LOWEST, event -> {
            final Player source = event.getTransaction().getSource();
            if(source != this.getPlayer())
                return true;
            if(!this.getViewers().contains(source) || MENU_MAP.get(source) != this)
                return false;

            InventoryAction lastAction = null;
            for(InventoryAction action : event.getTransaction().getActions()) {
                if((action instanceof DropItemAction && lastAction instanceof SlotChangeAction && ((SlotChangeAction) lastAction).getInventory() instanceof PlayerInventory) ||
                        (action instanceof SlotChangeAction && ((SlotChangeAction) action).getInventory() instanceof PlayerInventory))
                    event.setCancelled(true);
                lastAction = action;
            }

            return true;
        });
    }

    private boolean onTransaction(InventoryTransactionEvent event) {
        final InventoryTransaction transaction = event.getTransaction();
        final Player player = transaction.getSource();
        if(this.getViewers().size() == 0)
            return false;
        if(player != this.getPlayer() || !this.getViewers().contains(this.getPlayer()))
            return true;

        final List<FakeTransactionEvent> fakeTransactionEvents = new ArrayList<>();
        for(InventoryAction action : transaction.getActions()) {
            final Item targetItem = action.getTargetItem();
            final Item sourceItem = action.getSourceItem();
            if(action instanceof DropItemAction) {
                final FakeTransactionEvent parentEvent = fakeTransactionEvents.stream().filter(fakeEvent -> fakeEvent.getSourceItem().equals(targetItem)).findAny().orElse(null);
                if(parentEvent != null) {
                    fakeTransactionEvents.add(new FakeTransactionEvent(
                            parentEvent,
                            parentEvent.getInventory(),
                            player,
                            parentEvent.getSlot(),
                            sourceItem,
                            targetItem,
                            true
                    ));
                    fakeTransactionEvents.remove(parentEvent);
                } else {
                    fakeTransactionEvents.add(new FakeTransactionEvent(
                            null,
                            null,
                            player,
                            -1,
                            sourceItem,
                            targetItem,
                            true
                    ));
                }
            }

            if(action instanceof SlotChangeAction) {
                if(((SlotChangeAction) action).getInventory() == this) {
                    final FakeTransactionEvent parentEvent = new FakeTransactionEvent(
                            null,
                            this,
                            player,
                            ((SlotChangeAction) action).getSlot(),
                            sourceItem,
                            targetItem,
                            false
                    );

                    final FakeTransactionEvent childEvent = fakeTransactionEvents.stream().filter(fakeEvent -> fakeEvent.getTargetItem().equals(sourceItem)).findAny().orElse(null);
                    if(childEvent != null) {
                        fakeTransactionEvents.remove(childEvent);
                        fakeTransactionEvents.add(new FakeTransactionEvent(
                                parentEvent,
                                parentEvent.getInventory(),
                                player,
                                parentEvent.getSlot(),
                                childEvent.getSourceItem(),
                                childEvent.getTargetItem(),
                                true
                        ));
                    } else {
                        fakeTransactionEvents.add(parentEvent);
                    }
                }
            }
        }

        if(!fakeTransactionEvents.isEmpty()) {
            boolean cancelled = false;
            for(FakeTransactionEvent fakeTransactionEvent : fakeTransactionEvents) {
                final int slot = fakeTransactionEvent.getSlot();
                final FakeTransactionListener slotListener = this.slotListeners.get(slot);
                if(slotListener != null)
                    slotListener.onTransaction(fakeTransactionEvent);
                for(FakeTransactionListener listener : this.listeners)
                    listener.onTransaction(fakeTransactionEvent);
                if(fakeTransactionEvent.isCancelled())
                    cancelled = true;
            }

            if(cancelled)
                event.setCancelled(true);
        }

        return true;
    }

}

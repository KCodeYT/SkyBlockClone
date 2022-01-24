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

package ms.kevi.skyblock.entity.minion;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.network.protocol.*;
import lombok.Getter;
import lombok.Setter;
import ms.kevi.skyblock.SkyBlockPlugin;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.minion.IMinionType;
import ms.kevi.skyblock.game.minion.upgrade.MinionUpgrade;
import ms.kevi.skyblock.game.minion.upgrade.MinionUpgradeInstance;
import ms.kevi.skyblock.game.minion.upgrade.UpgradeSlot;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.ItemValidation;
import ms.kevi.skyblock.registry.GameItemRegistry;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.Language;
import ms.kevi.skyblock.util.MinionSkinUtil;
import ms.kevi.skyblock.util.Timings;
import ms.kevi.skyblock.util.Utils;
import ms.kevi.skyblock.util.form.FormHelper;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static ms.kevi.skyblock.game.minion.upgrade.UpgradeSlot.*;

public class EntityMinion extends EntityHuman {

    private static final int ROTATION_SPEED = 15;
    private static final String MINION_GEOMETRY = Utils.readFileStrict(new File(SkyBlockPlugin.getInstance().getDataFolder(), "entities/minion.geometry.json"));
    private static final Runnable EMPTY_TICK = () -> {
    };

    private final List<Runnable> nextTicks = new ArrayList<>();
    private final Map<UpgradeSlot, MinionUpgradeInstance> upgrades = new EnumMap<>(UpgradeSlot.class);
    private UUID upgradingId;
    private Player upgradingPlayer;
    private UpgradeSlot upgradingSlot;

    @Getter
    private IMinionType type;
    @Setter
    @Getter
    private UUID owner;

    private double defaultYaw;
    private double defaultPitch;

    private FloatingTextParticle speakingText;
    private int speakingTimer;

    private long lastActionTime;
    private float timeBetweenActions;
    private Block currentBlock;

    @Getter
    private int tierLevel;
    @Getter
    private int inventorySlots;

    public EntityMinion(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    protected void initEntity() {
        super.initEntity();
        this.setScale(0.5f);
        if(this.namedTag.contains("DefaultRotation")) {
            final ListTag<DoubleTag> defaultRotation = this.namedTag.getList("DefaultRotation", DoubleTag.class);
            this.defaultYaw = defaultRotation.get(0).data;
            this.defaultPitch = defaultRotation.get(1).data;
        } else {
            this.defaultYaw = this.yaw;
            this.defaultPitch = this.pitch;
        }

        if(this.namedTag.contains("OwnerUUIDMost"))
            this.owner = new UUID(this.namedTag.getLong("OwnerUUIDMost"), this.namedTag.getLong("OwnerUUIDLeast"));

        if(this.namedTag.contains("MinionTypeEnum"))
            this.type = Registries.MINIONS.valueOfNonNull(this.namedTag.getString("MinionTypeEnum"));

        if(this.namedTag.contains("MinionUpgrades")) {
            for(Tag minionUpgrade0 : this.namedTag.getCompound("MinionUpgrades").getAllTags()) {
                final CompoundTag minionUpgrade = (CompoundTag) minionUpgrade0;
                this.upgrades.put(valueOf(minionUpgrade.getName()), new MinionUpgradeInstance(MinionUpgrade.valueOf(minionUpgrade.getString("upgrade")), minionUpgrade.getLong("insertion")));
            }
        }

        this.lastActionTime = System.currentTimeMillis();

        this.tierLevel = 1;
        if(this.namedTag.contains("TierLevel"))
            this.tierLevel = this.namedTag.getInt("TierLevel");

        this.setMaxHealth(4);
        this.setHealth(4);
        this.resendData();
    }

    private void resendData() {
        this.skin = new Skin();
        this.skin.setGeometryName("geometry.armor_stand.skull");
        this.skin.setGeometryData(EntityMinion.MINION_GEOMETRY);
        this.skin.setSkinData(MinionSkinUtil.fetchSkin(this.type, this.tierLevel));
        this.skin.setSkinId(Utils.randomString(ThreadLocalRandom.current().nextInt(8) + 8));
        this.sendSkin();

        final Map<Integer, Item> oldContents = new LinkedHashMap<>();
        if(this.inventory != null) {
            oldContents.putAll(this.inventory.getContents());
            if(this.inventory instanceof MinionInventory) {
                for(int i = this.inventorySlots; i < this.inventory.getSize(); i++)
                    oldContents.remove(i);
            }
        }

        this.inventorySlots = this.type.getMaxStorage(this.tierLevel) / 64;
        this.timeBetweenActions = this.type.getTimeBetweenActions(this.tierLevel);

        this.inventory = new MinionInventory(this, oldContents, this.type.getItemInHand());
        this.inventory.setChestplate(this.type.getChestplateItem());
        this.inventory.setLeggings(this.type.getLeggingsItem());
        this.inventory.setBoots(this.type.getBootsItem());
        this.inventory.sendArmorContents(Server.getInstance().getOnlinePlayers().values());
        this.inventory.sendContents(Server.getInstance().getOnlinePlayers().values());
        for(int i = this.inventorySlots; i < this.inventory.getSize(); i++)
            this.inventory.setItem(i, ItemBuilder.get(Item.BEDROCK, 0, 1).setCustomName(Utils.randomString(ThreadLocalRandom.current().nextInt(8) + 8)).toItem());
    }

    private void sendSkin() {
        final PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.entries = new PlayerListPacket.Entry[]{
                new PlayerListPacket.Entry(this.getUniqueId(), this.getId(), Utils.randomString(ThreadLocalRandom.current().nextInt(8) + 8), this.skin)
        };
        playerListPacket.type = PlayerListPacket.TYPE_ADD;
        this.sendPacket(playerListPacket);
        playerListPacket.type = PlayerListPacket.TYPE_REMOVE;
        this.sendPacket(playerListPacket);
    }

    @Override
    public boolean onUpdate(int currentTick) {
        final long startMillis = System.currentTimeMillis();
        if(this.isAlive() && this.isValid()) {
            this.tickSpeaking();
            this.tickArea(currentTick);
            this.tickTask();
        }
        Timings.addTiming("Minion Entity.onUpdate(...)", System.currentTimeMillis() - startMillis);

        return super.onUpdate(currentTick);
    }

    private void tickTask() {
        if(!this.nextTicks.isEmpty()) {
            this.nextTicks.get(0).run();
            this.nextTicks.remove(0);
            return;
        }

        long startTime = System.currentTimeMillis();
        final Block block;
        if(this.lastActionTime + (long) (this.timeBetweenActions * 1000) <= System.currentTimeMillis() && (block = this.randomBlock()) != null) {
            final int action = this.type.checkBreakability(block) ? 1 : this.type.checkPlaceability(block) ? 2 : 0;
            if(action == 0) {
                this.currentBlock = null;
                return;
            }

            final List<Item> drops = new ArrayList<>();
            if(this.type.checkBreakability(block))
                drops.addAll(this.getDrops(block));
            final List<Item> inventoryItems = new ArrayList<>();
            for(int i = 0; i < this.inventorySlots; i++)
                if(this.inventory.getItem(i).getId() != Item.AIR)
                    inventoryItems.add(this.inventory.getItem(i));
            final Item itemFormat = this.type.getItemFormat();
            final Item blockFormat = this.type.getBlockFormat();
            final List<Item> convertedBlocks =
                    blockFormat != null && this.hasUpgrade(MinionUpgrade.COMPACTOR) ?
                            this.toBlocks(blockFormat.getId(), blockFormat.getDamage(), itemFormat.getId(), itemFormat.getDamage(), inventoryItems) :
                            null;
            for(int i = 0; i < this.inventorySlots; i++)
                this.inventory.setItem(i, Item.get(Item.AIR));
            if(convertedBlocks != null)
                convertedBlocks.forEach(this.inventory::addItem);
            inventoryItems.forEach(this.inventory::addItem);
            if(!this.canAddDrops(drops))
                return;
            Timings.addTiming("Minion Random Block", System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();

            final Vector3 blockPos = block.add(0.5, 0.5, 0.5);
            this.rotateTo((((Math.atan2(blockPos.z - this.z, blockPos.x - this.x) * 180) / Math.PI) + 270) % 360,
                    (((Math.atan2(new Vector2(this.x, this.z).distance(blockPos.x, blockPos.z), blockPos.y - this.y) * 180) / Math.PI) + 270) % 360);

            this.inventory.setItemInHand(this.type.getItemInHand(block));

            final List<Block> animationBlocks = this.type.getAnimationBlocks(block);
            if(action != 2) {
                final int breakTicks = this.type.getBreakTicks();
                this.nextTicks.add(() -> {
                    for(Block animationBlock : animationBlocks) {
                        final LevelEventPacket eventPacket = new LevelEventPacket();
                        eventPacket.evid = LevelEventPacket.EVENT_BLOCK_START_BREAK;
                        eventPacket.x = (float) animationBlock.x;
                        eventPacket.y = (float) animationBlock.y;
                        eventPacket.z = (float) animationBlock.z;
                        eventPacket.data = breakTicks * 712;
                        this.sendPacket(eventPacket);
                    }
                });

                for(int i = 0; i <= breakTicks; i++) {
                    this.nextTicks.add(this::sendSwingAnimation);
                    for(int i2 = 0; i2 < 2; i2++)
                        this.nextTicks.add(EMPTY_TICK);
                }
            }

            final Item[] dropsArray = drops.toArray(new Item[0]);
            this.nextTicks.add(() -> {
                for(Block animationBlock : animationBlocks) {
                    final LevelEventPacket eventPacket = new LevelEventPacket();
                    eventPacket.evid = LevelEventPacket.EVENT_BLOCK_STOP_BREAK;
                    eventPacket.x = (float) animationBlock.x;
                    eventPacket.y = (float) animationBlock.y;
                    eventPacket.z = (float) animationBlock.z;
                    eventPacket.data = 0;
                    this.sendPacket(eventPacket);
                }

                final Block levelBlock = block.getLevelBlock();

                final int currentAction = this.type.checkBreakability(levelBlock) ? 1 : this.type.checkPlaceability(levelBlock) ? 2 : 0;
                this.inventory.setItemInHand(this.type.getItemInHand(levelBlock));

                if(currentAction > 0) {
                    this.sendSwingAnimation();
                    if(action == 1) {
                        for(Block animationBlock : animationBlocks)
                            this.getLevel().addParticle(new DestroyBlockParticle(animationBlock, animationBlock.getLevelBlock()));
                        this.type.breakBlock(levelBlock);
                        this.getInventory().addItem(dropsArray);
                    } else
                        this.type.placeBlock(levelBlock);
                } else
                    this.getLevel().addParticle(new GenericParticle(this.add(0, 1, 0), GenericParticle.TYPE_VILLAGER_ANGRY));

                this.lastActionTime = System.currentTimeMillis();
                this.currentBlock = null;

                for(int i = 0; i < 3; i++)
                    this.nextTicks.add(EMPTY_TICK);
                this.rotateTo(this.defaultYaw, this.defaultPitch);
            });
            Timings.addTiming("Minion Action", System.currentTimeMillis() - startTime);
        }
    }

    private void rotateTo(double toYaw, double toPitch) {
        double tickYaw = this.yaw;
        while(tickYaw % 360 != toYaw % 360) {
            if(tickYaw % 360 < toYaw % 360 && tickYaw % 360 + ROTATION_SPEED > toYaw % 360)
                tickYaw = toYaw % 360;
            else if(toYaw % 360 > tickYaw % 360)
                tickYaw += ROTATION_SPEED;
            else
                tickYaw -= ROTATION_SPEED;
            final double finalTickYaw = tickYaw % 360;
            this.nextTicks.add(() -> this.setRotation(finalTickYaw, toPitch));
        }
    }

    private void sendPacket(DataPacket dataPacket) {
        dataPacket = dataPacket.clone();
        dataPacket.tryEncode();
        for(Player player : this.getViewers().values())
            player.dataPacket(dataPacket);
    }

    private void sendSwingAnimation() {
        final AnimatePacket animatePacket = new AnimatePacket();
        animatePacket.action = AnimatePacket.Action.SWING_ARM;
        animatePacket.eid = this.getId();
        this.sendPacket(animatePacket);
    }

    private Block randomBlock() {
        if(this.currentBlock != null)
            return this.currentBlock;
        final List<Block> area = this.type.createArea(this);
        List<Block> blocks = area.stream().filter(block -> this.type.checkPlaceability(block)).collect(Collectors.toList());
        if(blocks.isEmpty())
            blocks = area.stream().filter(block -> this.type.checkBreakability(block)).collect(Collectors.toList());
        return blocks.isEmpty() ? null : (this.currentBlock = blocks.get(GameHolder.getGameServer().getRandom().nextInt(blocks.size())));
    }

    private List<Item> getDrops(Block block) {
        final List<Item> drops = new ArrayList<>();
        for(Block animatedBlock : this.type.getAnimationBlocks(block)) {
            for(Item item : animatedBlock.getDrops(this.getInventory().getItemInHand()))
                drops.add(ItemHelper.convertItem(item));
        }
        return drops;
    }

    private boolean canAddDrops(List<Item> drops) {
        for(Item dropItem : drops) {
            if(dropItem.getCount() == 0)
                return true;
            if(!this.getInventory().canAddItem(dropItem))
                return false;
        }
        return true;
    }

    private List<Item> toBlocks(int blockId, int blockMeta, int itemId, int itemMeta, List<Item> items) {
        final List<Item> blocks = new ArrayList<>();
        int itemCount = 0;
        for(Item item : items)
            if(item.getId() == itemId && item.getDamage() == itemMeta)
                itemCount += item.getCount();
        int blocksCount = itemCount / 9;
        int blockStacks = blocksCount / 64;
        itemCount = blocksCount * 9;
        if(blockStacks > 0)
            for(int i = 0; i < blockStacks; i++) {
                blocks.add(Item.get(blockId, blockMeta, 64));
                blocksCount -= 64;
            }
        if(blocksCount > 0)
            blocks.add(Item.get(blockId, blockMeta, blocksCount));
        for(Item item : new ArrayList<>(items)) {
            if(item.getId() == itemId && item.getDamage() == itemMeta && itemCount > 0) {
                if(item.getCount() == itemCount) {
                    item.setCount(0);
                    items.remove(item);
                    itemCount = 0;
                    continue;
                }

                if(item.getCount() > itemCount) {
                    item.setCount(item.getCount() - itemCount);
                    itemCount = 0;
                    continue;
                }

                itemCount -= item.getCount();
                item.setCount(0);
                items.remove(item);
            }
        }
        return blocks;
    }

    private void tickArea(int currentTick) {
        final List<Block> area = this.type.createArea(this);
        final int blockCount = this.type.checkArea(area);
        if(blockCount == 0)
            this.speak(Language.get("MINION_NEED_SPACE"));
        else if(blockCount < area.size())
            this.speak(Language.get("LOCATION_NOT_PERFECT"));
        if(this.nextTicks.isEmpty() && currentTick % 5 == 0) {
            final Block nextBlock = this.randomBlock();
            if(nextBlock != null) {
                final Item nextItemInHand = this.type.getItemInHand(nextBlock);
                if(!nextItemInHand.equals(this.inventory.getItemInHand()))
                    this.inventory.setItemInHand(nextItemInHand);
            }
        }
    }

    public boolean hasUpgrade(MinionUpgrade upgrade) {
        for(MinionUpgradeInstance value : this.upgrades.values())
            if(value.getUpgrade() == upgrade)
                return true;
        return false;
    }

    public boolean hasUpgrade(UpgradeSlot slot) {
        return this.upgrades.containsKey(slot);
    }

    public MinionUpgrade getUpgrade(UpgradeSlot slot) {
        return this.upgrades.get(slot).getUpgrade();
    }

    public long getUpgradeInsertionTime(UpgradeSlot slot) {
        return this.upgrades.get(slot).getInsertionTime();
    }

    private void speak(String text) {
        if(this.speakingText != null) {
            if(text.isEmpty()) {
                this.speakingText.setInvisible(true);
                this.getLevel().addParticle(this.speakingText);
                this.speakingText = null;
            } else {
                this.speakingTimer = 2;
                if(!this.speakingText.getTitle().equalsIgnoreCase(text)) {
                    this.speakingText.setTitle(text);
                    this.getLevel().addParticle(this.speakingText);
                }
            }
        } else if(!text.isEmpty()) {
            this.speakingText = new FloatingTextParticle(this.add(0, 1.25, 0), text);
            this.getLevel().addParticle(this.speakingText);
            this.speakingTimer = 2;
        }
    }

    private void tickSpeaking() {
        if(this.speakingTimer > 0) {
            this.speakingTimer--;
        } else if(this.speakingText != null) {
            this.speakingText.setInvisible(true);
            this.getLevel().addParticle(this.speakingText);
            this.speakingText = null;
        }
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if(player.getUniqueId().equals(this.owner)) {
            if(player.equals(this.upgradingPlayer)) {
                final IGameItem gameItem;
                if(item == null ||
                        item.isNull() ||
                        !ItemValidation.hasExtraAttributes(item) ||
                        (gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item))) == null ||
                        !MinionUpgrade.isMinionUpgrade(gameItem)
                ) {
                    this.upgradingId = null;
                    this.upgradingPlayer = null;
                    this.upgradingSlot = null;
                    player.sendMessage("§cSorry! Please click with an correct item!");
                    return false;
                }

                final boolean canAdd;
                final MinionUpgrade upgrade = MinionUpgrade.valueOf(gameItem.getAttributeId());
                switch(upgrade.getUpgradeType()) {
                    case TIME:
                        canAdd = this.upgradingSlot == FUEL;
                        break;
                    case AUTOMATIC_SHIPPING:
                        canAdd = this.upgradingSlot == AUTOMATIC_SHIPPING;
                        break;
                    default:
                    case INVENTORY:
                        canAdd = this.upgradingSlot == UPGRADE_1 || this.upgradingSlot == UPGRADE_2;
                        break;
                }

                if(!canAdd) {
                    this.upgradingId = null;
                    this.upgradingPlayer = null;
                    this.upgradingSlot = null;
                    player.sendMessage("§cSorry! Please click with an correct item!");
                    return true;
                }

                this.upgrades.put(this.upgradingSlot, new MinionUpgradeInstance(upgrade, System.currentTimeMillis()));
                this.upgradingId = null;
                this.upgradingPlayer = null;
                this.upgradingSlot = null;
                player.sendMessage("§7You have insert your " + gameItem.getDisplayName() + " into your " + this.type.getDisplayName() + " minion!");
                return true;
            }

            FormHelper.simple(this.type.getDisplayName() + " Minion " + Utils.toRoman(this.tierLevel)).
                    ifDo(this.getTierLevel() < this.type.getMaxTierLevel(), helper -> helper.button("Next Tier")).
                    ifDo(this.upgradingId == null, helper -> helper.
                            button(this.hasUpgrade(FUEL) ? "Take fuel out" : "Add Fuel").
                            button(this.hasUpgrade(AUTOMATIC_SHIPPING) ? "Remove " + this.getUpgrade(AUTOMATIC_SHIPPING).getItem().getDisplayName() : "Add Automatic Shipping").
                            button(this.hasUpgrade(UPGRADE_1) ? "Remove " + this.getUpgrade(UPGRADE_1).getItem().getDisplayName() : "Add Upgrade 1").
                            button(this.hasUpgrade(UPGRADE_2) ? "Remove " + this.getUpgrade(UPGRADE_2).getItem().getDisplayName() : "Add Upgrade 2")).
                    button("Open Inventory").
                    button("Pickup Minion").
                    onResponse(r -> {
                        if(this.closed)
                            return;
                        switch(r.getClickedButton().getText().split("\n")[0]) {
                            case "Add Upgrade 1":
                                if(this.upgradingId == null) {
                                    final UUID upgradingId = UUID.randomUUID();
                                    this.upgradingId = upgradingId;
                                    this.upgradingPlayer = player;
                                    this.upgradingSlot = UPGRADE_1;

                                    player.sendMessage("§7Please click the minion holding an Upgrade Item!!");
                                    TaskExecutor.delayed(() -> {
                                        if(this.upgradingId == upgradingId) {
                                            this.upgradingId = null;
                                            this.upgradingPlayer = null;
                                            this.upgradingSlot = null;
                                            player.sendMessage("§cSorry! Your time to select an upgrade is over!");
                                        }
                                    }, 30 * 20);
                                } else {
                                    player.sendMessage("§cSorry! Someone is already upgrading this minion!");
                                }
                                break;
                            case "Open Inventory":
                                this.getInventory().onOpen(player);
                                break;
                            case "Next Tier":
                                if(this.getTierLevel() < this.type.getMaxTierLevel())
                                    this.setTierLevel(this.getTierLevel() + 1);
                                break;
                            case "Pickup Minion":
                                this.despawnFromAll();
                                this.close();

                                final List<Item> items = new ArrayList<>();
                                items.add(GameItemRegistry.MINION.createItem(this.type, this.tierLevel));
                                for(int i = 0; i < this.inventorySlots; i++)
                                    items.add(this.inventory.getItem(i));

                                for(Item item0 : items)
                                    if(player.getInventory().canAddItem(item0))
                                        player.getInventory().addItem(item0);
                                    else
                                        this.getLevel().dropItem(this.floor().add(0.5, 0.5, 0.5), item0);
                                break;
                        }
                    }).send(player);
            return true;
        }
        return super.onInteract(player, item, clickedPos);
    }

    @Override
    public void close() {
        if(this.speakingText != null) {
            this.speakingText.setInvisible(true);
            this.getLevel().addParticle(this.speakingText);
        }
        super.close();
    }

    @Override
    public void spawnTo(Player player) {
        if(this.speakingText != null)
            this.getLevel().addParticle(this.speakingText, new Player[]{player});
        super.spawnTo(player);
    }

    @Override
    public void despawnFrom(Player player) {
        if(this.speakingText != null) {
            this.speakingText.setInvisible(true);
            this.getLevel().addParticle(this.speakingText, new Player[]{player});
            this.speakingText.setInvisible(false);
        }
        super.despawnFrom(player);
    }

    @Override
    public void addMovement(double x, double y, double z, double yaw, double pitch, double headYaw) {
        final MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.eid = this.getId();
        movePlayerPacket.x = (float) this.x;
        movePlayerPacket.y = (float) (this.y + this.getEyeHeight());
        movePlayerPacket.z = (float) this.z;
        movePlayerPacket.yaw = (float) this.yaw;
        movePlayerPacket.headYaw = (float) this.yaw;
        movePlayerPacket.pitch = (float) this.pitch;
        this.sendPacket(movePlayerPacket);
    }

    @Override
    public void saveNBT() {
        if(this.owner != null) {
            this.namedTag.putLong("OwnerUUIDMost", this.owner.getMostSignificantBits());
            this.namedTag.putLong("OwnerUUIDLeast", this.owner.getLeastSignificantBits());
        }

        final CompoundTag minionUpgrades = new CompoundTag();
        for(UpgradeSlot upgradeSlot : this.upgrades.keySet())
            minionUpgrades.putCompound(upgradeSlot.name(), new CompoundTag().
                    putString("upgrade", this.getUpgrade(upgradeSlot).name()).
                    putLong("insertion", this.getUpgradeInsertionTime(upgradeSlot))
            );
        if(!minionUpgrades.isEmpty())
            this.namedTag.putString("MinionTypeEnum", this.type.name());

        if(!this.nextTicks.isEmpty())
            this.nextTicks.get(this.nextTicks.size() - 1).run();
        this.namedTag.putInt("TierLevel", this.tierLevel);
        this.namedTag.putList(new ListTag<>("DefaultRotation").
                add(new DoubleTag("", this.defaultYaw)).
                add(new DoubleTag("", this.defaultPitch))
        );
        super.saveNBT();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && this.type != null;
    }

    public void setType(IMinionType type) {
        this.type = type;
        this.resendData();
    }

    public void setTierLevel(int tierLevel) {
        this.tierLevel = tierLevel;
        this.resendData();
    }

}

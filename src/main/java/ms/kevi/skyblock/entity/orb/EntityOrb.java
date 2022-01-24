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

package ms.kevi.skyblock.entity.orb;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.ExplodeParticle;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.MovePlayerPacket;
import lombok.Getter;
import lombok.Setter;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.booster.BoosterSlot;
import ms.kevi.skyblock.game.orb.IOrbType;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.registry.OrbTypeRegistry;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.SkinUtil;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class EntityOrb extends EntityHuman {

    private static final Map<IOrbType, byte[]> CACHED_TEXTURES = new HashMap<>();
    private final long creationDate = System.currentTimeMillis();
    private final List<Player> spawnedTo = new ArrayList<>();
    @Getter
    @Setter
    private UUID owner;
    private IOrbType orbType;
    private FloatingTextParticle floatingText;
    private int flyTicks;
    private boolean flyUp;
    public EntityOrb(FullChunk chunk, CompoundTag nbt) {
        super(chunk, addOrbNBT(nbt));
    }

    private static CompoundTag addOrbNBT(CompoundTag compoundTag) {
        final IOrbType orbType = compoundTag.contains("OrbType") ? Registries.ORBS.valueOfNonNull(compoundTag.getString("OrbType")) : OrbTypeRegistry.RADIANT;
        final CompoundTag skinTag = new CompoundTag();
        if(!CACHED_TEXTURES.containsKey(orbType))
            CACHED_TEXTURES.put(orbType, SkinUtil.fromImage(SkinUtil.base64Texture(orbType.getSkinTexture())));
        skinTag.putByteArray("Data", CACHED_TEXTURES.get(orbType));
        skinTag.putString("ModelId", "orb_skull." + UUID.randomUUID());
        skinTag.putString("GeometryName", SkinUtil.SKULL_GEOMETRY_NAME);
        skinTag.putByteArray("GeometryData", SkinUtil.SKULL_GEOMETRY.getBytes(StandardCharsets.UTF_8));
        return compoundTag.putCompound("Skin", skinTag);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.orbType = this.namedTag.contains("OrbType") ? Registries.ORBS.valueOfNonNull(this.namedTag.getString("OrbType")) : OrbTypeRegistry.RADIANT;

        this.floatingText = new FloatingTextParticle(this.add(0, 0.5, 0), "");
        try {
            Field metadataField = this.floatingText.getClass().getDeclaredField("metadata");
            metadataField.setAccessible(true);
            EntityMetadata entityMetadata = (EntityMetadata) metadataField.get(this.floatingText);
            entityMetadata.putLong(Entity.DATA_FLAGS, 0);
            metadataField.set(this.floatingText, entityMetadata);
        } catch(Throwable ignored) {
        }

        this.flyTicks = 0;
        this.flyUp = true;
        this.owner = null;
        if(this.namedTag.contains("OwnerUUIDMost"))
            this.owner = new UUID(this.namedTag.getLong("OwnerUUIDMost"), this.namedTag.getLong("OwnerUUIDLeast"));

        this.setMaxHealth(4);
        this.setHealth(4);
    }

    @Override
    public boolean attack(float damage) {
        return false;
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        return false;
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if(player.getUniqueId().equals(this.owner) && player.getInventory().getItemInHand().isNull()) {
            this.close();
            return false;
        }
        return super.onInteract(player, item, clickedPos);
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(this.isAlive() && this.isValid()) {
            this.tickRotate();
            this.tickFlying();
            this.tickParticles();
            if(currentTick % 10 == 0)
                this.tickPlayers();

            this.floatingText.setTitle(this.getNameTagTitle());
            this.spawnedTo.forEach(player -> {
                MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
                movePlayerPacket.eid = this.floatingText.getEntityId();
                movePlayerPacket.x = (float) this.x;
                movePlayerPacket.y = (float) (this.y + 1.75);
                movePlayerPacket.z = (float) this.z;
                movePlayerPacket.yaw = 0;
                movePlayerPacket.headYaw = 0;
                movePlayerPacket.pitch = 0;
                player.dataPacket(movePlayerPacket);
            });
            if(this.creationDate + this.orbType.getTime() < System.currentTimeMillis())
                this.close();
        }
        return super.onUpdate(currentTick);
    }

    private String getNameTagTitle() {
        return this.orbType.getColor() + this.orbType.getName() + " §e" + (int) ((this.creationDate + this.orbType.getTime() - System.currentTimeMillis()) / 1000) + "s";
    }

    private void tickParticles() {
        this.orbType.addOrbParticles(GameHolder.getGameServer().getRandom(), this);
    }

    private void tickPlayers() {
        final List<Player> players = this.getLevel().getPlayers().values().stream().filter(player -> {
            final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
            return player.distance(this) <= this.orbType.getPlayerRadius() && (skyBlockPlayer == null || !skyBlockPlayer.getBoosterHolder().hasBooster(BoosterSlot.POWER_ORB));
        }).collect(Collectors.toList());
        if(!players.isEmpty()) {
            final Random random = GameHolder.getGameServer().getRandom();
            Collections.shuffle(players, random);
            final List<Player> subList = players.subList(0, Math.min(this.orbType.getMaxPlayers(), players.size()));
            if(!subList.isEmpty()) {
                for(Player player : subList) {
                    for(int i = 0; i < 3; i++)
                        this.orbType.addPlayerParticles(random, player);

                    final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
                    if(skyBlockPlayer != null)
                        skyBlockPlayer.getBoosterHolder().setBooster(BoosterSlot.POWER_ORB, this.orbType.getOrbBuff().getStatsBooster());
                }
            }
        }
    }

    private void tickRotate() {
        float rotateSpeed = 16.5f;
        this.yaw += rotateSpeed;
        if(this.yaw >= 360)
            this.yaw -= 360;
    }

    private void tickFlying() {
        float flyingHigh = 0.075f;
        int flyingTicks = 20;

        if(this.flyUp) {
            this.y = (float) this.y + flyingHigh;
            if(this.flyTicks++ == flyingTicks) {
                this.flyUp = false;
                this.flyTicks = 0;
            }
        } else {
            this.y = (float) this.y - flyingHigh;
            if(this.flyTicks++ == flyingTicks) {
                this.flyUp = true;
                this.flyTicks = 0;
            }
        }
    }

    @Override
    public void addMovement(double x, double y, double z, double yaw, double pitch, double headYaw) {
        MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.eid = this.getId();
        movePlayerPacket.x = (float) this.x;
        movePlayerPacket.y = (float) (this.y + this.getEyeHeight());
        movePlayerPacket.z = (float) this.z;
        movePlayerPacket.yaw = (float) this.yaw;
        movePlayerPacket.headYaw = (float) this.yaw;
        movePlayerPacket.pitch = (float) this.pitch;
        this.getViewers().values().forEach(player -> player.dataPacket(movePlayerPacket));
    }

    @Override
    public void saveNBT() {
        this.close();
    }

    @Override
    public void close() {
        if(this.closed)
            return;
        final Random random = GameHolder.getGameServer().getRandom();
        for(int i = 0; i < 5; i++)
            this.getLevel().addParticle(new ExplodeParticle(this.add(random.nextDouble() * (random.nextBoolean() ? -1 : 1),
                    0,
                    random.nextDouble() * (random.nextBoolean() ? -1 : 1))));
        super.close();
    }

    @Override
    public void spawnTo(Player player) {
        final DataPacket[] dataPackets = this.floatingText.encode();
        for(DataPacket dataPacket : dataPackets)
            player.dataPacket(dataPacket);
        this.spawnedTo.add(player);
        super.spawnTo(player);
    }

    @Override
    public void despawnFrom(Player player) {
        this.floatingText.setInvisible(true);
        DataPacket[] dataPackets = this.floatingText.encode();
        for(DataPacket dataPacket : dataPackets)
            player.dataPacket(dataPacket);
        this.floatingText.setInvisible(false);
        this.spawnedTo.remove(player);
        super.despawnFrom(player);
    }

}

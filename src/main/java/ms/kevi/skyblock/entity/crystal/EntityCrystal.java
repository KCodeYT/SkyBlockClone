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

package ms.kevi.skyblock.entity.crystal;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MovePlayerPacket;
import lombok.Getter;
import lombok.Setter;
import ms.kevi.skyblock.SkyBlockPlugin;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.crystal.ICrystalType;
import ms.kevi.skyblock.registry.CrystalTypeRegistry;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.SkinUtil;
import ms.kevi.skyblock.util.Utils;

import java.io.File;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EntityCrystal extends EntityHuman {

    private static final String HEAD_GEOMETRY = Utils.readFileStrict(new File(SkyBlockPlugin.getInstance().getDataFolder(), "entities/crystal.geometry.json"));
    private static final byte[] HEAD_SKIN = SkinUtil.fromImage(Utils.readImageFile(new File(SkyBlockPlugin.getInstance().getDataFolder(), "entities/textures/crystal.skin.png")));
    private final RandomBlock randomBlock = new RandomBlock();
    private int flyTicks;
    private boolean flyUp;
    @Getter
    @Setter
    private UUID owner;
    private int defaultY;

    private ICrystalType type;
    @Getter
    @Setter
    private int range;

    @Getter
    private long lastBehaviorTick;
    @Getter
    @Setter
    private int timeBetweenActions;

    public EntityCrystal(FullChunk chunk, CompoundTag nbt) {
        super(chunk, addCrystalNBT(nbt));
    }

    private static CompoundTag addCrystalNBT(CompoundTag compoundTag) {
        final CompoundTag skinTag = new CompoundTag();
        skinTag.putByteArray("Data", EntityCrystal.HEAD_SKIN);
        skinTag.putString("ModelId", "crystal_skull." + UUID.randomUUID().toString());
        skinTag.putString("GeometryName", "geometry.crystal_skull");
        skinTag.putByteArray("GeometryData", EntityCrystal.HEAD_GEOMETRY.getBytes());
        return compoundTag.putCompound("Skin", skinTag);
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        this.flyTicks = 0;
        this.flyUp = true;
        this.owner = null;
        if(this.namedTag.contains("OwnerUUIDMost"))
            this.owner = new UUID(this.namedTag.getLong("OwnerUUIDMost"), this.namedTag.getLong("OwnerUUIDLeast"));
        if(this.namedTag.contains("DefaultY"))
            this.defaultY = this.namedTag.getInt("DefaultY");
        else
            this.defaultY = this.getFloorY();
        this.y = this.defaultY;

        this.range = 30;
        if(this.namedTag.contains("Range"))
            this.range = this.namedTag.getInt("Range");
        this.timeBetweenActions = 1;
        if(this.namedTag.contains("TimeBetweenActions"))
            this.timeBetweenActions = this.namedTag.getInt("TimeBetweenActions");
        this.type = CrystalTypeRegistry.NETHER_WART;
        if(this.namedTag.contains("CrystalTypeEnum"))
            this.type = Registries.CRYSTALS.valueOf(this.namedTag.getString("CrystalTypeEnum"));

        this.lastBehaviorTick = System.currentTimeMillis();

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
            this.tickBehavior();
        }

        return super.onUpdate(currentTick);
    }

    private void tickParticles() {
        final Random random = GameHolder.getGameServer().getRandom();
        if(random.nextInt(8) > 2) {
            float randomX = (float) ((this.x - 0.5f) + random.nextFloat());
            float randomY = (float) ((this.y - 0.5f) + random.nextFloat());
            float randomZ = (float) ((this.z - 0.5f) + random.nextFloat());

            this.getLevel().addParticle(new GenericParticle(new Vector3(randomX, randomY, randomZ), Particle.TYPE_MOB_SPELL, ((0xff) << 24) | ((245 & 0xff) << 16) | ((245 & 0xff) << 8) | (245 & 0xff)));
        }
    }

    private void tickRotate() {
        final float rotateSpeed = 22.5f;
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

    private void tickBehavior() {
        if(this.lastBehaviorTick + (this.timeBetweenActions * 1000L) <= System.currentTimeMillis()) {
            if(this.randomBlock.isSearching() || !this.randomBlock.equals(RandomBlock.EMPTY))
                return;
            final Block block = this.getLevel().getBlock(this.randomBlock.getX(), this.randomBlock.getY(), this.randomBlock.getZ());
            this.nextBlock();

            if(block.getId() != BlockID.AIR) {
                final double x = this.getFloorX() + 0.5;
                final double y = this.getFloorY() + 0.5;
                final double z = this.getFloorZ() + 0.5;
                final double blockX = block.getFloorX() + 0.5;
                final double blockY = block.getFloorY() + 0.5;
                final double blockZ = block.getFloorZ() + 0.5;

                final double pitch = ((((Math.atan2(Math.sqrt(Math.pow(x - blockX, 2) + Math.pow(z - blockZ, 2)), blockY - y) * 180) / Math.PI) - 90) + 90.0D) * Math.PI / 180.0D;
                final double yaw = ((((Math.atan2(blockZ - z, blockX - x) * 180) / Math.PI) - 90) + 90.0D) * Math.PI / 180.0D;
                final Vector3 vector = (new Vector3(Math.sin(pitch) * Math.cos(yaw), Math.cos(pitch), Math.sin(pitch) * Math.sin(yaw))).normalize().multiply(0.45);

                int particles = 0;
                Vector3 thisVector = this.add(vector.x, vector.y + 1.5, vector.z);
                final GenericParticle genericParticle = new GenericParticle(new Vector3(0, 0, 0), Particle.TYPE_FIREWORKS_SPARK, ((0xff) << 24) | ((245 & 0xff) << 16) | ((245 & 0xff) << 8) | (245 & 0xff));

                while(block.distance(thisVector.subtract(0, 1.5, 0)) > 1.75 && particles < 64) {
                    this.getLevel().addParticle((Particle) genericParticle.setComponents(thisVector.x, thisVector.y, thisVector.z));
                    thisVector = thisVector.add(vector);
                    particles++;
                }

                this.type.executeBlock(block);
                this.lastBehaviorTick = System.currentTimeMillis();
            }
        }
    }

    private void nextBlock() {
        final Level level = this.getLevel();
        final int floorX = this.getFloorX();
        final int floorY = this.getFloorY();
        final int floorZ = this.getFloorZ();
        final int range = this.range;

        this.randomBlock.setSearching(true);
        TaskExecutor.async(() -> {
            final Random random = ThreadLocalRandom.current();
            final long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < 50) {
                final int x = floorX + (random.nextInt(range) * (random.nextBoolean() ? 1 : -1));
                final int y = floorY + 2 - random.nextInt(16);
                final int z = floorZ + (random.nextInt(range) * (random.nextBoolean() ? 1 : -1));
                final Block block = level.getBlock(x, Utils.mathInBetween(y, 0, 255), z);
                if(block.getId() == BlockID.AIR)
                    continue;
                if(this.type.checkBlock(block)) {
                    this.randomBlock.setComponents(block.getFloorX(), block.getFloorZ(), block.getFloorZ());
                    this.randomBlock.setSearching(false);
                    return;
                }
            }

            this.randomBlock.setComponents(0, 0, 0);
            this.randomBlock.setSearching(false);
        });
    }

    @Override
    public void saveNBT() {
        if(this.owner != null) {
            this.namedTag.putLong("OwnerUUIDMost", this.owner.getMostSignificantBits());
            this.namedTag.putLong("OwnerUUIDLeast", this.owner.getLeastSignificantBits());
        }
        this.namedTag.putInt("DefaultY", this.defaultY);
        this.namedTag.putInt("Range", this.range);
        this.namedTag.putInt("TimeBetweenActions", this.timeBetweenActions);
        this.namedTag.putString("CrystalTypeEnum", this.type.name());
        super.saveNBT();
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
        this.getViewers().values().forEach(player -> player.dataPacket(movePlayerPacket));
    }

}

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

package ms.kevi.skyblock.entity.pet;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import ms.kevi.skyblock.game.pet.PetData;
import ms.kevi.skyblock.util.ShortTermMemory;
import ms.kevi.skyblock.util.SkinUtil;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class EntityFlyingSkullPet extends EntityHuman {

    private static final double FLYING_HIGH = 0.045;
    private static final int FLYING_TICKS = 20;

    private final PetData petData;
    private final Player owner;
    private Vector3 targetGoal;
    private Vector3 lastValid;
    private Vector3 lastPoint;
    private int flyTicks;
    private int waitingTicks;
    private boolean flyUp;

    protected EntityFlyingSkullPet(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        final CompoundTag petTag = nbt.getCompound(PetData.TAG_NAME);
        final PetData petData = ShortTermMemory.access(petTag.getByteArray("PetData"));
        if(petData == null) {
            this.petData = null;
            this.owner = null;
            this.close();
            return;
        }

        this.petData = petData;

        final UUID ownerUniqueId = new UUID(petTag.getLong("PetOwnerUniqueIdMost"), petTag.getLong("PetOwnerUniqueIdLeast"));
        this.owner = this.getServer().getPlayer(ownerUniqueId).orElse(null);

        this.flyTicks = FLYING_TICKS / 2;
        this.waitingTicks = 0;
        this.flyUp = false;
    }

    protected static CompoundTag addSkin(BufferedImage bufferedImage, CompoundTag compoundTag) {
        return compoundTag.putCompound("Skin", new CompoundTag().
                putByteArray("Data", SkinUtil.fromImage(bufferedImage)).
                putString("ModelId", UUID.randomUUID().toString()).
                putString("GeometryName", SkinUtil.SKULL_GEOMETRY_NAME).
                putByteArray("GeometryData", SkinUtil.SKULL_GEOMETRY.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setScale(0.725f);
    }

    @Override
    public float getHeight() {
        return 0.1f;
    }

    private boolean hasOwner() {
        return this.owner != null;
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        return false;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(!this.hasOwner() || !this.owner.isOnline()) {
            this.close();
            return false;
        }

        this.updateNameTag();
        this.updatePet();
        super.updateMovement();
        return true;
    }

    private void updatePet() {
        this.handleParticles(this);
        if(this.lastPoint == null || this.lastPoint.distance(this.owner) > 3)
            this.lastPoint = this.owner.add(0, 0, 0);
        if(this.targetGoal == null) {
            if(this.waitingTicks >= 10) {
                if(this.flyUp) {
                    this.y += FLYING_HIGH;
                    if(this.flyTicks++ == FLYING_TICKS) {
                        this.waitingTicks = 0;
                        this.flyUp = false;
                        this.flyTicks = 0;
                    }
                } else {
                    this.y -= FLYING_HIGH;
                    if(this.flyTicks++ == FLYING_TICKS) {
                        this.waitingTicks = 0;
                        this.flyUp = true;
                        this.flyTicks = 0;
                    }
                }
            } else
                this.waitingTicks++;
        } else {
            this.flyTicks = FLYING_TICKS / 2;
            this.waitingTicks = 0;
            this.flyUp = false;
        }

        final double requiredDistance = this.owner.isOnGround() ? 2 : 2.75;
        final Vector3 ownerPos = this.owner.add(0, this.owner.getEyeHeight(), 0);

        if(this.distance(ownerPos) > 16) {
            final Random random = ThreadLocalRandom.current();
            this.teleport(ownerPos.add(random.nextFloat() * (random.nextBoolean() ? -1 : 1), 0, random.nextFloat() * (random.nextBoolean() ? -1 : 1)));
            this.targetGoal = null;
            return;
        }

        if(this.targetGoal != null) {
            this.yaw = ((Math.atan2(this.owner.z - this.z, this.owner.x - this.x) * 180) / Math.PI) - 90;
            this.pitch = 0;
            if(this.distance(ownerPos) < requiredDistance)
                this.targetGoal = null;
            else {
                final double movementSpeed = 3;
                final double x = this.targetGoal.x - this.x;
                final double y = this.targetGoal.y - this.y;
                final double z = this.targetGoal.z - this.z;

                final double diff = Math.abs(x) + Math.abs(y) + Math.abs(z);
                this.motionX = movementSpeed * 0.15 * (x / diff);
                this.motionY = movementSpeed * 0.27 * (y / diff);
                this.motionZ = movementSpeed * 0.15 * (z / diff);

                this.keepMovement = true;
                this.move(this.motionX, this.motionY, this.motionZ);
            }
        }

        if(this.distance(ownerPos) >= requiredDistance || this.lastValid == null) {
            double x = ownerPos.x;
            double y = ownerPos.y;
            double z = ownerPos.z;
            if(this.lastValid != null) {
                if(Math.abs(y - this.lastValid.y) > 0) {
                    x = Math.abs(x - this.lastValid.x) > 1.5 ? x : this.lastValid.x;
                    if(Math.abs(y - this.lastValid.y) > (FLYING_HIGH * this.flyTicks))
                        y += (FLYING_HIGH * this.flyTicks) * (y > this.lastValid.y ? 1 : -1);
                    z = Math.abs(z - this.lastValid.z) > 1.5 ? z : this.lastValid.z;
                }
            }

            this.lastValid = new Vector3(x, y, z);
        }

        if(this.distance(ownerPos) >= requiredDistance)
            this.targetGoal = new Vector3(this.lastValid.x, this.lastValid.y, this.lastValid.z);
    }

    protected abstract void handleParticles(Vector3 lastVector);

    private void updateNameTag() {
        if(this.petData == null)
            return;
        this.setNameTag(
                "§8[§7Lv" + this.petData.getLevel() + "§8] " +
                        this.petData.getRarity().getColorCode() + (this.hasOwner() ? this.owner.getName() : "Unknown") + "'s " +
                        this.petData.getType().getDisplayName()
        );
    }

}
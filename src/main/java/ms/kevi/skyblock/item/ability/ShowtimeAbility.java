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

package ms.kevi.skyblock.item.ability;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.item.EntityFirework;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.ItemFirework;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.DyeColor;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.ImageUtil;
import ms.kevi.skyblock.util.SkinUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowtimeAbility implements IAbility {

    private static final List<byte[]> BALLOON_SKINS;
    private static final DyeColor[] COLORS;

    static {
        COLORS = Arrays.stream(DyeColor.values()).
                filter(color -> color != DyeColor.BLACK && color != DyeColor.BROWN &&
                        color != DyeColor.LIGHT_GRAY && color != DyeColor.GRAY && color != DyeColor.WHITE).
                toArray(DyeColor[]::new);

        final String texturesUrl = "http://textures.minecraft.net/texture/";
        BALLOON_SKINS = Collections.unmodifiableList(Stream.of(
                "52dd11da04252f76b6934bc26612f54f264f30eed74df89941209e191bebc0a2",
                "76387fc246893d92a6dd9ea1b52dcd581e991eeee2e263b27fff1bcf1b154eb7",
                "7a2df315b43583b1896231b77bae1a507dbd7e43ad86c1cfbe3b2b8ef3430e9e",
                "f868e6a5c4a445d60a3050b5bec1d37af1b25943745d2d479800c8436488065a",
                "f052be1c06a4a325129d6f41bb84f0ea1ca6f9f69ebdfff4316e742451c79c21",
                "cb1ae7a471729651b5667b81694e492808c5090c2b168f0a9190fd002ee50a26",
                "a26ec7cd3b6ae249997137c1b94867c66e97499da071bf50adfd37034132fa03",
                "4f85522ee815d110587fffc74113f419d929598e2463b8ce9d39caa9fb6ff5ab"
        ).map(skinId -> SkinUtil.fromImage(ImageUtil.readImage(texturesUrl + skinId))).collect(Collectors.toList()));
    }

    @Override
    public String getDisplayName() {
        return "Showtime";
    }

    @Override
    public String getDescription() {
        return "§7Shoots balloons that create a large explosion on impact, dealing up to §c404 §7damage."; //TODO: Damage calculation
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ITEM;
    }

    @Override
    public InteractionType getInteractionType() {
        return InteractionType.CLICK;
    }

    @Override
    public int getIntelligenceCost() {
        return 100;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Vector3 directionVector = player.getDirectionVector().add(0);
        final Location playerPos = player.add(0, player.getEyeHeight(), 0);

        final byte[] skinData = BALLOON_SKINS.get(GameHolder.getGameServer().getRandom().nextInt(BALLOON_SKINS.size()));
        final EntityHuman entityHuman = this.createHead(playerPos.getChunk(), directionVector.multiply(1.5).add(playerPos), skinData);
        entityHuman.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_INVISIBLE, true);
        entityHuman.spawnToAll();

        TaskExecutor.delayed(() -> entityHuman.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_INVISIBLE, false), 3);
        TaskExecutor.delayedRepeating(new AbilityTask(entityHuman, directionVector), 3, 1);
        return true;
    }

    private EntityHuman createHead(FullChunk fullChunk, Vector3 spawn, byte[] skinData) {
        return new EntityHuman(fullChunk, Entity.getDefaultNBT(spawn).putCompound("Skin", new CompoundTag().
                putByteArray("Data", skinData).
                putString("ModelId", Base64.getEncoder().encodeToString(skinData)).
                putString("GeometryName", SkinUtil.SKULL_GEOMETRY_NAME).
                putByteArray("GeometryData", SkinUtil.SKULL_GEOMETRY.getBytes())));
    }

    @RequiredArgsConstructor
    private static class AbilityTask implements Runnable {
        private final EntityHuman entityHuman;
        private final Vector3 directionVector;
        private final int spawnTick = Server.getInstance().getTick();

        @Override
        public void run() {
            if(this.entityHuman.isClosed()) {
                TaskExecutor.cancel();
                return;
            }

            final int currentTick = Server.getInstance().getTick();
            if((currentTick - this.spawnTick) > 200) {
                this.entityHuman.close();
                TaskExecutor.cancel();
                return;
            }

            final Level level = this.entityHuman.getLevel();
            final AxisAlignedBB bb = this.entityHuman.getBoundingBox();
            bb.setMinY(bb.getMinY() - 0.28);
            bb.setMaxY(bb.getMaxY() - 1.28);
            if(!Arrays.stream(level.getCollisionBlocks(bb)).allMatch(Block::canPassThrough)) {
                Arrays.stream(level.getNearbyEntities(bb.grow(1.5, 1.5, 1.5), this.entityHuman)).
                        filter(entity -> entity instanceof Player).
                        map(entity -> (Player) entity).
                        forEach(player -> player.knockBack(this.entityHuman, 0, player.x - this.entityHuman.x, player.z - this.entityHuman.z, 0.5));

                final EntityFirework entityFirework = new EntityFirework(this.entityHuman.getChunk(), Entity.getDefaultNBT(this.entityHuman));
                try {
                    final Field lifetimeField = EntityFirework.class.getDeclaredField("lifetime");
                    lifetimeField.setAccessible(true);
                    lifetimeField.set(entityFirework, 1);
                } catch(NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                final ItemFirework itemFirework = new ItemFirework();
                itemFirework.clearExplosions();
                itemFirework.addExplosion(new ItemFirework.FireworkExplosion().
                        addColor(COLORS[GameHolder.getGameServer().getRandom().nextInt(COLORS.length)]).
                        type(ItemFirework.FireworkExplosion.ExplosionType.SMALL_BALL));
                entityFirework.setFirework(itemFirework);
                entityFirework.spawnToAll();
                this.entityHuman.close();
                TaskExecutor.cancel();
            }

            this.entityHuman.teleport(Location.fromObject(
                    this.entityHuman.add(this.directionVector),
                    level,
                    this.entityHuman.yaw + 5, 0));
        }
    }

}

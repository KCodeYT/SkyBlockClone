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

package ms.kevi.skyblock.entity.npc;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MovePlayerPacket;
import lombok.Setter;
import ms.kevi.skyblock.util.Language;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class EntityNPC extends EntityHuman {

    private final Map<UUID, Long> coolDowns;
    @Setter
    private Function<Player, Boolean> playerFunction = player -> false;
    private boolean lookAtPlayers = true;

    public EntityNPC(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.coolDowns = new HashMap<>();
    }

    public static EntityNPC create(String nameTag, boolean lookAtPlayers, Position position, BufferedImage skinImage) {
        final CompoundTag baseTag = Entity.getDefaultNBT(position);
        final Skin skin = new Skin();
        skin.setSkinData(skinImage);
        final CompoundTag skinTag = new CompoundTag("Skin");
        skinTag.putString("ModelId", "EntityNPC." + UUID.randomUUID().toString().replace("-", ""));
        skinTag.putByteArray("Data", skin.getSkinData().data);
        baseTag.put("Skin", skinTag);
        baseTag.putString("NameTag", nameTag);
        baseTag.putBoolean("LookAtPlayers", lookAtPlayers);
        return new EntityNPC(position.getLevel().getChunk(position.getChunkX(), position.getChunkZ()), baseTag);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.lookAtPlayers = this.namedTag.getBoolean("LookAtPlayers");
    }

    private boolean applyPlayerFunction(Player player) {
        if(this.coolDowns.containsKey(player.getUniqueId())) {
            if(this.coolDowns.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.sendMessage(Language.get("NPC_WAIT_LITTLE_BIT"));
                return false;
            }
        }

        this.coolDowns.put(player.getUniqueId(), System.currentTimeMillis() + 2000);
        return this.playerFunction.apply(player);
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        return this.applyPlayerFunction(player);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if(source instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) source).getDamager() instanceof Player)
            return this.applyPlayerFunction((Player) ((EntityDamageByEntityEvent) source).getDamager());
        return super.attack(source);
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(this.lookAtPlayers) {
            for(Player player : this.hasSpawned.values()) {
                final double yaw = ((Math.atan2(player.z - this.z, player.x - this.x) * 180) / Math.PI) - 90;
                final double pitch = ((Math.atan2(new Vector2(this.x, this.z).distance(player.x, player.z), player.y - this.y) * 180) / Math.PI) - 90;
                final MovePlayerPacket pk = new MovePlayerPacket();
                pk.eid = this.getId();
                pk.x = (float) this.x;
                pk.y = (float) this.y + this.getEyeHeight();
                pk.z = (float) this.z;
                pk.yaw = (float) yaw;
                pk.headYaw = (float) yaw;
                pk.pitch = (float) pitch;
                player.dataPacket(pk);
            }
        }

        return super.onUpdate(currentTick);
    }

}

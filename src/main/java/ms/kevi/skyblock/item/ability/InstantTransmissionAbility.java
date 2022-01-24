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
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.MovePlayerPacket;

import java.util.ArrayList;
import java.util.List;

public class InstantTransmissionAbility implements IAbility {

    @Override
    public String getDisplayName() {
        return "Instant Transmission";
    }

    @Override
    public String getDescription() {
        return "§7Teleport §a8 blocks§7 ahead of you and gain §a+50 §fSpeed§7 for §a3 seconds§7.";
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
        return 50;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Vector3 directionVector = new Vector3().add(player.getDirectionVector());
        final Location headLoc = player.floor().add(0, player.getEyeHeight(), 0);
        final Location fraction = player.subtract(player.floor());
        final List<Location> locations = new ArrayList<>();
        for(double d0 = -0.25; d0 <= 8; d0 += 0.05) {
            final Location location1 = headLoc.add(directionVector.multiply(d0)).add(fraction);
            final boolean bool1 = location1.getLevelBlock().canPassThrough();
            if(!bool1) {
                if(locations.size() - 22 < 0)
                    locations.clear();
                else
                    for(int i = 0; i < 22; i++)
                        locations.remove(locations.size() - 1);
                break;
            }

            locations.add(location1);
        }

        if(locations.isEmpty()) {
            player.sendMessage("§cThere are blocks in the way!");
            return false;
        }

        final Location location = locations.get(locations.size() - 1);
        final MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.eid = player.getId();
        movePlayerPacket.x = (float) location.x;
        movePlayerPacket.y = (float) location.y + player.getEyeHeight() + 0.0001F;
        movePlayerPacket.z = (float) location.z;
        movePlayerPacket.yaw = (float) player.yaw;
        movePlayerPacket.headYaw = (float) player.yaw;
        movePlayerPacket.pitch = (float) player.pitch;
        player.dataPacket(movePlayerPacket);
        player.getViewers().values().forEach(v -> v.dataPacket(movePlayerPacket));
        return true;
    }

}

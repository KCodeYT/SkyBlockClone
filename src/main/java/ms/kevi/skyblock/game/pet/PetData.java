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

package ms.kevi.skyblock.game.pet;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ms.kevi.skyblock.SkyBlockPlugin;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.ShortTermMemory;
import ms.kevi.skyblock.util.Utils;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PetData implements Comparable<PetData> {

    public static final String TAG_NAME = "PetData";
    private final IPetType type;
    private final GameRarity rarity;
    private double experience;

    public static PetData fromJson(String jsonString) {
        final Map<String, Object> map = SkyBlockPlugin.GSON.<Map<String, Object>>fromJson(jsonString, Map.class);
        return new PetData(Registries.PETS.valueOfNonNull((String) map.get("type")), GameRarity.valueOf((String) map.get("tier")), (Double) map.get("exp"));
    }

    public static PetData of(IPetType petType, GameRarity rarity, double experience) {
        return new PetData(petType, rarity, experience);
    }

    Entity createPet(Player player) {
        final CompoundTag petTag = new CompoundTag();
        petTag.putLong("PetOwnerUniqueIdMost", player.getUniqueId().getMostSignificantBits());
        petTag.putLong("PetOwnerUniqueIdLeast", player.getUniqueId().getLeastSignificantBits());
        final byte[] uniqueId = Utils.randomBytes(16);
        petTag.putByteArray("PetData", uniqueId);
        ShortTermMemory.store(uniqueId, this);

        final Location spawnVector = player.floor().add(0.5, player.getEyeHeight(), 0.5);
        spawnVector.yaw = GameHolder.getGameServer().getRandom().nextDouble() * 360;
        spawnVector.pitch = 0;
        return this.type.getEntityCreator().create(spawnVector.getChunk(), Entity.getDefaultNBT(spawnVector).putCompound(TAG_NAME, petTag));
    }

    public String toJson() {
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", this.type.name());
        map.put("tier", this.rarity.name());
        map.put("exp", this.experience);
        return SkyBlockPlugin.GSON.toJson(map);
    }

    public String getDisplayName() {
        return this.rarity.getColorCode() + this.type.getDisplayName();
    }

    public void addExperience(double experience) {
        this.experience += experience;
    }

    public int getLevel() {
        return PetLevelData.getLevel(this);
    }

    private double calcScore() {
        return (this.rarity.getId() * PetLevelData.getExperience(this.rarity, this.type.getMaxLevel())) + this.experience;
    }

    @Override
    public int compareTo(PetData o) {
        final double s1, s2;
        return (s1 = this.calcScore()) == (s2 = o.calcScore()) ?
                this.type.getDisplayName().compareToIgnoreCase(o.type.getDisplayName()) :
                Double.compare(s1 * -1, s2 * -1);
    }

}

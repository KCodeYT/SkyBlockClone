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

package ms.kevi.skyblock.game.pet.type;

import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.CriticalParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.Getter;
import ms.kevi.skyblock.entity.EntityType;
import ms.kevi.skyblock.entity.pet.EntityFlyingSkullPet;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.pet.*;
import ms.kevi.skyblock.game.skill.SkillType;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class BeePetType implements IPetType {

    private static final BufferedImage SKIN = ImageUtil.readImage("http://textures.minecraft.net/texture/7e941987e825a24ea7baafab9819344b6c247c75c54a691987cd296bc163c263");

    private final EntityCreator<?> entityCreator = Entity::new;
    private final String displayName = "Bee";
    private final SkillType skillType = SkillType.FARMING;
    private final List<GameRarity> availableRarities = Arrays.asList(GameRarity.COMMON, GameRarity.UNCOMMON, GameRarity.RARE, GameRarity.EPIC, GameRarity.LEGENDARY);
    private final int maxLevel = 100;

    @Override
    public StatsBooster getStatsBooster(PetData petData) {
        final int level = petData.getLevel();
        return StatsBooster.create().
                put(GameStats.INTELLIGENCE, level / 2).
                put(GameStats.SPEED, level / 10).
                put(GameStats.STRENGTH, 5 + level / 4);
    }

    @Override
    public List<IPetAbility> getPetAbilities(PetData petData) {
        final List<IPetAbility> petAbilities = new ArrayList<>(Collections.singletonList(PetAbilities.HIVE));
        if(petData.getRarity().ordinal() >= GameRarity.RARE.ordinal())
            petAbilities.add(PetAbilities.BUSY_BUZZ_BUZZ);
        if(petData.getRarity().ordinal() >= GameRarity.LEGENDARY.ordinal())
            petAbilities.add(PetAbilities.WEAPONIZED_HONEY);
        return petAbilities;
    }

    @Override
    public Item getItem(PetData petData) {
        return Item.get(Item.SPAWN_EGG, EntityType.BEE.getNetworkId(), 1);
    }

    private static class Entity extends EntityFlyingSkullPet {

        private Entity(FullChunk chunk, CompoundTag nbt) {
            super(chunk, EntityFlyingSkullPet.addSkin(BeePetType.SKIN, nbt));
        }

        @Override
        protected void handleParticles(Vector3 lastVector) {
            this.level.addParticle(new CriticalParticle(lastVector.add(0, 0.05, 0)));
        }

    }

}

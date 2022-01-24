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
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.level.particle.SmokeParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.Getter;
import ms.kevi.skyblock.entity.pet.EntityFlyingSkullPet;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.pet.*;
import ms.kevi.skyblock.game.skill.SkillType;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.util.Color;
import ms.kevi.skyblock.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
public class EnderDragonPetType implements IPetType {

    private static final BufferedImage SKIN = ImageUtil.readImage("http://textures.minecraft.net/texture/c279dc91373b427769043fae889ce2add3ae32166496534a4d6a8a8aaa2d");

    private final EntityCreator<?> entityCreator = Entity::new;
    private final String displayName = "Ender Dragon";
    private final SkillType skillType = SkillType.COMBAT;
    private final List<GameRarity> availableRarities = Arrays.asList(GameRarity.EPIC, GameRarity.LEGENDARY);
    private final int maxLevel = 100;

    @Override
    public StatsBooster getStatsBooster(PetData petData) {
        final int level = petData.getLevel();
        return StatsBooster.create().
                put(GameStats.CRITICAL_CHANCE, level / 10).
                put(GameStats.CRITICAL_DAMAGE, level / 2).
                put(GameStats.STRENGTH, level / 2);
    }

    @Override
    public List<IPetAbility> getPetAbilities(PetData petData) {
        final List<IPetAbility> petAbilities = new ArrayList<>(Arrays.asList(PetAbilities.END_STRIKE, PetAbilities.ONE_WITH_THE_DRAGONS));
        if(petData.getRarity().ordinal() >= GameRarity.LEGENDARY.ordinal())
            petAbilities.add(PetAbilities.SUPERIOR);
        return petAbilities;
    }

    @Override
    public Item getItem(PetData petData) {
        return Item.get(Item.DRAGON_EGG, 0, 1);
    }

    private static class Entity extends EntityFlyingSkullPet {

        private Entity(FullChunk chunk, CompoundTag nbt) {
            super(chunk, EntityFlyingSkullPet.addSkin(EnderDragonPetType.SKIN, nbt));
        }

        @Override
        protected void handleParticles(Vector3 lastVector) {
            this.level.addParticle(new SmokeParticle(lastVector.subtract(0, 0.05, 0)));
            final Random random = GameHolder.getGameServer().getRandom();
            if(random.nextInt(5) <= 2) {
                final int rnd = random.nextInt(70);
                this.level.addParticle(new GenericParticle(lastVector.subtract(0, 0.05, 0), GenericParticle.TYPE_WITCH_SPELL, new Color(120 + rnd, 0, 120 + rnd).toARGB()));
            }
        }

    }

}

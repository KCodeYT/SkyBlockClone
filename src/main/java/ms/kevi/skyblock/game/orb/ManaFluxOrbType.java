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

package ms.kevi.skyblock.game.orb;

import cn.nukkit.Player;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.DyeColor;
import lombok.Getter;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Getter
public class ManaFluxOrbType implements IOrbType {

    private final String name = "Mana Flux";
    private final String color = "§9";
    private final String skinTexture = "82ada1c7fcc8cf35defeb944a4f8ffa9a9d260560fc7f5f5826de8085435967c";
    private final long time = TimeUnit.SECONDS.toMillis(30);
    private final int maxPlayers = 5;
    private final int playerRadius = 18;
    private final GameRarity rarity = GameRarity.UNCOMMON;
    private final int itemId = ItemID.DYE;
    private final int itemDamage = DyeColor.LIME.getDyeData();
    private final IOrbBuff orbBuff = new IOrbBuff() {
        @Override
        public StatsBooster getStatsBooster() {
            return StatsBooster.create().
                    put(GameStats.STRENGTH, 10).
                    putRegeneration(GameStats.HEALTH, 2).
                    putRegeneration(GameStats.INTELLIGENCE, 50);
        }

        @Override
        public String[] getDescription() {
            return new String[]{
                    "§7Grants §b+50% §7base mana regen.",
                    "§7Heals §c2% §7of max §cHealth §7per second.",
                    "§7Grants §c+10 Strength§7."
            };
        }
    };

    @Override
    public void addPlayerParticles(Random random, Player player) {
        this.addParticle(player.getLevel(), player.temporalVector.setComponents(
                player.getX() + (-0.5 + random.nextFloat()),
                player.getY() + (random.nextFloat() / 2),
                player.getZ() + (-0.5 + random.nextFloat())
        ));
    }

    @Override
    public void addOrbParticles(Random random, Location location) {
        this.addParticle(location.getLevel(), location.add(location.getDirectionVector().multiply(0.5)));
    }

    private void addParticle(Level level, Vector3 vector3) {
        level.addParticle(new GenericParticle(vector3.add(0, 0.25, 0), Particle.TYPE_FALLING_DUST, ((0xff) << 24) | ((92 & 0xff) << 16) | ((133 & 0xff) << 8) | (181 & 0xff)));
    }

}

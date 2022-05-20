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
import lombok.Getter;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Getter
public class OverfluxOrbType implements IOrbType {

    private final String name = "Overflux";
    private final String color = "§5";
    private final String skinTexture = "b561595d9c7457796c719fae463a22271cbc01cf10809f5a64ccb3d6ae7f8f6";
    private final long time = TimeUnit.SECONDS.toMillis(60);
    private final int maxPlayers = 5;
    private final int playerRadius = 18;
    private final GameRarity rarity = GameRarity.EPIC;
    private final int itemId = ItemID.REDSTONE_DUST;
    private final int itemDamage = 0;
    private final IOrbBuff orbBuff = new IOrbBuff() {
        @Override
        public StatsBooster getStatsBooster() {
            return StatsBooster.create().
                    put(GameStats.STRENGTH, 25).
                    putRegeneration(GameStats.HEALTH, 2.5).
                    putRegeneration(GameStats.INTELLIGENCE, 100);
        }

        @Override
        public String[] getDescription() {
            return new String[]{
                    "§7Grants §b+100% §7base mana regen.",
                    "§7Heals §c2.5% §7of max §cHealth §7per second.",
                    "§7Increases all heals by §a+5%§7.",
                    "§7Grants §c+25 Strength§7."
            };
        }
    };

    @Override
    public void addPlayerParticles(Random random, Player player) {
        this.addParticle(this.getColor(random.nextBoolean()), player.getLevel(), player.temporalVector.setComponents(
                player.getX() + (-0.5 + random.nextFloat()),
                player.getY() + (random.nextFloat() / 2),
                player.getZ() + (-0.5 + random.nextFloat())
        ));
    }

    @Override
    public void addOrbParticles(Random random, Location location) {
        final Level level = location.getLevel();
        final Vector3 directionVector = location.getDirectionVector();
        this.addParticle(this.getColor(true), level, location.add(directionVector.multiply(0.5)));
        this.addParticle(this.getColor(false), level, location.add(directionVector.multiply(-0.5)));
    }

    private int getColor(boolean red) {
        return red ? ((0xff) << 24) | ((113 & 0xff) << 16) | (18 & 0xff) : ((0xff) << 24) | ((8 & 0xff) << 16) | (27 & 0xff);
    }

    private void addParticle(int color, Level level, Vector3 vector3) {
        level.addParticle(new GenericParticle(vector3.add(0, 0.25, 0), Particle.TYPE_FALLING_DUST, color));
    }

}

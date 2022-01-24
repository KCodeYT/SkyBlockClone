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

package ms.kevi.skyblock.item.custom;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.particle.FlameParticle;
import cn.nukkit.math.Vector3;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.ability.InteractionType;
import ms.kevi.skyblock.item.ability.ItemAbility;
import ms.kevi.skyblock.item.registry.BuildableGameItem;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.VectorUtil;

public class AspectOfTheDragonsItem {

    public static final IGameItem INSTANCE = BuildableGameItem.builder().
            attributeId("ASPECT_OF_THE_DRAGONS").
            displayName("Aspect of the Dragons").
            rarity(GameRarity.LEGENDARY).itemType(ItemType.SWORD).
            id(Item.DIAMOND_SWORD).damage(0).
            modifiable(true).
            stackSize(1).
            statsBooster(StatsBooster.create().
                    put(GameStats.DAMAGE, 225).
                    put(GameStats.STRENGTH, 100)).
            ability(ItemAbility.builder().
                    displayName("Dragon Rage").
                    description("§7All Monsters in front of you take §a404§7 damage. Hit monsters take large knockback.").
                    interactionType(InteractionType.CLICK).
                    intelligenceCost(100).
                    onInteract(event -> {
                        final Player player = event.getPlayer();
                        final Level level = player.getLevel();
                        final Vector3 headVector = player.add(0, player.getEyeHeight(), 0);

                        int i = 0;
                        for(double c = 2 * Math.PI; c <= 8 * Math.PI; c += (Math.PI / 10D)) {
                            final double size = c - Math.PI;
                            final Vector3 finalVector = headVector.add(VectorUtil.getRelativeToDirection(
                                    player.yaw, player.pitch,
                                    Math.sin(1.2 * c) * size * 0.0465625D,
                                    Math.cos(1.2 * c) * size * 0.0465625D,
                                    size * 0.07625D
                            ));

                            TaskExecutor.delayed(() -> level.addParticle(new FlameParticle(finalVector)), i++ / 6);
                        }

                        return true;
                    }).build()).
            build();

}

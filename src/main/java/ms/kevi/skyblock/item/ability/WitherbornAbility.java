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
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.mob.EntityWither;
import cn.nukkit.math.Vector3;
import ms.kevi.skyblock.entity.IDamageable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class WitherbornAbility implements FullSetBonus0 {

    private static final int SECONDS_TO_SPAWN = 30;
    private static final double HEIGHT = 0.02;
    private static final Map<Player, TaskData> TASKS = new HashMap<>();

    @Override
    public String getDisplayName() {
        return "Witherborn";
    }

    @Override
    public String getDescription() {
        return "§7Spawns a wither minion every §e" + SECONDS_TO_SPAWN + " §7seconds up to a maximum §a1 §7wither. " +
                "Your withers will travel to and explode on nearby enemies.\n" +
                "§7Reduces the damage you take from withers by §c10%§7.";
    }

    @Override
    public void onActivate(Player player) {
        TASKS.put(player, new TaskData());
    }

    @Override
    public void onDeactivate(Player player) {
        final TaskData taskData = TASKS.remove(player);
        if(taskData != null) {
            if(taskData.entityWither != null)
                taskData.entityWither.close();
            taskData.tasks.clear();
        }
    }

    @Override
    public void onTick(Player player, int currentTick) {
        final TaskData taskData = TASKS.get(player);
        if(taskData.entityWither == null) {
            if(taskData.canSpawn()) {
                taskData.entityWither = new EntityWither(player.getChunk(), Entity.getDefaultNBT(new Vector3(0, HEIGHT, 0).add(player)));
                taskData.entityWither.setScale(0.1F);
                taskData.entityWither.setHealth(taskData.entityWither.getMaxHealth());
                taskData.entityWither.spawnToAll();
            }
        }

        if(taskData.entityWither != null) {
            final EntityWither wither = taskData.entityWither;
            final Optional<Entity> optionalEntity = Arrays.
                    stream(wither.getLevel().getNearbyEntities(wither.getBoundingBox().expand(0.75, 0.75, 0.75), wither)).
                    filter(entity -> entity instanceof IDamageable).
                    findAny();
            if(optionalEntity.isPresent()) {
                taskData.calcNext();
                taskData.tasks.clear();
                taskData.entityWither = null;
                optionalEntity.get().kill();
                wither.close();
                return;
            }

            final int times = 120;
            final double radius = 1.33;

            if(taskData.tasks.isEmpty()) {
                for(int i = 0; i < times; i++) {
                    final double angle = 2 * Math.PI * i / times;
                    final Vector3 pos = new Vector3(Math.cos(angle) * radius, HEIGHT, Math.sin(angle) * radius);
                    taskData.tasks.add(() -> wither.teleport(pos.add(player)));
                }
            }

            taskData.tasks.remove(0).run();
        }
    }

    private static class TaskData {

        private final List<Runnable> tasks = new ArrayList<>();

        private EntityWither entityWither;
        private long spawnTime;

        private TaskData() {
            this.calcNext();
        }

        private boolean canSpawn() {
            return this.spawnTime < System.currentTimeMillis();
        }

        private void calcNext() {
            this.spawnTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(SECONDS_TO_SPAWN);
        }

    }

}

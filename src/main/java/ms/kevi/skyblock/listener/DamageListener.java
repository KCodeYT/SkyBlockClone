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

package ms.kevi.skyblock.listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Location;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import ms.kevi.skyblock.entity.IDamageable;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.attribute.GameAttributes;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.enchantment.*;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.scheduler.TaskExecutor;

import java.util.List;
import java.util.Random;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);

        final EntityDamageEvent.DamageCause cause = event.getCause();
        final float baseDamage = event.getOriginalDamage();

    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        event.setCancelled(true);

        final EntityDamageEvent.DamageCause damageCause = event.getCause();
        final Entity entity = event.getEntity();
        final Entity damager = event.getDamager();

        if(damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if(!(damager instanceof Player))
                return;
            if(((Player) damager).getInventory().getItemInHand().getId() == ItemID.SPAWN_EGG) {
                entity.close();
                return;
            }
            if(!(entity instanceof IDamageable))
                return;

            final Random random = GameHolder.getGameServer().getRandom();
            final Player player = (Player) damager;
            final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
            final GameAttributes gameAttributes = skyBlockPlayer.getGameAttributes();

            final Vector3 textPos = entity.add(new Location(
                    0, 0, 0,
                    ((Math.atan2(player.z - entity.z, player.x - entity.x) * 180) / Math.PI) - 90,
                    ((Math.atan2(new Vector2(entity.x, entity.z).distance(player.x, player.z), player.y - entity.y) * 180) / Math.PI) - 90
            ).getDirectionVector().multiply(1.35)).add(
                    (random.nextBoolean() ? 0.5 : -0.5) * random.nextFloat(),
                    player.getEyeHeight() / 1.75 + ((random.nextBoolean() ? 0.1 : -0.1) * random.nextFloat()),
                    (random.nextBoolean() ? 0.5 : -0.5) * random.nextFloat());


            final int damageAttr = gameAttributes.get(GameStats.DAMAGE).getValue();
            final int strengthAttr = gameAttributes.get(GameStats.STRENGTH).getValue();
            final int criticalDamageAttr = gameAttributes.get(GameStats.CRITICAL_DAMAGE).getValue();
            final int criticalChanceAttr = gameAttributes.get(GameStats.CRITICAL_CHANCE).getValue();

            float damage = ((5 + damageAttr + (strengthAttr / 5f)) * (1 + (strengthAttr / 100f)));
            float enchantmentDmg = 0;

            final Item item = player.getInventory().getItemInHand();
            final IGameItem gameItem = Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item));
            if(gameItem != null) {
                final List<Enchantment> enchantments = ItemHelper.getEnchantments(item);
                if(enchantments != null && !enchantments.isEmpty()) {
                    for(Enchantment enchantment : enchantments) {
                        if(enchantment instanceof SharpnessEnchantment)
                            enchantmentDmg += 0.05 * enchantment.getLevel();
                        if(enchantment instanceof SmiteEnchantment || enchantment instanceof BaneOfArthropodsEnchantment) // TODO: ONLY ZOMBIES AND SPIDERS
                            enchantmentDmg += 0.08 * enchantment.getLevel();
                        if(enchantment instanceof GiantKillerEnchantment) // TODO: REPLACE HEALTH
                            enchantmentDmg += Math.min(0.25, ((13000 - 2153) / 2153f) * (0.1 * enchantment.getLevel() * 100));
                        if(enchantment instanceof EnderSlayerEnchantment) // TODO: ONLY END CREATURES
                            enchantmentDmg += 0.12 * enchantment.getLevel();
                        if(enchantment instanceof DragonHunterEnchantment) // TODO: ONLY DRAGONS
                            enchantmentDmg += 0.08 * enchantment.getLevel();
                        if(enchantment instanceof ExecuteEnchantment) // TODO: REPLACE HEALTH
                            enchantmentDmg += ((2154 - 2153) / 2154f) * (0.0002 * enchantment.getLevel() * 100);
                        if(enchantment instanceof CubismEnchantment)
                            enchantmentDmg += 0.1 * enchantment.getLevel();
                        if(enchantment instanceof FirstStrikeEnchantment) // TODO: ONLY ON FIRST HIT
                            enchantmentDmg += 0.25 * enchantment.getLevel();
                        if(enchantment instanceof ImpalingEnchantment) // TODO: ONLY SEA CREATURES
                            enchantmentDmg += 0.125 * enchantment.getLevel();
                    }
                }
            }

            final boolean isCritical = random.nextInt(101) <= criticalChanceAttr;
            final int finalDamage = (int) Math.floor(damage * (1 + enchantmentDmg) * (isCritical ? (1 + (criticalDamageAttr / 100f)) : 1));
            final FloatingTextParticle textParticle = new FloatingTextParticle(textPos, (isCritical ? addCriticalColors(finalDamage) : "§7" + finalDamage));
            player.getLevel().addParticle(textParticle, player);
            textParticle.setInvisible(true);
            TaskExecutor.delayed(() -> player.getLevel().addParticle(textParticle, player), random.nextInt(15) + 5);
        }
    }

    private String addCriticalColors(int finalDamage) {
        final char[] colors = {'f', 'e', '6', 'c'};
        final char[] charArray = String.valueOf(finalDamage).toCharArray();
        final StringBuilder builder = new StringBuilder();
        char lastColor = ' ';
        for(int i = 0; i < charArray.length; i++) {
            final char color = i >= colors.length ? colors[colors.length - 1] : colors[i];
            if(color != lastColor)
                builder.append(TextFormat.ESCAPE).append(color);
            lastColor = color;
            builder.append(charArray[i]);
        }
        return "\u00a77\u00bb " + builder + " \u00a77\u00ab";
    }

}

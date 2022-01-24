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

import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.attribute.Attribute;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.player.event.IntelligenceConsumeEvent;
import ms.kevi.skyblock.game.player.event.Listener;
import ms.kevi.skyblock.game.stats.GameStats;

public class DragonBloodAbilities {

    private static final Listener<IntelligenceConsumeEvent> WISE_BLOOD_LISTENER = event -> event.setIntelligence(event.getIntelligence() - (event.getIntelligence() / 3));

    public static final FullSetBonus SUPERIOR = FullSetBonus.builder().
            displayName("Superior Blood").
            description("§7Most of your stats are increased by §a5%§7 and §6Aspect of the §6Dragons§7 ability deals §a50%§7 more damage.").
            statsBooster(StatsBooster.create().putPercent(GameStats.values(), 5)).
            build();
    public static final FullSetBonus WISE = FullSetBonus.builder().
            displayName("Wise Blood").
            description("§7Abilities have §a2/3§7 of the mana cost.").
            onActivate(player -> GameHolder.getPlayer(player).getEventManager().on(IntelligenceConsumeEvent.class, WISE_BLOOD_LISTENER)).
            onDeactivate(player -> GameHolder.getPlayer(player).getEventManager().remove(WISE_BLOOD_LISTENER)).
            build();
    public static final FullSetBonus YOUNG = FullSetBonus.builder().
            displayName("Young Blood").
            description("§7Gain §a+70§7 Walk Speed while you are above §a50% §7HP\n§8+100 Walk Speed Cap").
            onTick((player, tick) -> {
                final Attribute speed = GameHolder.getPlayer(player).getGameAttributes().get(GameStats.SPEED);
                speed.setForcedMaxValue(speed.getForcedMaxValue() + 100);
                speed.setMaxValue(speed.getForcedMaxValue());

                final Attribute health = GameHolder.getPlayer(player).getGameAttributes().get(GameStats.HEALTH);
                if(health.getValue() >= health.getMaxValue() / 2)
                    speed.setValue(health.getValue() + 70, true);
            }).
            build();
    public static final FullSetBonus PROTECTOR = FullSetBonus.builder().
            displayName("Protective Blood").
            description("§7Increases the defense of each armor piece by §a+1% Defense§7 for each missing percent of HP.").
            modifyItemBooster(booster -> {
                //final Attribute health = GameHolder.getPlayer(player).getGameAttributes().get(GameStats.HEALTH);
                //final int percent = (int) (health.getValue() / (double) health.getMaxValue()) * 100;
                return booster;
            }).
            build();

}

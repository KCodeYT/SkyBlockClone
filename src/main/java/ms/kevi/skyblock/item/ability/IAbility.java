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
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerToggleSneakEvent;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.registry.Registrable;
import ms.kevi.skyblock.registry.Registries;

public interface IAbility extends Registrable {

    String getDisplayName();

    String getDescription();

    AbilityType getType();

    InteractionType getInteractionType();

    default StatsBooster getStatsBooster() {
        return null;
    }

    default int getIntelligenceCost() {
        return 0;
    }

    default double getCoolDown() {
        return 0;
    }

    default void onActivate(Player player) {

    }

    default void onDeactivate(Player player) {

    }

    default void onTick(Player player, int currentTick) {

    }

    default StatsBooster modifyItemBooster(StatsBooster booster) {
        return booster;
    }

    default boolean onInteract(PlayerInteractEvent event) {
        return false;
    }

    default boolean onSneak(PlayerToggleSneakEvent event) {
        return false;
    }

    default boolean onDrop(PlayerDropItemEvent event) {
        return false;
    }

    @Override
    default String name() {
        return Registries.ITEM_ABILITIES.nameOf(this);
    }

}

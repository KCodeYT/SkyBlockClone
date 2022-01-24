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
import lombok.Builder;
import lombok.Getter;
import ms.kevi.skyblock.game.booster.StatsBooster;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Builder
public class FullSetBonus implements IAbility {

    private final AbilityType type = AbilityType.ITEM;
    private final InteractionType interactionType = InteractionType.NONE;
    private final String displayName;
    private final String description;
    private final StatsBooster statsBooster;
    private final int intelligenceCost;
    private final double coolDown;
    private final Consumer<Player> onActivate;
    private final Consumer<Player> onDeactivate;
    private final BiConsumer<Player, Integer> onTick;
    private final Function<StatsBooster, StatsBooster> modifyItemBooster;
    private final Function<PlayerInteractEvent, Boolean> onInteract;
    private final Function<PlayerToggleSneakEvent, Boolean> onSneak;
    private final Function<PlayerDropItemEvent, Boolean> onDrop;

    @Override
    public void onActivate(Player player) {
        if(this.onActivate != null)
            this.onActivate.accept(player);
    }

    @Override
    public void onDeactivate(Player player) {
        if(this.onDeactivate != null)
            this.onDeactivate.accept(player);
    }

    @Override
    public void onTick(Player player, int currentTick) {
        if(this.onTick != null)
            this.onTick.accept(player, currentTick);
    }

    @Override
    public StatsBooster modifyItemBooster(StatsBooster booster) {
        if(this.modifyItemBooster != null)
            return this.modifyItemBooster.apply(booster);
        return booster;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent event) {
        if(this.onInteract != null)
            return this.onInteract.apply(event);
        return false;
    }

    @Override
    public boolean onSneak(PlayerToggleSneakEvent event) {
        if(this.onSneak != null)
            return this.onSneak.apply(event);
        return false;
    }

    @Override
    public boolean onDrop(PlayerDropItemEvent event) {
        if(this.onDrop != null)
            return this.onDrop.apply(event);
        return false;
    }

}

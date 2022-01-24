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
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.player.event.IntelligenceConsumeEvent;
import ms.kevi.skyblock.game.player.event.Listener;

public class WiseBloodAbility implements FullSetBonus0 {

    private static final Listener<IntelligenceConsumeEvent> LISTENER = event -> event.setIntelligence(event.getIntelligence() - (event.getIntelligence() / 3));

    @Override
    public String getDisplayName() {
        return "Wise Blood";
    }

    @Override
    public String getDescription() {
        return "§7Abilities have §a2/3 §7of the mana cost.";
    }

    @Override
    public void onActivate(Player player) {
        GameHolder.getPlayer(player).getEventManager().on(IntelligenceConsumeEvent.class, LISTENER);
    }

    @Override
    public void onDeactivate(Player player) {
        GameHolder.getPlayer(player).getEventManager().remove(LISTENER);
    }

}

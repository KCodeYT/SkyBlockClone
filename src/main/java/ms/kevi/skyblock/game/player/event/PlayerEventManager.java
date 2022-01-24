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

package ms.kevi.skyblock.game.player.event;

import ms.kevi.skyblock.game.attribute.Attribute;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.game.stats.GameStats;

import java.util.*;

public class PlayerEventManager {

    private final Map<Class<PlayerEvent>, List<Listener<PlayerEvent>>> listeners;
    private final SkyBlockPlayer player;

    public PlayerEventManager(SkyBlockPlayer player) {
        this.listeners = new HashMap<>();
        this.player = player;
        this.defaults();
    }

    private void defaults() {
        this.on(IntelligenceConsumeEvent.class, event -> {
            final int intelligence = event.getIntelligence();
            final Attribute attribute = this.player.getGameAttributes().get(GameStats.INTELLIGENCE);
            final int current = attribute.getValue();
            if(current < intelligence) {
                event.setCancelled(true);
                return;
            }

            attribute.setValue(current - intelligence, true);
        });
    }

    public <E extends PlayerEvent> boolean run(E event) {
        final Class<? extends PlayerEvent> clazz = event.getClass();
        final List<Listener<PlayerEvent>> listeners = this.listeners.getOrDefault(clazz, Collections.emptyList());
        for(int i = listeners.size() - 1; i >= 0; i--)
            listeners.get(i).listen(event);
        return !event.isCancelled();
    }

    public <E extends PlayerEvent> void on(Class<E> clazz, Listener<E> listener) {
        //noinspection unchecked
        this.listeners.computeIfAbsent((Class<PlayerEvent>) clazz, x -> new ArrayList<>()).add((Listener<PlayerEvent>) listener);
    }

    public <E extends PlayerEvent> void remove(Listener<E> listener) {
        for(List<Listener<PlayerEvent>> listeners : this.listeners.values())
            if(listeners.remove(listener))
                return;
    }

}

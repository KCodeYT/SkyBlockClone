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

package ms.kevi.skyblock.util;

import cn.nukkit.Server;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginManager;
import ms.kevi.skyblock.SkyBlockPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class EventUtil {

    @SuppressWarnings("unchecked")
    public static <T extends Event> void registerEventHandler(Class<T> eventClass, EventPriority eventPriority, Function<T, Boolean> eventFunction) {
        final Listener listener = new Listener() {
        };
        Server.getInstance().getPluginManager().registerEvent(eventClass, listener, eventPriority, (listenerB, event) -> {
            if(!eventFunction.apply((T) event))
                getEventListeners(eventClass).unregister(listener);
        }, SkyBlockPlugin.getInstance());
    }

    private static HandlerList getEventListeners(Class<? extends Event> type) {
        try {
            final Method method = PluginManager.class.getDeclaredMethod("getEventListeners", Class.class);
            method.setAccessible(true);
            return (HandlerList) method.invoke(Server.getInstance().getPluginManager(), type);
        } catch(NullPointerException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("getHandlers method in " + type.getName() + " was not static!");
        }
    }

    public static void registerListener(Listener listener) {
        Server.getInstance().getPluginManager().registerEvents(listener, SkyBlockPlugin.getInstance());
    }

}

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

package ms.kevi.skyblock.registry;

import cn.nukkit.item.Item;
import com.google.gson.internal.LinkedTreeMap;
import ms.kevi.skyblock.item.IGameItem;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ItemRegistry {

    private static final ItemRegistry INSTANCE = new ItemRegistry();

    public static ItemRegistry get() {
        return INSTANCE;
    }

    private ItemRegistry() {
        if(INSTANCE != null) throw new UnsupportedOperationException();
    }

    private final Map<String, IGameItem> map = new LinkedTreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void register(IGameItem gameItem) {
        this.register(gameItem.getAttributeId(), gameItem);
    }

    public void register(String identifier, IGameItem gameItem) {
        this.map.put(identifier, gameItem);
    }

    public Collection<IGameItem> values() {
        return Collections.unmodifiableCollection(this.map.values());
    }

    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.map.keySet());
    }

    public String keyOf(IGameItem gameItem) {
        for(Map.Entry<String, IGameItem> entry : this.map.entrySet())
            if(entry.getValue().equals(gameItem))
                return entry.getKey();
        return null;
    }

    public IGameItem get(String identifier) {
        return this.map.get(identifier);
    }

    public IGameItem fromVanilla(Item item) {
        return this.fromVanilla(item.getId(), item.getDamage());
    }

    public IGameItem fromVanilla(int id, int damage) {
        for(IGameItem gameItem : this.values())
            if(gameItem.isVanilla() && gameItem.getId() == id && gameItem.getDamage() == damage)
                return gameItem;
        return null;
    }

}

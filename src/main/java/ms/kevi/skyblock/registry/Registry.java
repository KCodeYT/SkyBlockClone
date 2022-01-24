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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Registry<T extends Registrable> {

    protected final Map<T, Class<? extends Registry<T>>> registrarMap;
    protected final Map<T, String> map;

    @SuppressWarnings("unchecked")
    Registry() {
        final Class<? extends Registry<T>> clazz = (Class<? extends Registry<T>>) this.getClass();
        try {
            final Map<T, Class<? extends Registry<T>>> registrarMap = new LinkedHashMap<>();
            final Map<T, String> map = new LinkedHashMap<>();
            for(Field field : clazz.getDeclaredFields()) {
                final String fieldName = field.getName();
                if(Modifier.isPrivate(field.getModifiers())) {
                    if(Modifier.isStatic(field.getModifiers()) && fieldName.equals("SUB_REGISTRIES")) {
                        field.setAccessible(true);
                        for(Object o : (List<?>) field.get(null)) {
                            final Class<?> subRegistry = (Class<?>) o;
                            if(Registry.class.isAssignableFrom(subRegistry)) {
                                for(Field field1 : subRegistry.getDeclaredFields()) {
                                    final T cast = (T) field1.get(null);
                                    registrarMap.put(cast, (Class<? extends Registry<T>>) subRegistry);
                                    map.put(cast, field1.getName());
                                }
                            }
                        }
                    }
                    continue;
                }

                final T cast = (T) field.get(null);
                registrarMap.put(cast, clazz);
                map.put(cast, fieldName);
            }
            this.registrarMap = Collections.unmodifiableMap(registrarMap);
            this.map = Collections.unmodifiableMap(map);
        } catch(IllegalAccessException | ClassCastException e) {
            throw new RegistryException(e, clazz);
        }
    }

    public String nameOf(T value) {
        return Objects.requireNonNull(this.map.get(value), "No value " + value + " could be found");
    }

    public T valueOf(String name) {
        for(T value : this.map.keySet()) {
            if(value.checkName(name))
                return value;
        }
        return null;
    }

    public T valueOfNonNull(String name) {
        return Objects.requireNonNull(this.valueOf(name), "No value named " + name + " could be found");
    }

    public Set<String> nameSet() {
        return Collections.unmodifiableSet(new HashSet<>(this.map.values()));
    }

    public Collection<T> values() {
        return Collections.unmodifiableCollection(this.map.keySet());
    }

    public Class<? extends Registry<T>> registrarOf(T value) {
        return Objects.requireNonNull(this.registrarMap.get(value), "No registrar from value " + value + " could be found");
    }

}

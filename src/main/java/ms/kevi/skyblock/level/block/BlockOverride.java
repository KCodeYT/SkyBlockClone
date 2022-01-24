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

package ms.kevi.skyblock.level.block;

import cn.nukkit.block.Block;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface BlockOverride {

    static void register(Block block) {
        final Class<? extends Block> clazz = block.getClass();
        final int id = block.getId();
        Block.list[id] = clazz;

        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
            for(int data = 0; data < 16; ++data)
                Block.fullList[(id << 4) | data] = (Block) constructor.newInstance(data);
            Block.hasMeta[id] = true;
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignore) {
            for(int data = 0; data < 16; ++data)
                Block.fullList[(id << 4) | data] = block;
            Block.hasMeta[id] = false;
        }
    }

}

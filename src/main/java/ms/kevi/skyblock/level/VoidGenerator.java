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

package ms.kevi.skyblock.level;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class VoidGenerator extends Generator {

    private ChunkManager chunkManager;

    public VoidGenerator() {
        this(null);
    }

    public VoidGenerator(Map<String, Object> options) {
    }

    @Override
    public int getId() {
        return TYPE_FLAT;
    }

    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
        this.chunkManager = chunkManager;
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        FullChunk fullChunk = this.chunkManager.getChunk(chunkX, chunkZ);
        fullChunk.setGenerated();
        if(chunkX == 0 && chunkZ == 0)
            fullChunk.setBlock(0, 128, 0, 1, 0);

    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {

    }

    @Override
    public Map<String, Object> getSettings() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return "Void";
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0, 128, 0);
    }

    @Override
    public ChunkManager getChunkManager() {
        return this.chunkManager;
    }

}

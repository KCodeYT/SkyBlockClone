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

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.LevelProviderManager;
import cn.nukkit.level.format.anvil.Anvil;
import cn.nukkit.level.generator.Flat;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.Vector3;
import lombok.AllArgsConstructor;
import ms.kevi.skyblock.SkyBlockPlugin;
import ms.kevi.skyblock.game.server.ServerType;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LevelUtil {

    private static final Map<ServerType, TemplateData> TEMPLATE_MAP = new HashMap<>();

    static {
        TEMPLATE_MAP.put(ServerType.HUB, new TemplateData("Hub", new Vector3(-2.5, 70.5, -69.5), 52, true));
        TEMPLATE_MAP.put(ServerType.DYNAMIC, new TemplateData("Private Island", new Vector3(7.5, 100, 7.5), 10, false));
    }

    public static void init(SkyBlockPlugin plugin, ServerType serverType) {
        try {
            if(!TEMPLATE_MAP.containsKey(serverType))
                return;
            final TemplateData templateData = TEMPLATE_MAP.get(serverType);

            final Server server = Server.getInstance();
            LevelProviderManager.addProvider(server, Anvil.class);
            Generator.addGenerator(Flat.class, "flat", Generator.TYPE_FLAT);

            final File worldTemplatesDir = new File("world_templates");
            if(!worldTemplatesDir.exists()) {
                plugin.getLogger().warning("This server is running without the SkyBlock Clone template worlds!");
                return;
            }

            final File worldsDir = new File("worlds");

            final File levelDir = new File(worldTemplatesDir, templateData.levelName);
            final File tmpDir = new File(worldsDir, "temp-" + templateData.levelName);

            if(tmpDir.exists())
                FileUtils.deleteDirectory(tmpDir);
            FileUtils.copyDirectory(levelDir, tmpDir);

            final Level level;
            final String levelName = tmpDir.getName();
            if(!server.loadLevel(levelName) || (level = server.getLevelByName(levelName)) == null) {
                plugin.getLogger().warning("Could not find world by template!");
                return;
            }

            level.setAutoSave(false);
            server.setDefaultLevel(level);

            final Vector3 spawnVector = templateData.spawnVector;
            level.setSpawnLocation(spawnVector);
            final int chunkRadius = templateData.chunkRadius;
            final int centerX = spawnVector.getChunkX();
            final int centerZ = spawnVector.getChunkZ();
            final int radiusSqr = chunkRadius * chunkRadius;
            for(int x = -chunkRadius; x <= chunkRadius; x++) {
                for(int z = -chunkRadius; z <= chunkRadius; z++) {
                    if((x * x) + (z * z) > radiusSqr && templateData.circle)
                        continue;
                    final int cx = x + centerX, cz = z + centerZ;
                    TaskExecutor.async(() -> level.loadChunk(cx, cz));
                }
            }
        } catch(IOException e) {
            plugin.getLogger().error("Error whilst loading skyblock world!", e);
        }
    }

    @AllArgsConstructor
    private static class TemplateData {

        private final String levelName;
        private final Vector3 spawnVector;
        private final int chunkRadius;
        private final boolean circle;

    }

}

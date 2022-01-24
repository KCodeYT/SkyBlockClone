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

package ms.kevi.skyblock.game;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import ms.kevi.skyblock.SkyBlockPlugin;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.game.server.GameServer;
import ms.kevi.skyblock.game.server.ServerSize;
import ms.kevi.skyblock.game.server.ServerType;
import ms.kevi.skyblock.scheduler.TaskExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameHolder {

    private static final Map<UUID, SkyBlockPlayer> PLAYERS = new HashMap<>();
    private static final GameServer GAME_SERVER;

    static {
        final Config config = SkyBlockPlugin.getServerConfig();
        GAME_SERVER = new GameServer(ServerSize.valueOf(config.getString("ServerSize")), ServerType.valueOf(config.getString("ServerType")));
        TaskExecutor.repeating(() -> PLAYERS.values().forEach(SkyBlockPlayer::tick), 1);
    }

    public static GameServer getGameServer() {
        return GAME_SERVER;
    }

    public static void addPlayer(Player player) {
        final SkyBlockPlayer skyBlockPlayer = new SkyBlockPlayer(player);
        skyBlockPlayer.load();
        PLAYERS.put(player.getUniqueId(), skyBlockPlayer);
    }

    public static SkyBlockPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    public static SkyBlockPlayer getPlayer(UUID uniqueId) {
        return PLAYERS.get(uniqueId);
    }

    public static void removePlayer(Player player) {
        final SkyBlockPlayer skyBlockPlayer = getPlayer(player);
        if(skyBlockPlayer != null)
            skyBlockPlayer.save();
        PLAYERS.remove(player.getUniqueId());
    }

}

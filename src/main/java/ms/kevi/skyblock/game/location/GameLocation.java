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

package ms.kevi.skyblock.game.location;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.server.ServerType;
import ms.kevi.skyblock.util.MapUtil;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum GameLocation {

    NONE("§7None", new Vector3(0, 0, 0), 0, ServerType.NONE),
    PRIVATE_ISLAND("§aYour Island", new Vector3(0, 0, 0), 0, ServerType.DYNAMIC);

    private final String displayName;
    private final Vector3 position;
    private final int distance;
    private final ServerType allowedType;

    public static GameLocation findNearest(Player player) {
        final ServerType serverType = GameHolder.getGameServer().getType();
        final List<GameLocation> locations = Arrays.stream(values()).filter(gameLocation -> gameLocation.allowedType == serverType && gameLocation.position.distance(player) <= gameLocation.distance).collect(Collectors.toList());
        if(locations.size() == 0)
            return GameLocation.NONE;
        final Map<GameLocation, Double> distances = new HashMap<>();
        for(GameLocation location : locations)
            distances.put(location, location.position.distance(player));
        final List<GameLocation> sortList = new ArrayList<>(MapUtil.sortByValue(distances).keySet());
        return sortList.isEmpty() ? GameLocation.NONE : sortList.get(0);
    }

}

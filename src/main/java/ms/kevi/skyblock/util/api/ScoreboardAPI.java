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

package ms.kevi.skyblock.util.api;

import cn.nukkit.Player;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.date.GameCalender;
import ms.kevi.skyblock.game.location.GameLocation;
import ms.kevi.skyblock.util.Utils;
import ms.kevi.skyblock.util.scoreboard.Scoreboard;
import ms.kevi.skyblock.util.tool.AnimationTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardAPI {

    private static final Map<Player, PlayerScoreboardData> SCOREBOARD_MAP = new HashMap<>();
    private static final List<String> TITLE_ANIMATIONS = AnimationTool.animateText("SKYBLOCK");

    public static void update(Player player) {
        if(!SCOREBOARD_MAP.containsKey(player)) {
            final Scoreboard scoreboard = new Scoreboard(" Loading... ", "skyblock");
            scoreboard.addLine(0, " §fLoading...");
            scoreboard.addLine(1, " ");
            scoreboard.addLine(2, "  §fLoading... ");
            scoreboard.addLine(3, "  §7Loading... ");
            scoreboard.addLine(4, "  §7» §fLoading... ");
            scoreboard.addLine(5, " §r ");
            scoreboard.addLine(6, " §fCoins: §6Loading... ");
            scoreboard.addLine(7, " §r§r ");
            scoreboard.addLine(8, " §r§ekevingames.net ");
            scoreboard.addPlayer(player);
            SCOREBOARD_MAP.put(player, new PlayerScoreboardData(scoreboard));
        }

        final PlayerScoreboardData scoreboardData = SCOREBOARD_MAP.get(player);
        updateLine(scoreboardData.scoreboard, 0, " §7" + Utils.currentDate() + " §8" + GameHolder.getGameServer().getDisplayId() + " ");
        updateLine(scoreboardData.scoreboard, 2, "  §f" + GameCalender.getCurrentDate() + " ");
        updateLine(scoreboardData.scoreboard, 3, "  §7" + GameCalender.getCurrentTime() + " ");
        updateLine(scoreboardData.scoreboard, 4, "  §7» §r§f" + GameLocation.findNearest(player).getDisplayName());
        updateLine(scoreboardData.scoreboard, 6, " §fCoins: §6" + Utils.formatNumber(MoneyAPI.getCoins(player.getUniqueId())) + " ");

        scoreboardData.scoreboard.setDisplayName("§l" + TITLE_ANIMATIONS.get(scoreboardData.animationIndex++ % TITLE_ANIMATIONS.size()));
    }

    private static void updateLine(Scoreboard scoreboard, int score, String line) {
        if(!scoreboard.getLine(score).equalsIgnoreCase(line))
            scoreboard.addLine(score, line);
    }

    @RequiredArgsConstructor
    private static class PlayerScoreboardData {

        private final Scoreboard scoreboard;
        private int animationIndex = 0;

    }

}

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
import ms.kevi.skyblock.game.effect.Effect;
import ms.kevi.skyblock.game.location.GameLocation;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.util.Utils;
import ms.kevi.skyblock.util.scoreboard.DisplaySlot;
import ms.kevi.skyblock.util.scoreboard.Scoreboard;
import ms.kevi.skyblock.util.tool.AnimationTool;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreboardAPI {

    private static final Map<Player, PlayerScoreboardData> SCOREBOARD_MAP = new HashMap<>();
    private static final List<String> TITLE_ANIMATIONS = AnimationTool.animateText("SKYBLOCK");

    public static void update(Player player) {
        if(!SCOREBOARD_MAP.containsKey(player)) {
            final Scoreboard sidebarScoreboard = new Scoreboard(" Loading... ", "skyblock:sidebar");
            sidebarScoreboard.addLine(0, " §fLoading...");
            sidebarScoreboard.addLine(1, " ");
            sidebarScoreboard.addLine(2, "  §fLoading... ");
            sidebarScoreboard.addLine(3, "  §7Loading... ");
            sidebarScoreboard.addLine(4, "  §7» §fLoading... ");
            sidebarScoreboard.addLine(5, " §r ");
            sidebarScoreboard.addLine(6, " §fCoins: §6Loading... ");
            sidebarScoreboard.addLine(7, " §r§r ");
            sidebarScoreboard.addLine(8, " §r§ekevingames.net ");
            sidebarScoreboard.addPlayer(player);

            final Scoreboard activeEffects = new Scoreboard("§a§lActive Effects", "skyblock:active-effects");
            activeEffects.setDisplaySlot(DisplaySlot.LIST);
            activeEffects.addLine(0, "§fLoading...");
            activeEffects.addPlayer(player);

            SCOREBOARD_MAP.put(player, new PlayerScoreboardData(sidebarScoreboard, activeEffects));
        }

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);

        final PlayerScoreboardData scoreboardData = SCOREBOARD_MAP.get(player);
        updateLine(scoreboardData.sidebarScoreboard, 0, " §7" + Utils.currentDate() + " §8" + GameHolder.getGameServer().getDisplayId() + " ");
        updateLine(scoreboardData.sidebarScoreboard, 2, "  §f" + GameCalender.getCurrentDate() + " ");
        updateLine(scoreboardData.sidebarScoreboard, 3, "  §7" + GameCalender.getCurrentTime() + " ");
        updateLine(scoreboardData.sidebarScoreboard, 4, "  §7» §r§f" + GameLocation.findNearest(player).getDisplayName());
        updateLine(scoreboardData.sidebarScoreboard, 6, " §fCoins: §6" + Utils.formatNumber(MoneyAPI.getCoins(player.getUniqueId())) + " ");

        updateLine(scoreboardData.activeEffects, 0, "§7You have §e" + Utils.formatNumber(skyBlockPlayer.getEffects().size()) + " §r§7 active effects.");

        final List<Effect> effects = skyBlockPlayer.getEffects().getActive().stream().sorted(Comparator.comparingInt(Effect::getDurationTicks)).collect(Collectors.toList());
        if(effects.size() > 0) {
            for(int i = 0; i < effects.size(); i++) {
                final Effect effect = effects.get(i);

                updateLine(scoreboardData.activeEffects, i + 1, effect.getType().getColorCode() + effect.getType().getDisplayName() + " " + Utils.toRoman(effect.getAmplifier()) + " §r§f" + Utils.toTimeWithTicks(effect.getDurationTicks()));
            }
        } else {
            for(int i = 1; i < 15; i++) {
                final String line = scoreboardData.activeEffects.getLine(i);
                if(line == null) break;
                scoreboardData.activeEffects.removeLine(i);
            }
        }

        scoreboardData.sidebarScoreboard.setDisplayName("§l" + TITLE_ANIMATIONS.get(scoreboardData.animationIndex++ % TITLE_ANIMATIONS.size()));
    }

    private static void updateLine(Scoreboard scoreboard, int score, String line) {
        final String oldLine = scoreboard.getLine(score);
        if(oldLine == null || !oldLine.equalsIgnoreCase(line))
            scoreboard.addLine(score, line);
    }

    @RequiredArgsConstructor
    private static class PlayerScoreboardData {

        private final Scoreboard sidebarScoreboard;
        private final Scoreboard activeEffects;
        private int animationIndex = 0;

    }

}

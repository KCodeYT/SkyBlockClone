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

package ms.kevi.skyblock.util.scoreboard;

import cn.nukkit.Player;
import ms.kevi.skyblock.util.scoreboard.network.RemoveObjectivePacket;
import ms.kevi.skyblock.util.scoreboard.network.ScoreEntry;
import ms.kevi.skyblock.util.scoreboard.network.SetDisplayObjectivePacket;
import ms.kevi.skyblock.util.scoreboard.network.SetScorePacket;

import java.util.*;

public class Scoreboard {

    private static volatile int scoreIdentifies = 0;

    private final List<Player> players;
    private final Map<Integer, String> lines;
    private final Map<Integer, Integer> scoreIds;
    private final String objectiveName;

    private String displayName;
    private DisplaySlot displaySlot;
    private SortOrder sortOrder;

    public Scoreboard(String displayName) {
        this(displayName, "dummy", DisplaySlot.SIDEBAR, SortOrder.ASCENDING);
    }

    public Scoreboard(String displayName, String objectiveName) {
        this(displayName, objectiveName, DisplaySlot.SIDEBAR, SortOrder.ASCENDING);
    }

    public Scoreboard(String displayName, String objectiveName, DisplaySlot displaySlot) {
        this(displayName, objectiveName, displaySlot, SortOrder.ASCENDING);
    }

    public Scoreboard(String displayName, String objectiveName, DisplaySlot displaySlot, SortOrder sortOrder) {
        this.displayName = displayName;
        this.objectiveName = objectiveName;

        this.displaySlot = displaySlot;
        this.sortOrder = sortOrder;

        this.players = new ArrayList<>();
        this.lines = new HashMap<>();
        this.scoreIds = new HashMap<>();
    }

    public synchronized void addPlayer(Player player) {
        if(this.players.contains(player)) return;
        final SetDisplayObjectivePacket setDisplayObjectivePacket = new SetDisplayObjectivePacket();
        setDisplayObjectivePacket.criteriaName = "dummy";
        setDisplayObjectivePacket.displayName = this.displayName;
        setDisplayObjectivePacket.objectiveName = this.objectiveName;
        setDisplayObjectivePacket.displaySlot = this.displaySlot.name().toLowerCase();
        setDisplayObjectivePacket.sortOrder = this.sortOrder.ordinal();

        player.dataPacket(setDisplayObjectivePacket);

        final ScoreEntry[] entries = new ScoreEntry[this.lines.size()];

        int index = 0;
        for(int score : this.lines.keySet()) {
            entries[index] = new ScoreEntry();
            entries[index].type = ScoreEntry.TYPE_FAKE_PLAYER;
            entries[index].score = score;
            entries[index].objectiveName = this.objectiveName;
            entries[index].displayName = this.lines.get(score);
            entries[index].scoreId = this.scoreIds.get(score);
            index++;
        }

        final SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.type = SetScorePacket.TYPE_CHANGE;
        setScorePacket.entries = entries;

        player.dataPacket(setScorePacket);

        this.players.add(player);
    }

    public synchronized void removePlayer(Player player) {
        if(!this.players.contains(player)) return;
        final ScoreEntry[] entries = new ScoreEntry[this.lines.size()];

        int index = 0;
        for(int score : this.lines.keySet()) {
            entries[index] = new ScoreEntry();
            entries[index].type = ScoreEntry.TYPE_FAKE_PLAYER;
            entries[index].score = 0;
            entries[index].objectiveName = "";
            entries[index].displayName = "";
            entries[index].scoreId = this.scoreIds.get(score);
            index++;
        }

        final SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.type = SetScorePacket.TYPE_REMOVE;
        setScorePacket.entries = entries;

        player.dataPacket(setScorePacket);

        final RemoveObjectivePacket removeObjectivePacket = new RemoveObjectivePacket();
        removeObjectivePacket.objectiveName = this.objectiveName;

        player.dataPacket(removeObjectivePacket);

        this.players.remove(player);
    }

    public synchronized void addLine(int score, String lineContent) {
        final int scoreId = Scoreboard.scoreIdentifies++;

        if(this.lines.containsKey(score)) this.removeLine(score);

        this.lines.put(score, lineContent);
        this.scoreIds.put(score, scoreId);

        final ScoreEntry entry = new ScoreEntry();
        entry.type = ScoreEntry.TYPE_FAKE_PLAYER;
        entry.score = score;
        entry.objectiveName = this.objectiveName;
        entry.displayName = lineContent;
        entry.scoreId = scoreId;

        final SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.type = SetScorePacket.TYPE_CHANGE;
        setScorePacket.entries = new ScoreEntry[]{entry};

        for(Player player : this.players) player.dataPacket(setScorePacket);
    }

    public synchronized String getLine(int score) {
        return this.lines.get(score);
    }

    public synchronized void removeLine(int score) {
        if(!this.lines.containsKey(score)) return;
        final int scoreId = this.scoreIds.get(score);

        this.lines.remove(score);
        this.scoreIds.remove(score);

        final ScoreEntry entry = new ScoreEntry();
        entry.type = ScoreEntry.TYPE_FAKE_PLAYER;
        entry.score = 0;
        entry.objectiveName = "";
        entry.displayName = "";
        entry.scoreId = scoreId;

        final SetScorePacket setScorePacket = new SetScorePacket();
        setScorePacket.type = SetScorePacket.TYPE_REMOVE;
        setScorePacket.entries = new ScoreEntry[]{entry};

        for(Player player : this.players) player.dataPacket(setScorePacket);
    }

    private synchronized void resendToAll() {
        for(Player player : this.players.toArray(new Player[0])) {
            this.removePlayer(player);
            this.addPlayer(player);
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.resendToAll();
    }

    public DisplaySlot getDisplaySlot() {
        return this.displaySlot;
    }

    public void setDisplaySlot(DisplaySlot displaySlot) {
        this.displaySlot = displaySlot;
        this.resendToAll();
    }

    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        this.resendToAll();
    }

}

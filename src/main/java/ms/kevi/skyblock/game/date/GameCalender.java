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

package ms.kevi.skyblock.game.date;

import cn.nukkit.level.Level;

public class GameCalender {

    private static final String[] SUFFIXES = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
    private static final String[] MONTH_NAMES;
    private static final long GAME_TIME;

    static {
        final String[] seasons = {"Spring", "Summer", "Autumn", "Winter"};
        MONTH_NAMES = new String[12];
        for(int i = 0; i < 4; i++) {
            MONTH_NAMES[i * 3] = "Early " + seasons[i];
            MONTH_NAMES[i * 3 + 1] = seasons[i];
            MONTH_NAMES[i * 3 + 2] = "Late " + seasons[i];
        }

        GAME_TIME = System.currentTimeMillis();
    }

    public static String getCurrentTime() {
        final long timeMillis = System.currentTimeMillis() - GAME_TIME;
        long minutes = (GameTimeUnit.toMinutes(timeMillis) / 10) * 10;
        while(minutes >= 60)
            minutes -= 60;
        long hours = GameTimeUnit.toHours(timeMillis);
        while(hours >= 24)
            hours -= 24;
        return (hours > 12 ? hours - 12 : hours == 0 ? 12 : hours) + ":" + (minutes == 0 ? "00" : minutes) + (hours >= 12 ? "pm" : "am");
    }

    public static int getCurrentLevelTime(Level level) {
        if(level.stopTime) return level.getTime();
        final long timeMillis = System.currentTimeMillis() - GAME_TIME;
        long minutes = GameTimeUnit.toMinutes(timeMillis);
        while(minutes >= 60)
            minutes -= 60;
        long hours = GameTimeUnit.toHours(timeMillis);
        while(hours >= 24)
            hours -= 24;
        float levelTime = ((hours * 1000) + Math.round(((float) minutes) * 1000 / 60)) - 8000;
        while(levelTime > 24000)
            levelTime -= 24000;
        return (int) levelTime;
    }

    public static String getCurrentDate() {
        final long timeMillis = System.currentTimeMillis() - GAME_TIME;
        long days = GameTimeUnit.toDays(timeMillis);
        while(days >= 31)
            days -= 31;
        long months = GameTimeUnit.toMonths(timeMillis);
        while(months >= 12)
            months -= 12;
        return MONTH_NAMES[(int) months] + " " + getOrdinal((int) days + 1);
    }

    private static String getOrdinal(int i) {
        if(i == 11 || i == 12 || i == 13)
            return i + "th";
        return i + SUFFIXES[i % 10];
    }

}

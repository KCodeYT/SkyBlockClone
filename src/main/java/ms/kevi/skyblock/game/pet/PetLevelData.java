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

package ms.kevi.skyblock.game.pet;

import ms.kevi.skyblock.game.GameRarity;

public class PetLevelData {

    private static final int[] OFFSETS;
    private static final double[] FULL_TABLE;

    static {
        OFFSETS = new int[]{0, 6, 11, 16, 20, 20, -1, -1, -1};
        FULL_TABLE = new double[]{
                100, 110, 120, 130, 145, 160, 175, 190, 210, 230, 250,
                275, 300, 330, 360, 400, 440, 490, 540, 600, 660, 730,
                800, 880, 960, 1050, 1150, 1260, 1380, 1510, 1650, 1800, 1960,
                2130, 2310, 2500, 2700, 2920, 3160, 3420, 3700, 4000, 4350, 4750,
                5200, 5700, 6300, 7000, 7800, 8700, 9700, 10800, 12000, 13300, 14700,
                16200, 17800, 19500, 21300, 23200, 25200, 27400, 29800, 32400, 35200, 38200,
                41400, 44800, 48400, 52200, 56200, 60400, 64800, 69400, 74200, 79200, 84700,
                90700, 97200, 104200, 111700, 119700, 128200, 137200, 146700, 156700, 167700, 179700,
                192700, 206700, 221700, 237700, 254700, 272700, 291700, 311700, 333700, 357700, 383700,
                411700, 441700, 476700, 516700, 561700, 611700, 666700, 726700, 791700, 861700, 936700,
                1016700, 1101700, 1191700, 1286700, 1386700, 1496700, 1616700, 1746700, 1886700,

                0, 0, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700, 1886700,
                1886700
        };
    }

    public static int getLevel(PetData petData) {
        final int offset = OFFSETS[petData.getRarity().ordinal()];
        if(offset == -1)
            return 0;
        double cumulative = 0;
        for(int level = 0; level <= petData.getType().getMaxLevel(); level++)
            if(petData.getExperience() < (cumulative += FULL_TABLE[level + offset]))
                return level + 1;
        return petData.getType().getMaxLevel();
    }

    public static double getExperience(GameRarity gameRarity, int level) {
        final int offset = OFFSETS[gameRarity.ordinal()];
        if(offset == -1)
            return 0;
        double cumulative = 0;
        for(int i = 0; i < level - 1; i++)
            cumulative += FULL_TABLE[offset + i];
        return cumulative;
    }

    public static double getRequiredExperience(GameRarity gameRarity, int level) {
        final int offset = OFFSETS[gameRarity.ordinal()];
        if(offset == -1)
            return 0;
        return FULL_TABLE[level + offset - 1];
    }

}

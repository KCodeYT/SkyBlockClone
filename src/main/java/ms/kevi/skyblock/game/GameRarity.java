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

import lombok.AllArgsConstructor;
import lombok.Getter;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.util.Color;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GameRarity {

    COMMON(0, 1, "Common", new Color(255, 255, 255), "§f"),
    UNCOMMON(1, 2, "Uncommon", new Color(85, 255, 85), "§a"),
    RARE(2, 3, "Rare", new Color(85, 85, 255), "§9"),
    EPIC(3, 4, "Epic", new Color(170, 0, 170), "§5"),
    LEGENDARY(5, 6, "Legendary", new Color(255, 170, 0), "§6"),
    MYTHIC(6, 7, "Mythic", new Color(255, 85, 255), "§d"),
    DIVINE(7, 8, "Divine", new Color(85, 255, 255), "§b"),
    SPECIAL(8, 9, "Special", new Color(255, 85, 85), "§c"),
    VERY_SPECIAL(9, 10, "Very Special", new Color(255, 85, 85), "§c");

    private final int id;
    private final int level;
    private final String name;
    private final Color color;
    private final String colorCode;

    public static GameRarity getById(int id) {
        return Arrays.stream(values()).filter(gameRarity -> gameRarity.getId() == id).findAny().orElse(null);
    }

    public static GameRarity getByLowestId(int id) {
        return Arrays.stream(values()).filter(gameRarity -> gameRarity.getId() == id).findAny().orElse(id <= 0 ? GameRarity.COMMON : GameRarity.getByLowestId(id - 1));
    }

    public String buildName(boolean bold) {
        return this.buildName(bold, false, false, null);
    }

    public String buildName(boolean bold, boolean upgraded, boolean fromDungeon, ItemType itemType) {
        final String colorCode = this.getColorCode() + (bold ? "§l" : "");
        return colorCode + (upgraded ? "§ka§r " : "") + colorCode + this.getName().toUpperCase() + (fromDungeon ? " DUNGEON" + (itemType == null ? " ITEM" : "") : "") + (itemType != null ? " " + itemType.name().toUpperCase().replace('_', ' ') : "") + (upgraded ? colorCode + " §ka" : "");
    }

}

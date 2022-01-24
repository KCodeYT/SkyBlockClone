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

package ms.kevi.skyblock.item.custom.minion;

import cn.nukkit.item.Item;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.game.minion.IMinionType;

@RequiredArgsConstructor
public class SpecificMinionItem extends MinionItem {

    private final MinionItem parent;
    private final IMinionType type;
    private final int tierLevel;

    @Override
    public Item createItem(Object... args) {
        return this.parent.createItem(this.type, this.tierLevel);
    }

    @Override
    public boolean checkName(String name) {
        return name.equalsIgnoreCase(this.parent.createId(this.type, this.tierLevel));
    }

}

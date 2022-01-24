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

package ms.kevi.skyblock.game.crafting;

import cn.nukkit.item.Item;
import ms.kevi.skyblock.item.IGameItem;

public class GameResultItem extends ResultItem {

    private final Creator creator;

    public GameResultItem(IGameItem gameItem, Creator creator) {
        super(gameItem);
        this.creator = creator;
    }

    @Override
    public Item create() {
        return this.creator.create(this.gameItem);
    }

    public interface Creator {

        Item create(IGameItem gameItem);

    }

}

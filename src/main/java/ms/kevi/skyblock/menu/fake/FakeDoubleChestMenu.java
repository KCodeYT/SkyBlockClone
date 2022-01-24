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

package ms.kevi.skyblock.menu.fake;

import cn.nukkit.Player;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.math.BlockVector3;

import java.util.Arrays;
import java.util.List;

public class FakeDoubleChestMenu extends FakeChestMenu {

    public FakeDoubleChestMenu(Player player, String name) {
        super(InventoryType.DOUBLE_CHEST, player, name);
    }

    @Override
    protected List<BlockVector3> onOpenBlock(Player who) {
        final BlockVector3 blockPos = super.onOpenBlock(who).get(0);
        return Arrays.asList(blockPos, blockPos.add(1));
    }

}

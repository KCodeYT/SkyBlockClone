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

package ms.kevi.skyblock.menu;

import cn.nukkit.Player;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.registry.ItemGenerator;
import ms.kevi.skyblock.menu.fake.FakeDoubleChestMenu;

public class BitsStoreMenu extends FakeDoubleChestMenu {

    public BitsStoreMenu(Player player) {
        super(player, "Community Shop");
        this.setItem(30,
                ItemBuilder.
                        fromCopy(ItemGenerator.create("booster_cookie", 1)).
                        addLore(
                                "",
                                "§r§7Cost",
                                "§r§a325 SkyBlock Gems",
                                "",
                                "§r§7You have: "
                        ).
                        toItem(),
                fakeEvent -> {

                }
        );
    }

}

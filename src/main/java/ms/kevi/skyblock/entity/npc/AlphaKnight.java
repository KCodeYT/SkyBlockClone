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

package ms.kevi.skyblock.entity.npc;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.menu.shop.ShopMenu;
import ms.kevi.skyblock.util.ImageUtil;

import java.awt.image.BufferedImage;

public class AlphaKnight {

    private static final String SHOP_NAME = "Alpha Knight";
    private static final BufferedImage TEXTURE = ImageUtil.readImage("http://textures.minecraft.net/texture/90fb0cbaa891138027a8067693fe1eaf93fe443bc2f79c8217355c051494be17");

    public static void create(Player player) {
        final EntityNPC entityNPC = EntityNPC.create("§r§f" + SHOP_NAME + "\n§r§l§eSHOP",
                true,
                player.getLevel().getSafeSpawn(player.add(0, 0, 3)).floor().add(0.5, 0, 0.5),
                TEXTURE);
        entityNPC.spawnToAll();

        final ShopMenu shopMenu = new ShopMenu(SHOP_NAME, entityNPC, true);
        shopMenu.addItem(0, 1000000,
                ItemBuilder.get(Item.GOLDEN_NUGGET, 0, 1).
                        setCustomName("§r§cGiant pile of Cash").
                        setLore("§r§c§lSPECIAL CASH ITEM").
                        editExtraAttributes(extraAttributes -> extraAttributes.putString(ItemHelper.ATTRIBUTE_ID, "GIANT_PILE_OF_CASH")).
                        toItem(), true);

        entityNPC.setPlayerFunction(shopMenu::open);
    }

}

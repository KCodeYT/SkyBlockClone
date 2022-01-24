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

package ms.kevi.skyblock.item.custom.crystal;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.entity.crystal.EntityCrystal;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.crystal.ICrystalType;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHandler;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.FunctionInvoker;

import java.util.ArrayList;
import java.util.List;

public class CrystalItem extends AbstractGameItem implements ItemHandler {

    @Override
    public Item createItem(Object... args) {
        final ICrystalType crystalType = (ICrystalType) args[0];
        final List<String> lore = new ArrayList<>();
        ItemHelper.addDescription(lore, "§7" + crystalType.getDescription());
        lore.add("");
        lore.add("§r§7Effective Radius: §b" + crystalType.getEffectiveRadius());
        return ItemBuilder.from(super.createItem(args)).
                editExtraAttributes(extraAttributes -> extraAttributes.putString(ItemHelper.ATTRIBUTE_ID, crystalType.name() + "_" + this.name())).
                setCustomName("§9" + crystalType.getDisplayName() + " Crystal").
                setLore(lore).
                toItem();
    }

    @Override
    public boolean needsParams() {
        return true;
    }

    @Override
    public List<FunctionInvoker<Item>> possibleInvokers() {
        final List<FunctionInvoker<Item>> allItems = new ArrayList<>();
        for(ICrystalType crystalType : Registries.CRYSTALS.values())
            allItems.add(FunctionInvoker.of(this::createItem, crystalType));
        return allItems;
    }

    @Override
    public String getDisplayName() {
        return "Unfinished Crystal";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.COMMON;
    }

    @Override
    public int getId() {
        return Item.EMERALD;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final Item item = event.getItem();
        if(block.getId() == 0)
            return;
        final ICrystalType crystalType = Registries.CRYSTALS.valueOf(ItemHelper.getAttributeId(item).replace("_CRYSTAL", ""));
        if(crystalType == null)
            return;

        new EntityCrystal(block.getChunk(), Entity.getDefaultNBT(block.add(0.5, 1.5, 0.5)).
                putLong("OwnerUUIDMost", player.getUniqueId().getMostSignificantBits()).
                putLong("OwnerUUIDLeast", player.getUniqueId().getLeastSignificantBits()).
                putString("CrystalTypeEnum", crystalType.name()).
                putInt("TimeBetweenActions", crystalType.getActionTime()).
                putInt("EffectiveRadius", crystalType.getEffectiveRadius())).spawnToAll();
    }

    @Override
    public boolean checkName(String name) {
        return name.endsWith(this.name());
    }

}

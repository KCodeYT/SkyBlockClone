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

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.nbt.tag.CompoundTag;
import ms.kevi.skyblock.entity.EntityType;
import ms.kevi.skyblock.entity.minion.EntityMinion;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.minion.IMinionType;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHandler;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.FunctionInvoker;
import ms.kevi.skyblock.util.Language;
import ms.kevi.skyblock.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MinionItem extends AbstractGameItem implements ItemHandler {

    @Override
    public Item createItem(Object... args) {
        final IMinionType type = (IMinionType) args[0];
        final int tierLevel = (int) args[1];

        final List<String> descriptionList = new ArrayList<>();
        ItemHelper.addDescription(descriptionList,
                "§7" + Language.get("MINION_DESCRIPTION_START") + " " +
                        Language.get("MINION_TYPE_" + type.name() + "_INNER") + " " +
                        Language.get("MINION_DESCRIPTION_END"));
        descriptionList.add("");
        descriptionList.add("§7Time Between Actions: §a" + Utils.formatNumber(type.getTimeBetweenActions(tierLevel)) + "s");
        descriptionList.add("§7Max Storage: §e" + type.getMaxStorage(tierLevel));
        return ItemBuilder.get(this.getId(), this.getDamage(), 1).
                setCustomName("§9" + type.getDisplayName() + " " + this.getDisplayName() + " " + Utils.toRoman(tierLevel)).
                setLore(descriptionList).
                setSparkle(true).
                editNamedTag(compoundTag -> compoundTag.
                        putCompound(ItemHelper.EXTRA_ATTRIBUTES, new CompoundTag().
                                putString(ItemHelper.ATTRIBUTE_UUID, UUID.randomUUID().toString()).
                                putString(ItemHelper.ATTRIBUTE_ID, this.createId(type, tierLevel)).
                                putInt("TierLevel", tierLevel))).
                toItem();
    }

    @Override
    public boolean needsParams() {
        return true;
    }

    @Override
    public List<FunctionInvoker<Item>> possibleInvokers() {
        final List<FunctionInvoker<Item>> allItems = new ArrayList<>();
        for(IMinionType minionType : Registries.MINIONS.values()) {
            for(int tierLevel = 1; tierLevel <= minionType.getMaxTierLevel(); tierLevel++)
                allItems.add(FunctionInvoker.of(this::createItem, minionType, tierLevel));
        }
        return allItems;
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final Item item = event.getItem();

        if(block.getId() != Block.AIR && !block.asBlockVector3().equals(new BlockVector3(0, 0, 0))) {
            if(block.getChunk() != null) {
                final CompoundTag extraAttributes = ItemHelper.getExtraAttributes(item);
                new EntityMinion(block.getChunk(), EntityMinion.getDefaultNBT(Location.fromObject(block.add(0.5, 1, 0.5), block.level, roundYaw(player.yaw + 180))).
                        putCompound("Skin", new CompoundTag().putByteArray("Data", player.getSkin().getSkinData().data)).
                        putLong("OwnerUUIDMost", player.getUniqueId().getMostSignificantBits()).
                        putLong("OwnerUUIDLeast", player.getUniqueId().getLeastSignificantBits()).
                        putString("MinionTypeEnum", this.getMinionType(extraAttributes).name()).
                        putInt("TierLevel", extraAttributes.getInt("TierLevel"))).spawnToAll();
                if(player.isSurvival()) {
                    item.count--;
                    player.getInventory().setItemInHand(item);
                }
            }
        }
    }

    private String splitter() {
        return "_" + this.name() + "_";
    }

    String createId(IMinionType minionType, int tierLevel) {
        return minionType.name() + this.splitter() + tierLevel;
    }

    private IMinionType getMinionType(CompoundTag extraAttributes) {
        return Registries.MINIONS.valueOfNonNull(extraAttributes.getString(ItemHelper.ATTRIBUTE_ID).split(this.splitter())[0]);
    }

    private double roundYaw(double circle) {
        circle %= 360;
        if(circle < 45) return 0;
        if(circle >= 45 && circle < 135) return 90;
        if(circle >= 135 && circle < 225) return 180;
        if(circle >= 225 && circle < 315) return 270;
        return 360;
    }

    @Override
    public String getDisplayName() {
        return "Minion";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.COMMON;
    }

    @Override
    public int getId() {
        return Item.SPAWN_EGG;
    }

    @Override
    public int getDamage() {
        return EntityType.WANDERING_TRADER.getNetworkId();
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean checkName(String name) {
        return name.toLowerCase().contains(this.splitter().toLowerCase());
    }

    @Override
    public MinionItem specific(Object... args) {
        return new SpecificMinionItem(this, (IMinionType) args[0], (int) args[1]);
    }

}

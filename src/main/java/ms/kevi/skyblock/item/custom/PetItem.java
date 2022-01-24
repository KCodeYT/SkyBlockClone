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

package ms.kevi.skyblock.item.custom;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.pet.IPetAbility;
import ms.kevi.skyblock.game.pet.IPetType;
import ms.kevi.skyblock.game.pet.PetData;
import ms.kevi.skyblock.game.pet.PetLevelData;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.item.ItemBuilder;
import ms.kevi.skyblock.item.ItemHandler;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.registry.AbstractGameItem;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.FunctionInvoker;
import ms.kevi.skyblock.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PetItem extends AbstractGameItem implements ItemHandler {

    @Override
    public Item createItem(Object... args) {
        final PetData petData = (PetData) args[0];
        final int level = petData.getLevel();
        final Item typeItem = petData.getType().getItem(petData);
        double lastLevelExperience = 0;
        int lastLevel = level - 1;
        while(lastLevel > 0)
            lastLevelExperience += PetLevelData.getRequiredExperience(petData.getRarity(), lastLevel--);
        final double requiredExperience = PetLevelData.getRequiredExperience(petData.getRarity(), level);

        final List<String> lore = new ArrayList<>();
        lore.add("§r§8" + petData.getType().getSkillType() + " Pet");
        lore.add("§r");

        final StatsBooster statsBooster = petData.getType().getStatsBooster(petData);
        final Set<GameStats> gameStatsKeySet = statsBooster.getKeySet();

        if(!gameStatsKeySet.isEmpty()) {
            for(GameStats gameStats : gameStatsKeySet)
                lore.add("§r§7" + gameStats.getDisplayName() + ": §a" + Utils.formatNumber(statsBooster.get(gameStats), true) + gameStats.getSuffix());
            lore.add("§r");
        }

        for(IPetAbility petAbility : petData.getType().getPetAbilities(petData)) {
            lore.add("§r§6" + petAbility.getDisplayName());
            ItemHelper.addDescription(lore, petAbility.getDescription(petData));
            lore.add("§r");
        }

        if(args.length > 1) {
            final PetData activePetData = GameHolder.getPlayer((Player) args[1]).getPets().getActive().getPetData();

            if(level < petData.getType().getMaxLevel()) {
                final double percent = (petData.getExperience() - lastLevelExperience) * 100 / requiredExperience;
                final StringBuilder expLine = new StringBuilder();
                for(int i = 1; i <= 20; i++)
                    expLine.append(percent > (i - 1) * 5 ? "§3" : "§f").append("-");

                lore.add("§r§7Progress to Level " + (level + 1) + ": §e" + Utils.roundNumber(percent, 1) + "%");
                lore.add("§r" + expLine + " §e" + Utils.formatNumber(petData.getExperience() - lastLevelExperience) + "§6/§e" + Utils.shortenNumber(requiredExperience));
            } else
                lore.add("§r§l§bMAX LEVEL");
            lore.add("§r ");
            lore.add("§r" + (petData.equals(activePetData) ? "§cClick to despawn" : "§eClick to summon"));
        } else {
            lore.add("§r§eInteract to add this pet to");
            lore.add("§r§eyour pet menu!");
            lore.add("§r ");
            lore.add("§r" + petData.getRarity().buildName(true));
        }

        return ItemBuilder.get(typeItem.getId(), typeItem.getDamage(), typeItem.getCount()).
                setCustomName("§r§7[Lvl " + level + "] §r§f" + petData.getDisplayName()).
                editExtraAttributes(extraAttributes -> extraAttributes.
                        putString(ItemHelper.ATTRIBUTE_ID, this.getAttributeId()).
                        putString(ItemHelper.ATTRIBUTE_UUID, UUID.randomUUID().toString()).
                        putString(PetData.TAG_NAME, petData.toJson())).
                setLore(lore).toItem();
    }

    @Override
    public boolean needsParams() {
        return true;
    }

    @Override
    public List<FunctionInvoker<Item>> possibleInvokers() {
        final List<FunctionInvoker<Item>> allItems = new ArrayList<>();
        for(IPetType petType : Registries.PETS.values()) {
            for(GameRarity rarity : petType.getAvailableRarities()) {
                allItems.add(FunctionInvoker.of(this::createItem, PetData.of(petType, rarity, 0)));
                allItems.add(FunctionInvoker.of(this::createItem, PetData.of(petType, rarity, PetLevelData.getExperience(rarity, 100))));
            }
        }
        return allItems;
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Item item = event.getItem();
        final CompoundTag extraAttributes = ItemHelper.getExtraAttributes(item);

        if(extraAttributes.contains(PetData.TAG_NAME)) {
            final PetData petData = PetData.fromJson(extraAttributes.getString(PetData.TAG_NAME));
            GameHolder.getPlayer(player).getPets().addPet(petData);
            player.sendMessage("§aSuccessfully added §r" + petData.getDisplayName() + "§r§a to your pet menu!");
            player.getInventory().setItemInHand(Item.get(Item.AIR));
        }
    }

    @Override
    public String getDisplayName() {
        return "Pet";
    }

    @Override
    public GameRarity getRarity() {
        return GameRarity.COMMON;
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public int getDamage() {
        return -1;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

}

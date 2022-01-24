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
import cn.nukkit.item.Item;
import cn.nukkit.utils.DyeColor;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.pet.PetData;
import ms.kevi.skyblock.game.pet.Pets;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.menu.basic.BasicMenu;
import ms.kevi.skyblock.menu.fake.FakeInventory;
import ms.kevi.skyblock.registry.GameItemRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PetsMenu extends BasicMenu {

    private final Map<Item, Consumer<Boolean>> dropItemConsumers;
    private final SkyBlockPlayer skyBlockPlayer;
    private boolean convertToItem;

    PetsMenu(Player player) {
        super(player, "Pets");
        this.skyBlockPlayer = GameHolder.getPlayer(player);
        this.dropItemConsumers = new HashMap<>();
        this.convertToItem = false;
        this.fillInventory(false, true);
    }

    static Item getMenuItem(PetData currentPet, boolean clickToView) {
        final String petName = "§r" + (currentPet == null ? "§cNone" : currentPet.getDisplayName());
        final String[] itemLore = new String[]{"§r§7View and manage all of your", "§r§7Pets", "§r", "§r§7Level up your pets faster by", "§r§7gaining xp in their favorite", "§r§7skill!", "§r", "§r§7Selected pet: §r§6{current_pet}"};
        final String[] lore = new String[itemLore.length + (clickToView ? 2 : 0)];
        for(int i = 0; i < itemLore.length; i++)
            lore[i] = itemLore[i].replace("{current_pet}", petName);
        if(clickToView) {
            lore[lore.length - 2] = "§r";
            lore[lore.length - 1] = "§r§eClick to view!";
        }
        return Item.get(Item.BONE).setCustomName("§r§aPets").setLore(lore);
    }

    @Override
    public void fillInventory(boolean next, boolean current) {
        this.fillItems();
        super.fillInventory(next, current);
    }

    private void fillItems() {
        this.menuItems.clear();
        final Pets pets = this.skyBlockPlayer.getPets();
        final Player player = this.getPlayer();

        this.setItem(4, PetsMenu.getMenuItem(pets.getActive().getPetData(), false), FakeInventory.CANCELLED);
        this.setItem(48, Item.get(Item.ARROW).setCustomName("§r§aGo Back"), this.cancelAndOpen(() -> new SkyBlockMenu(player)));
        this.setItem(49, FakeInventory.CLOSE_BARRIER, FakeInventory.CLOSING);
        this.setItem(50, Item.get(Item.DYE, this.convertToItem ? DyeColor.LIME.getDyeData() : DyeColor.GRAY.getDyeData()).
                        setCustomName("§r§aConvert Pet to an Item").
                        setLore(
                                "§r§7Enable this setting and",
                                "§r§7click any pet to convert it",
                                "§r§7to an item.",
                                "§r",
                                "§r" + (this.convertToItem ? "§aEnabled" : "§cDisabled")
                        ),
                FakeInventory.CANCELLED.andThen(event -> {
                    this.convertToItem = !this.convertToItem;
                    this.fillInventory(false, true);
                }));

        for(PetData petData : pets.getPets()) {
            this.addItem(GameItemRegistry.PET.createItem(petData, player), dropped -> {
                final boolean disabled = pets.getActive().getPetData() == petData;
                if(disabled)
                    pets.disablePet();
                if(this.convertToItem) {
                    if(this.tryAdd(petData))
                        pets.removePet(petData);
                    this.convertToItem = false;
                    this.fillInventory(false, true);
                } else {
                    this.close();
                    if(!disabled) {
                        if(pets.enablePet(player, petData))
                            player.sendMessage("§aYou summoned your §r" + petData.getDisplayName() + "§r§a!");
                        else
                            player.sendMessage("§cCouldn't summon your §r" + petData.getDisplayName() + "§r§c!");
                    } else {
                        player.sendMessage("§aYou despawned your §r" + petData.getDisplayName() + "§r§a!");
                    }
                }
            });
        }
    }

    private boolean tryAdd(PetData petData) {
        final Item petItem = GameItemRegistry.PET.createItem(petData);
        if(this.getPlayer().getInventory().canAddItem(petItem)) {
            this.getPlayer().getInventory().addItem(petItem);
            return true;
        } else
            this.getPlayer().sendMessage("§r§cYou dont have any inventory space!");
        return false;
    }

    private void addItem(Item item, Consumer<Boolean> dropConsumer) {
        this.menuItems.add(item);
        this.dropItemConsumers.put(item, dropConsumer);
    }

    @Override
    public void onDrop(Item item) {
        if(this.dropItemConsumers.containsKey(item))
            this.dropItemConsumers.get(item).accept(true);
    }

    @Override
    public void onClick(Item item) {
        if(this.dropItemConsumers.containsKey(item))
            this.dropItemConsumers.get(item).accept(false);
    }

}

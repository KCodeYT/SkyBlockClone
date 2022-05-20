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

package ms.kevi.skyblock.item.custom.orb;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.entity.orb.EntityOrb;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.orb.IOrbType;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.handler.ItemInteractHandler;
import ms.kevi.skyblock.item.registry.AbstractGameItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PowerOrbItem extends AbstractGameItem implements ItemInteractHandler {

    private final IOrbType orbType;

    @Override
    public Item createItem(Object... args) {
        final Item item = super.createItem(args);
        final List<String> lines = new ArrayList<>();
        final GameRarity rarity = ItemHelper.getRarity(item);
        lines.add("§6Item Ability: Deploy");
        ItemHelper.addDescription(lines, "§7Place an orb for §a" +
                TimeUnit.MILLISECONDS.toSeconds(this.orbType.getTime()) +
                "s §7buffing up to §b" + this.orbType.getMaxPlayers() +
                " §7players within §a" + this.orbType.getPlayerRadius() +
                " §7blocks.");
        lines.add("§8Costs 50% of max mana.");
        lines.add("§8Only one orb applies per player");
        lines.add("");
        lines.add(rarity.getColorCode() + "Orb Buff: " + this.orbType.getName());
        lines.addAll(Arrays.asList(this.orbType.getOrbBuff().getDescription()));
        lines.add("");
        lines.addAll(Arrays.asList(item.getLore()));
        return item.setLore(lines.stream().map(s -> (s.startsWith("§r") ? "" : "§r") + s).toArray(String[]::new));
    }

    @Override
    public String getDisplayName() {
        return this.orbType.getName() + " Power Orb";
    }

    @Override
    public GameRarity getRarity() {
        return this.orbType.getRarity();
    }

    @Override
    public int getId() {
        return this.orbType.getItemId();
    }

    @Override
    public int getDamage() {
        return this.orbType.getItemDamage();
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        if(block.getId() == 0)
            return;

        new EntityOrb(block.getChunk(), Entity.getDefaultNBT(block.add(0.5, 1.5, 0.5)).
                putLong("OwnerUUIDMost", player.getUniqueId().getMostSignificantBits()).
                putLong("OwnerUUIDLeast", player.getUniqueId().getLeastSignificantBits()).
                putString("OrbType", this.orbType.name())).spawnToAll();
    }

    @Override
    public int getStackSize() {
        return 1;
    }

}

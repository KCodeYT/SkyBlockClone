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

package ms.kevi.skyblock.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import ms.kevi.skyblock.game.minion.IMinionType;
import ms.kevi.skyblock.registry.GameItemRegistry;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.Utils;

public class GiveMinionCommand extends Command {

    public GiveMinionCommand() {
        super("giveminion");
        final String[] minionTypes = Registries.MINIONS.nameSet().stream().map(String::toLowerCase).toArray(String[]::new);
        this.setPermission("skyblock.cmd.giveminion");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("minionType", false, minionTypes),
                CommandParameter.newType("tier", false, CommandParamType.INT)
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("minionType", false, minionTypes),
                CommandParameter.newType("tier", false, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender))
            return false;
        if(args.length < 2) {
            sender.sendMessage("§cUsage: /giveminion <minionType> <tier> | /giveminion <target> <minionType> <tier>");
            return false;
        }

        final Player target = sender instanceof Player && args.length == 2 ? (Player) sender : args.length > 2 ? sender.getServer().getPlayer(args[0]) : null;
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final String typeArg = args[args.length == 2 ? 0 : 1];
        final IMinionType minionType = Registries.MINIONS.valueOf(typeArg);
        if(minionType == null) {
            sender.sendMessage("§cMinion type " + typeArg + " couldn't be found!");
            return false;
        }

        int tierLevel;
        try {
            tierLevel = Integer.parseInt(args[args.length == 2 ? 1 : 2]);
            if(tierLevel < 1)
                tierLevel = 1;
            final int maxTierLevel = minionType.getMaxTierLevel();
            if(tierLevel > maxTierLevel)
                tierLevel = maxTierLevel;
        } catch(NumberFormatException e) {
            sender.sendMessage("§cTier isn't a number!");
            return false;
        }

        final Item item = GameItemRegistry.MINION.createItem(minionType, tierLevel);
        final PlayerInventory inventory = target.getInventory();

        if(!inventory.canAddItem(item)) {
            sender.sendMessage("§cThe inventory of the target is full!");
            return false;
        }

        inventory.addItem(item);
        sender.sendMessage("§aGave minion type §r§f" + minionType.getDisplayName() + "§f " + Utils.toRoman(tierLevel) + "§r§a to SkyBlock player!");
        return false;
    }
}

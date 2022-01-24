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
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.registry.Registries;

public class GiveCommand extends Command {

    public GiveCommand() {
        super("give");
        final String[] items = Registries.ITEMS.values().stream().filter(item -> !item.needsParams()).map(IGameItem::name).map(String::toLowerCase).toArray(String[]::new);
        this.setPermission("skyblock.cmd.give");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("item", false, items),
                CommandParameter.newType("amount", false, CommandParamType.INT)
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("item", false, items),
                CommandParameter.newType("amount", false, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender))
            return false;
        if(args.length < 1) {
            sender.sendMessage("§cUsage: /give <item> [<amount>] | /give <target> <item> [<amount>]");
            return false;
        }

        final String firstArg = args[0];
        final boolean firstArgName = sender.getServer().getPlayer(firstArg) != null && (Registries.ITEMS.valueOf(firstArg) == null || Registries.ITEMS.valueOf(firstArg).needsParams());
        final Player target = sender instanceof Player && args.length < 3 && !firstArgName ? (Player) sender : sender.getServer().getPlayer(firstArg);
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final String itemArg = args.length < 3 && (!firstArgName || args.length == 1) ? firstArg : args[1];
        final IGameItem gameItem = Registries.ITEMS.valueOf(itemArg);
        if(gameItem == null || gameItem.needsParams()) {
            sender.sendMessage("§cItem " + itemArg + " couldn't be found!");
            return false;
        }

        int amount = 1;
        try {
            if(args.length > 1 && !firstArgName || args.length > 2)
                amount = Integer.parseInt(args[args.length < 3 ? 1 : 2]);
            if(amount < 1)
                amount = 1;
        } catch(NumberFormatException e) {
            sender.sendMessage("§cAmount isn't a number!");
            return false;
        }

        final int finalAmount = amount;
        final int stackSize = Math.max(gameItem.getStackSize(), 1);
        for(int i = amount; i > 0 && amount > 0; i -= stackSize) {
            final Item item = gameItem.createItem();
            item.setCount(Math.min(amount, stackSize));
            final PlayerInventory inventory = target.getInventory();
            if(!inventory.canAddItem(item)) {
                if(finalAmount - amount == 0) {
                    sender.sendMessage("§cThe inventory of the target is full!");
                    return false;
                }
                break;
            }

            inventory.addItem(item);
            amount -= stackSize;
        }

        sender.sendMessage("§aGave item §r§f" + gameItem.getRarity().getColorCode() + gameItem.getDisplayName() + "§r §8x" + (finalAmount - Math.max(amount, 0)) + "§r§a to SkyBlock player!");
        return false;
    }

}

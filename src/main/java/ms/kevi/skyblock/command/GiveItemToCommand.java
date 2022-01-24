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
import ms.kevi.skyblock.item.GiftedItemData;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.registry.ItemGenerator;
import ms.kevi.skyblock.registry.Registries;

public class GiveItemToCommand extends Command {

    public GiveItemToCommand() {
        super("giveitemto");
        final String[] items = Registries.ITEMS.values().stream().map(IGameItem::name).map(String::toLowerCase).toArray(String[]::new);
        this.setPermission("skyblock.cmd.giveitemto");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("item", false, items),
                CommandParameter.newType("amount", false, CommandParamType.INT),
                CommandParameter.newType("edition", false, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender) || !(sender instanceof Player))
            return false;

        final Player player = (Player) sender;
        if(args.length < 4) {
            sender.sendMessage("§cUsage: /giveitemto <target> <item> <amount> <edition>");
            return false;
        }

        final Player target = sender.getServer().getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final IGameItem gameItem = Registries.ITEMS.valueOf(args[1]);
        if(gameItem == null) {
            sender.sendMessage("§cItem " + args[1] + " couldn't be found!");
            return false;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
            if(amount < 1)
                amount = 1;
            final int stackSize = gameItem.getStackSize();
            if(stackSize > 0 && amount > stackSize)
                amount = stackSize;
        } catch(NumberFormatException e) {
            sender.sendMessage("§cAmount isn't a number!");
            return false;
        }

        int edition;
        try {
            edition = Integer.parseInt(args[3]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cEdition isn't a number!");
            return false;
        }

        final Item item = ItemGenerator.create(ItemHelper.setGiftedData(gameItem.createItem(), new GiftedItemData(
                System.currentTimeMillis(),
                edition,
                player.getNameTag(),
                target.getNameTag()
        )));
        item.setCount(amount);
        final PlayerInventory inventory = target.getInventory();

        if(!inventory.canAddItem(item)) {
            sender.sendMessage("§cThe inventory of the target is full!");
            return false;
        }

        inventory.addItem(item);
        sender.sendMessage("§aGave item §r§f" + gameItem.getRarity().getColorCode() + gameItem.getDisplayName() + "§r §8x" + amount + "§r§a to SkyBlock player!");
        return false;
    }
}

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
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.enchantment.Enchantment;
import ms.kevi.skyblock.item.enchantment.EnchantmentRegistry;
import ms.kevi.skyblock.item.registry.ItemGenerator;
import ms.kevi.skyblock.util.Utils;

import java.util.Arrays;

public class EnchantCommand extends Command {

    public EnchantCommand() {
        super("enchant");
        this.setPermission("skyblock.cmd.enchant");
        final String[] enchantments = Arrays.stream(EnchantmentRegistry.values()).map(Enum::name).map(String::toLowerCase).toArray(String[]::new);
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("enchantment", false, enchantments),
                new CommandParameter("level", CommandParamType.INT, false)
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                new CommandParameter("target", CommandParamType.TARGET, false),
                new CommandParameter("enchantment", false, enchantments),
                new CommandParameter("level", CommandParamType.INT, false)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender))
            return false;
        if(args.length < 2) {
            sender.sendMessage("§cUsage: /enchant <enchantment> <level> | /enchant <target> <enchantment> <level>");
            return false;
        }

        final Player target = sender instanceof Player && args.length == 2 ? (Player) sender : args.length > 2 ? sender.getServer().getPlayer(args[0]) : null;
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final Enchantment enchantment = Arrays.stream(EnchantmentRegistry.values()).filter(value -> value.name().equalsIgnoreCase(args[args.length == 2 ? 0 : 1])).findAny().map(value -> value.newInstance(0)).orElse(null);
        if(enchantment == null) {
            sender.sendMessage("§cEnchantment " + args[args.length == 2 ? 0 : 1].toLowerCase() + " couldn't be found!");
            return false;
        }

        final int level;
        try {
            level = Integer.parseInt(args[args.length == 2 ? 1 : 2]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cLevel isn't a number!");
            return false;
        }

        enchantment.setLevel(level);

        final PlayerInventory inventory = target.getInventory();
        final Item item = inventory.getItemInHand();
        final String attributeId = ItemHelper.getAttributeId(item);

        if(item == null || item.isNull()) {
            sender.sendMessage("§cThe target player has no item in his hand!");
            return false;
        }

        if(attributeId.equalsIgnoreCase("UNKNOWN")) {
            sender.sendMessage("§cThe target player has no valid item in his hand!");
            return false;
        }

        if(enchantment.getLevel() > 0) {
            inventory.setItemInHand(ItemGenerator.create(ItemHelper.addEnchantment(item, enchantment)));
            sender.sendMessage("§aEnchantment §r§6" + enchantment.getName() + "§r §7" + Utils.toRoman(level) + "§r§a added to item!");
        } else {
            inventory.setItemInHand(ItemGenerator.create(ItemHelper.removeEnchantment(item, enchantment)));
            sender.sendMessage("§aEnchantment §r§6" + enchantment.getName() + "§r§a removed from item!");
        }
        return false;
    }
}

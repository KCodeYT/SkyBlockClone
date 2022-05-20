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
import ms.kevi.skyblock.game.effect.IEffect;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.Utils;

public class GivePotionCommand extends Command {

    public GivePotionCommand() {
        super("givepotion");
        final String[] effects = Registries.EFFECTS.nameSet().stream().map(String::toLowerCase).toArray(String[]::new);
        this.setPermission("skyblock.cmd.givepotion");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("effect", false, effects),
                CommandParameter.newType("level", false, CommandParamType.INT),
                CommandParameter.newEnum("extended", false, new String[]{"true", "false"}),
                CommandParameter.newEnum("enhanced", false, new String[]{"true", "false"}),
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("effect", false, effects),
                CommandParameter.newType("level", false, CommandParamType.INT),
                CommandParameter.newEnum("extended", false, new String[]{"true", "false"}),
                CommandParameter.newEnum("enhanced", false, new String[]{"true", "false"}),
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender))
            return false;

        if(args.length < 4) {
            sender.sendMessage("§cUsage: /givepotion <effect> <level> <extended> <enhanced> | /givepotion <target> <effect> <level> <extended> <enhanced>");
            return false;
        }

        final Player target = sender instanceof Player && args.length == 4 ? (Player) sender : args.length > 4 ? sender.getServer().getPlayer(args[0]) : null;
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final String effectArg = args[args.length == 4 ? 0 : 1];
        final IEffect effect = Registries.EFFECTS.valueOf(effectArg);
        if(effect == null) {
            sender.sendMessage("§cEffect " + effectArg + " couldn't be found!");
            return false;
        }

        int level;
        try {
            level = Integer.parseInt(args[args.length == 4 ? 1 : 2]);
            if(level < 1) level = 1;

            final int maxLevel = effect.getMaxLevel();
            if(level > maxLevel) level = maxLevel;
        } catch(NumberFormatException e) {
            sender.sendMessage("§cLevel isn't a number!");
            return false;
        }

        boolean extended;
        try {
            extended = Boolean.parseBoolean(args[args.length == 4 ? 2 : 3]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cExtended isn't a boolean!");
            return false;
        }

        boolean enhanced;
        try {
            enhanced = Boolean.parseBoolean(args[args.length == 4 ? 2 : 3]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cEnhanced isn't a boolean!");
            return false;
        }

        final Item item = Registries.ITEMS.valueOfNonNull(effect.name()).createItem(level, extended, enhanced);
        final PlayerInventory inventory = target.getInventory();

        if(!inventory.canAddItem(item)) {
            sender.sendMessage("§cThe inventory of the target is full!");
            return false;
        }

        inventory.addItem(item);
        sender.sendMessage("§aGave potion §r§f" + effect.getDisplayName() + "§f " + Utils.toRoman(level) + "§r§a to SkyBlock player!");
        return false;
    }
}

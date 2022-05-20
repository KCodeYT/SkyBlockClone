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
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.game.stats.GameStats;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SetStatsCommand extends Command {

    public SetStatsCommand() {
        super("setstats");
        this.setPermission("skyblock.cmd.setstats");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("stats", false, Arrays.stream(GameStats.values()).map(Enum::name).collect(Collectors.joining(" ")).toLowerCase().split(" ")),
                CommandParameter.newType("value", false, CommandParamType.INT)
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("stats", false, Arrays.stream(GameStats.values()).map(Enum::name).collect(Collectors.joining(" ")).toLowerCase().split(" ")),
                CommandParameter.newType("value", false, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender))
            return false;
        if(args.length < 2) {
            sender.sendMessage("§cUsage: /setstats <stats> <value> | /setstats <target> <stats> <value>");
            return false;
        }

        final Player target = sender instanceof Player && args.length == 2 ? (Player) sender : args.length > 2 ? sender.getServer().getPlayer(args[0]) : null;
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final GameStats gameStats = Arrays.stream(GameStats.values()).filter(value -> value.name().equalsIgnoreCase(args[args.length == 2 ? 0 : 1])).findAny().orElse(null);
        if(gameStats == null) {
            sender.sendMessage("§cStats " + args[args.length == 2 ? 0 : 1].toLowerCase() + " couldn't be found!");
            return false;
        }

        final int value;
        try {
            value = Integer.parseInt(args[args.length == 2 ? 1 : 2]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cValue isn't a number!");
            return false;
        }

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(target);
        if(skyBlockPlayer == null) {
            sender.sendMessage("§cSkyBlock player couldn't be found!");
            return false;
        }

        skyBlockPlayer.getGameAttributes().get(gameStats).setValue(value, true);
        sender.sendMessage("§aSet stats " + gameStats.toString(true, false) + "§r§a of SkyBlock player to §6" + value + "§a!");
        return true;
    }

}

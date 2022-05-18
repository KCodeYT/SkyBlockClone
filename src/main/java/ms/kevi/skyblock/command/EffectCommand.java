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
import ms.kevi.skyblock.game.effect.Effect;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.registry.Registrable;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.Utils;

public class EffectCommand extends Command {

    public EffectCommand() {
        super("effect");
        this.setPermission("skyblock.cmd.effect");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("effect", false, Registries.EFFECTS.values().stream().map(Registrable::name).toArray(String[]::new)),
                CommandParameter.newType("duration", false, CommandParamType.INT)
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                CommandParameter.newEnum("effect", false, Registries.EFFECTS.values().stream().map(Registrable::name).toArray(String[]::new)),
                CommandParameter.newType("duration", false, CommandParamType.INT),
                CommandParameter.newType("amplifier", false, CommandParamType.INT)
        });
        this.commandParameters.put("default2", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("effect", false, Registries.EFFECTS.values().stream().map(Registrable::name).toArray(String[]::new)),
                CommandParameter.newType("duration", false, CommandParamType.INT),
                CommandParameter.newType("amplifier", false, CommandParamType.INT)
        });
        this.commandParameters.put("default3", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("effect", false, Registries.EFFECTS.values().stream().map(Registrable::name).toArray(String[]::new)),
                CommandParameter.newType("duration", false, CommandParamType.INT),
                CommandParameter.newType("amplifier", false, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender)) return false;

        if(args.length < 2) {
            sender.sendMessage("§cUsage: /effect <effect> <duration> | /effect <effect> <duration> <amplifier>");
            sender.sendMessage("§cUsage: /effect <target> <effect> <duration> | /effect <target> <effect> <duration> <amplifier>");
            return false;
        }

        if(args.length == 2 && !(sender instanceof Player)) {
            sender.sendMessage("This command can only be used in-game.");
            return false;
        }

        final Player target = sender instanceof Player && (args.length == 2 || !Utils.isInteger(args[1])) ? (Player) sender : sender.getServer().getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final Effect effect = Registries.EFFECTS.valueOf(args[Utils.isInteger(args[1]) ? 0 : 1]);
        if(effect == null) {
            sender.sendMessage("§cEffect " + args[args.length == 2 ? 0 : 1].toLowerCase() + " couldn't be found!");
            return false;
        }

        final int duration;
        try {
            duration = Integer.parseInt(args[Utils.isInteger(args[1]) ? 1 : 2]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cDuration isn't a number!");
            return false;
        }

        final int amplifier;
        try {
            amplifier = Integer.parseInt(args[Utils.isInteger(args[1]) ? 2 : 3]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cAmplifier isn't a number!");
            return false;
        }

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(target);
        if(skyBlockPlayer == null) {
            sender.sendMessage("§cSkyBlock player couldn't be found!");
            return false;
        }

        skyBlockPlayer.getEffects().add(effect, duration, amplifier);
        sender.sendMessage("§aAdded effect " + effect.getDisplayName() + "§r§a to " + target.getName() + "§r§a for §6" + duration + "§r§a seconds with amplifier §6" + amplifier + "§r§a.");
        return true;
    }

}

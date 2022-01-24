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
import ms.kevi.skyblock.game.pet.PetData;
import ms.kevi.skyblock.game.pet.PetLevelData;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;

public class GivePetXpCommand extends Command {

    public GivePetXpCommand() {
        super("givepetxp");
        this.setPermission("skyblock.cmd.givepetxp");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("experience", false, CommandParamType.INT)
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newType("experience", false, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender))
            return false;
        if(args.length < 1) {
            sender.sendMessage("§cUsage: /givepetxp <experience> | /givepetxp <target> <experience>");
            return false;
        }

        final Player target = sender instanceof Player && args.length == 1 ? (Player) sender : args.length > 1 ? sender.getServer().getPlayer(args[0]) : null;
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(target);
        if(skyBlockPlayer == null) {
            sender.sendMessage("§cSkyBlock player couldn't be found!");
            return false;
        }

        final PetData activePet = skyBlockPlayer.getPets().getActive().getPetData();
        if(activePet == null) {
            sender.sendMessage("§cThe player currently has no pet activated!");
            return false;
        }

        double experience = 0;
        int level = -1;
        try {
            final String xpArg = args[args.length == 1 ? 0 : 1];
            if(xpArg.toLowerCase().endsWith("l"))
                level = Math.max(Math.min(Integer.parseInt(xpArg.substring(0, xpArg.length() - 1)), activePet.getType().getMaxLevel()), 1);
            else
                experience = Math.max(Double.parseDouble(xpArg), 0);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cExperience isn't a number!");
            return false;
        }

        experience = Math.max(level == -1 ? experience : PetLevelData.getExperience(activePet.getRarity(), level), 0);
        final int petLevel = activePet.getLevel();
        activePet.addExperience(experience);
        if(petLevel != activePet.getLevel()) {
            target.sendMessage("§aYour §f" + activePet.getRarity().getColorCode() + activePet.getType().getDisplayName() + "§r§a levelled up to level §9" + activePet.getLevel() + "§a!");
        }

        sender.sendMessage("§aGave §6" + experience + "§a pet experience to SkyBlock player!");
        return true;
    }

}

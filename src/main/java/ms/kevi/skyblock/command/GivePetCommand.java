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
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.pet.IPetType;
import ms.kevi.skyblock.game.pet.PetLevelData;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;
import ms.kevi.skyblock.registry.Registries;

import java.util.Arrays;

public class GivePetCommand extends Command {

    public GivePetCommand() {
        super("givepet");
        this.setPermission("skyblock.cmd.givepet");
        final String[] petTypes = Registries.PETS.nameSet().stream().map(String::toLowerCase).toArray(String[]::new);
        final String[] gameRarities = Arrays.stream(GameRarity.values()).map(Enum::name).map(String::toLowerCase).toArray(String[]::new);
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("pet", false, petTypes),
                CommandParameter.newEnum("rarity", false, gameRarities),
                CommandParameter.newType("experience", false, CommandParamType.INT)
        });
        this.commandParameters.put("default1", new CommandParameter[]{
                CommandParameter.newType("target", false, CommandParamType.TARGET),
                CommandParameter.newEnum("pet", false, petTypes),
                CommandParameter.newEnum("rarity", false, gameRarities),
                CommandParameter.newType("experience", false, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.testPermission(sender))
            return false;
        if(args.length < 3) {
            sender.sendMessage("§cUsage: /givepet <pet> <rarity> <experience> | /givepet <target> <pet> <rarity> <experience>");
            return false;
        }

        final Player target = sender instanceof Player && args.length == 3 ? (Player) sender : args.length > 3 ? sender.getServer().getPlayer(args[0]) : null;
        if(target == null) {
            sender.sendMessage("§cNo target found!");
            return false;
        }

        final IPetType petType = Registries.PETS.valueOf(args[args.length == 3 ? 0 : 1]);
        if(petType == null) {
            sender.sendMessage("§cPet " + args[args.length == 3 ? 0 : 1].toLowerCase() + " couldn't be found!");
            return false;
        }

        final GameRarity gameRarity = Arrays.stream(GameRarity.values()).filter(value -> value.name().equalsIgnoreCase(args[args.length == 3 ? 1 : 2])).findAny().orElse(null);
        if(gameRarity == null) {
            sender.sendMessage("§cRarity " + args[args.length == 3 ? 1 : 2].toLowerCase() + " couldn't be found!");
            return false;
        }

        double experience = 0;
        int level = -1;
        try {
            final String xpArg = args[args.length == 3 ? 2 : 3];
            if(xpArg.toLowerCase().endsWith("l"))
                level = Math.max(Math.min(Integer.parseInt(xpArg.substring(0, xpArg.length() - 1)), petType.getMaxLevel()), 1);
            else
                experience = Math.max(Double.parseDouble(xpArg), 0);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cExperience isn't a number!");
            return false;
        }

        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(target);
        if(skyBlockPlayer == null) {
            sender.sendMessage("§cSkyBlock player couldn't be found!");
            return false;
        }

        experience = Math.max(Math.min(
                level == -1 ? experience : PetLevelData.getExperience(gameRarity, level),
                PetLevelData.getExperience(gameRarity, petType.getMaxLevel())
        ), 0);
        skyBlockPlayer.getPets().addPet(petType, gameRarity, experience);
        sender.sendMessage("§aGave pet §r§f" + gameRarity.getColorCode() + petType.getDisplayName() + "§r§a to SkyBlock player!");
        target.sendMessage("§r§6" + sender.getName() + "§r§a gave you a §r§f" + gameRarity.getColorCode() + petType.getDisplayName() + "§r§a! Open your Pet Menu to view all of your pets!");
        return true;
    }

}

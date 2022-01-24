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
import cn.nukkit.inventory.PlayerInventory;
import ms.kevi.skyblock.registry.GameItemRegistry;

public class SbClearCommand extends Command {

    public SbClearCommand() {
        super("sbclear");
        this.setPermission("skyblock.cmd.sbclear");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(!(commandSender instanceof Player) || !this.testPermission(commandSender))
            return false;
        final Player player = (Player) commandSender;
        final PlayerInventory inventory = player.getInventory();
        inventory.clearAll();
        inventory.setItem(8, GameItemRegistry.MENU.createItem());
        commandSender.sendMessage("§aZoop!");
        return false;
    }
}

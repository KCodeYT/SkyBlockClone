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
import cn.nukkit.command.data.CommandParameter;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.menu.crafting.ViewRecipeMenu;
import ms.kevi.skyblock.registry.Registries;

import java.util.Collections;

public class ViewRecipeCommand extends Command {

    public ViewRecipeCommand() {
        super("viewrecipe");
        final String[] recipes = Registries.RECIPES.nameSet().stream().map(String::toLowerCase).toArray(String[]::new);
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("recipe", false, recipes)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player) || !this.testPermission(sender))
            return false;
        if(args.length < 1) {
            sender.sendMessage("§cUsage: /viewrecipe <recipe>");
            return false;
        }

        final Recipe recipe = Registries.RECIPES.valueOf(args[0]);
        if(recipe == null) {
            sender.sendMessage("§cInvalid recipe '" + args[0] + "'.");
            return false;
        }

        new ViewRecipeMenu((Player) sender, Collections.singletonList(recipe));
        return false;
    }
}

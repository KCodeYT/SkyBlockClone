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

package ms.kevi.skyblock.util;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.SimpleCommandMap;

import java.util.Map;

public class CommandUtil {

    private static final SimpleCommandMap COMMAND_MAP = Server.getInstance().getCommandMap();

    public static void register(Command command) {
        final Map<String, Command> commands = COMMAND_MAP.getCommands();
        commands.remove("nukkit:" + command.getName());
        commands.remove(command.getName());
        COMMAND_MAP.register("skyblock", command);
    }

}

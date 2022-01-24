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

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ms.kevi.skyblock.util.Timings;

public class NewTimingsCommand extends Command {

    public NewTimingsCommand() {
        super("newtimings");
        this.setPermission("skyblock.cmd.newtimings");
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] strings) {
        if(!this.testPermission(commandSender))
            return false;
        commandSender.sendMessage("------ TIMINGS 2.0 ------");
        commandSender.sendMessage("Server TPS: " + Server.getInstance().getTicksPerSecond());
        commandSender.sendMessage("Server Average TPS: " + Server.getInstance().getTicksPerSecondAverage());
        commandSender.sendMessage("Server Usage: " + Server.getInstance().getTickUsage());
        commandSender.sendMessage("Server Average Usage: " + Server.getInstance().getTickUsageAverage());
        commandSender.sendMessage("Entity Count: " + Timings.getLastEntityCount());
        commandSender.sendMessage("Entity Count Average: " + Timings.getAverageEntityCount());
        for(String timingSpec : Timings.getTimingSpecs())
            commandSender.sendMessage(timingSpec + " (" + Timings.getTiming(timingSpec) + "ms, Average: " + Timings.getTimingAverage(timingSpec) + "ms)");
        commandSender.sendMessage("------ TIMINGS 2.0 ------");
        //commandSender.getServer().getLogger().info(new Gson().toJson(SkyBlockPlugin.getInstance().buildServerInfo()));
        return false;
    }

}

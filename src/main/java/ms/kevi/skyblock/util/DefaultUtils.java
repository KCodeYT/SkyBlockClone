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

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import ms.kevi.skyblock.SkyBlockPlugin;
import ms.kevi.skyblock.level.WorldConverter;
import ms.kevi.skyblock.level.schematic.Schematic;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DefaultUtils {

    private static final Map<Player, DoubleValue<Vector3, Vector3>> DEBUG_MAP = new HashMap<>();
    private static final Map<Player, Schematic> DEBUG_MAP_2 = new HashMap<>();

    public static void init(SkyBlockPlugin skyBlockPlugin) {
        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("pos1") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                DEBUG_MAP.putIfAbsent((Player) commandSender, new DoubleValue<>(null, null));
                DEBUG_MAP.get(commandSender).setValue1(((Player) commandSender).asBlockVector3().asVector3());
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("pos2") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                DEBUG_MAP.putIfAbsent((Player) commandSender, new DoubleValue<>(null, null));
                DEBUG_MAP.get(commandSender).setValue2(((Player) commandSender).asBlockVector3().asVector3());
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("schem") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                DEBUG_MAP.putIfAbsent((Player) commandSender, new DoubleValue<>(null, null));

                createSchematic((Player) commandSender, DEBUG_MAP.get(commandSender).getValue1(), DEBUG_MAP.get(commandSender).getValue2(), ((Player) commandSender).asBlockVector3().asVector3());
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("place") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                DEBUG_MAP_2.get(commandSender).buildInstant(((Player) commandSender).asBlockVector3().asVector3(), ((Player) commandSender).getLevel(), Boolean.parseBoolean(args[0]));
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("aniplace") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                DEBUG_MAP_2.get(commandSender).buildAnimated(((Player) commandSender).asBlockVector3().asVector3(), ((Player) commandSender).getLevel(), Boolean.parseBoolean(args[0]));
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("save") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                DEBUG_MAP_2.get(commandSender).save(new File("./" + String.join(" ", args) + ".schem"));
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("load") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                DEBUG_MAP_2.put((Player) commandSender, new Schematic(new File("./" + String.join(" ", args) + ".schem")));
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("worldtp", "", "", new String[]{"wtp", "tpw"}) {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                ((Player) commandSender).teleport(commandSender.getServer().getLevelByName(args[0]).getSpawnLocation());
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("loadworld", "", "", new String[]{"lw", "loadw"}) {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                commandSender.getServer().loadLevel(args[0]);
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("unloadworld", "", "", new String[]{"ulw", "unloadw"}) {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                commandSender.getServer().unloadLevel(commandSender.getServer().getLevelByName(args[0]));
                return true;
            }
        });

        skyBlockPlugin.getServer().getCommandMap().register("skyblock", new Command("convertworld", "", "", new String[]{"cw", "convertw"}) {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                WorldConverter.convert(commandSender.getServer().getLevelByName(args[0]), true);
                return true;
            }
        });
    }

    private static void createSchematic(Player player, Vector3 vector3_1, Vector3 vector3_2, Vector3 vector3_3) {
        int minX = (int) Math.floor(Math.min(vector3_1.x, vector3_2.x));
        int maxX = (int) Math.floor(Math.max(vector3_1.x, vector3_2.x));
        int minY = (int) Math.floor(Math.min(vector3_1.y, vector3_2.y));
        int maxY = (int) Math.floor(Math.max(vector3_1.y, vector3_2.y));
        int minZ = (int) Math.floor(Math.min(vector3_1.z, vector3_2.z));
        int maxZ = (int) Math.floor(Math.max(vector3_1.z, vector3_2.z));

        Schematic schematic = new Schematic();
        Level level = player.getLevel();
        for(int x = minX; x <= maxX; x++) {
            for(int y = minY; y <= maxY; y++) {
                for(int z = minZ; z <= maxZ; z++) {
                    Vector3 vector3 = new Vector3(x, y, z);
                    int xMinus = x - vector3_3.getFloorX();
                    int yMinus = y - vector3_3.getFloorY();
                    int zMinus = z - vector3_3.getFloorZ();
                    schematic.addBlock(new Vector3(xMinus, yMinus, zMinus), level.getBlock(vector3));
                    Arrays.stream(level.getEntities()).
                            filter(entity -> !(entity instanceof Player) && entity.getFloorX() == vector3.getFloorX() && entity.getFloorY() == vector3.getFloorY() && entity.getFloorZ() == vector3.getFloorZ()).
                            forEach(entity -> {
                                entity.saveNBT();
                                schematic.addEntity(entity.subtract(vector3_3.getFloorX(), vector3_3.getFloorY(), vector3_3.getFloorZ()), entity.getSaveId(), entity.namedTag.copy());
                            });
                    BlockEntity blockEntity = level.getBlockEntity(new Vector3(x, y, z));
                    if(blockEntity != null)
                        schematic.addBlockEntity(new Vector3(xMinus, yMinus, zMinus).asBlockVector3(), blockEntity.getSaveId(), blockEntity.getCleanedNBT().copy());
                }
            }
        }

        DEBUG_MAP_2.put(player, schematic);
    }

}

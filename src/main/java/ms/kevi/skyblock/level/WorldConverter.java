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

package ms.kevi.skyblock.level;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.format.anvil.RegionLoader;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.MainLogger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorldConverter {

    private static final Map<Integer, String> BLOCKS = new HashMap<>();

    static {
        BLOCKS.put(Block.CHEST, BlockEntity.CHEST);
        BLOCKS.put(Block.FURNACE, BlockEntity.FURNACE);
        BLOCKS.put(Block.BREWING_BLOCK, BlockEntity.BREWING_STAND);
        BLOCKS.put(Block.FLOWER_POT_BLOCK, BlockEntity.FLOWER_POT);
        BLOCKS.put(Block.BED_BLOCK, BlockEntity.BED);
        BLOCKS.put(Block.ENDER_CHEST, BlockEntity.ENDER_CHEST);
        BLOCKS.put(Block.SIGN_POST, BlockEntity.SIGN);
        BLOCKS.put(Block.WALL_SIGN, BlockEntity.SIGN);
        BLOCKS.put(Block.ENCHANT_TABLE, BlockEntity.ENCHANT_TABLE);
        BLOCKS.put(Block.ITEM_FRAME_BLOCK, BlockEntity.ITEM_FRAME);
        BLOCKS.put(Block.BEACON, BlockEntity.BEACON);
        BLOCKS.put(Block.HOPPER_BLOCK, BlockEntity.HOPPER);
    }

    public static void convert(Level level, boolean fast) {
        LevelProvider provider = level.getProvider();
        Server server = level.getServer();

        Pattern pattern = Pattern.compile("-?\\d+");
        File[] regions = new File(server.getDataPath() + "worlds/" + level.getFolderName() + "/region").listFiles(
                (f) -> f.isFile() && f.getName().endsWith(".mca")
        );

        String levelName = level.getName();
        if(regions != null && regions.length > 0) {
            double processed = 0;
            int blocks = 0;
            int blockEntities = 0;
            long time = System.currentTimeMillis();
            server.getLogger().info("Starting fixing world '" + levelName + "'");

            for(File region : regions) {
                Matcher m = pattern.matcher(region.getName());
                int regionX, regionZ;
                try {
                    if(m.find()) {
                        regionX = Integer.parseInt(m.group());
                    } else continue;
                    if(m.find()) {
                        regionZ = Integer.parseInt(m.group());
                    } else continue;
                } catch(NumberFormatException e) {
                    continue;
                }

                long start = System.currentTimeMillis();

                try {
                    RegionLoader loader = new RegionLoader(provider, regionX, regionZ);
                    Integer[] table = loader.getLocationIndexes();

                    for(Integer index : table) {
                        int chunkX = index & 0x1f;
                        int chunkZ = index >> 5;
                        BaseFullChunk chunk = loader.readChunk(chunkX, chunkZ);

                        if(chunk == null) continue;
                        chunk.initChunk();

                        boolean chunkChanged = false;

                        for(int x = 0; x < 16; x++) {
                            for(int y = 0; y < 256; y++) {
                                for(int z = 0; z < 16; z++) {
                                    int id = chunk.getBlockId(x, y, z);
                                    boolean changed = true;

                                    switch(id) {
                                        case 3:
                                            if(chunk.getBlockData(x, y, z) == 2) {
                                                chunk.setBlockId(x, y, z, Item.PODZOL);
                                            }
                                            break;
                                        case 43:
                                        case 44:
                                            if(chunk.getBlockData(x, y, z) == 6)
                                                chunk.setBlockData(x, y, z, 7);
                                            else if(chunk.getBlockData(x, y, z) == 7)
                                                chunk.setBlockData(x, y, z, 6);
                                            if(chunk.getBlockData(x, y, z) == 15)
                                                chunk.setBlockData(x, y, z, 14);
                                            else if(chunk.getBlockData(x, y, z) == 14)
                                                chunk.setBlockData(x, y, z, 15);
                                            break;
                                        case 125:
                                            chunk.setBlockId(x, y, z, Item.DOUBLE_WOODEN_SLAB);
                                            break;
                                        case 126:
                                            chunk.setBlockId(x, y, z, Item.WOOD_SLAB);
                                            break;
                                        case 95:
                                            chunk.setBlockId(x, y, z, 241);
                                            break;
                                        case 166:
                                            chunk.setBlockId(x, y, z, Item.INVISIBLE_BEDROCK);
                                            break;
                                        case 177:
                                            chunk.setBlockId(x, y, z, Item.AIR);
                                            break;
                                        case 188:
                                            chunk.setBlockId(x, y, z, Item.FENCE);
                                            chunk.setBlockData(x, y, z, 1);
                                            break;
                                        case 189:
                                            chunk.setBlockId(x, y, z, Item.FENCE);
                                            chunk.setBlockData(x, y, z, 2);
                                            break;
                                        case 190:
                                            chunk.setBlockId(x, y, z, Item.FENCE);
                                            chunk.setBlockData(x, y, z, 3);
                                            break;
                                        case 191:
                                            chunk.setBlockId(x, y, z, Item.FENCE);
                                            chunk.setBlockData(x, y, z, 4);
                                            break;
                                        case 192:
                                            chunk.setBlockId(x, y, z, Item.FENCE);
                                            chunk.setBlockData(x, y, z, 5);
                                            break;
                                        case 198:
                                            chunk.setBlockId(x, y, z, Item.END_ROD);
                                            break;
                                        case 199:
                                            chunk.setBlockId(x, y, z, Item.CHORUS_PLANT);
                                            break;
                                        case 202:
                                        case 204:
                                            chunk.setBlockId(x, y, z, Item.PURPUR_BLOCK);
                                            break;
                                        case 203:
                                            chunk.setBlockId(x, y, z, 202);
                                            break;
                                        case 208:
                                            chunk.setBlockId(x, y, z, Item.GRASS_PATH);
                                            break;
                                        case 210:
                                            chunk.setBlockId(x, y, z, 188);
                                            break;
                                        case 211:
                                            chunk.setBlockId(x, y, z, 189);
                                            break;
                                        case 158:
                                            chunk.setBlockId(x, y, z, 125);
                                            break;
                                        case 157:
                                            chunk.setBlockId(x, y, z, 126);
                                            break;
                                        case 235:
                                            chunk.setBlockId(x, y, z, 220);
                                            break;
                                        case 236:
                                            chunk.setBlockId(x, y, z, 221);
                                            break;
                                        case 237:
                                            chunk.setBlockId(x, y, z, 222);
                                            break;
                                        case 238:
                                            chunk.setBlockId(x, y, z, 223);
                                            break;
                                        case 239:
                                            chunk.setBlockId(x, y, z, 224);
                                            break;
                                        case 240:
                                            chunk.setBlockId(x, y, z, 225);
                                            break;
                                        case 241:
                                            chunk.setBlockId(x, y, z, 226);
                                            break;
                                        case 242:
                                            chunk.setBlockId(x, y, z, 227);
                                            break;
                                        case 243:
                                            chunk.setBlockId(x, y, z, 228);
                                            break;
                                        case 244:
                                            chunk.setBlockId(x, y, z, 229);
                                            break;
                                        case 245:
                                            chunk.setBlockId(x, y, z, 230);
                                            break;
                                        case 246:
                                            chunk.setBlockId(x, y, z, 231);
                                            break;
                                        case 247:
                                            chunk.setBlockId(x, y, z, 232);
                                            break;
                                        case 248:
                                            chunk.setBlockId(x, y, z, 233);
                                            break;
                                        case 249:
                                            chunk.setBlockId(x, y, z, 234);
                                            break;
                                        case 250:
                                            chunk.setBlockId(x, y, z, 235);
                                            break;
                                        case 251:
                                            chunk.setBlockId(x, y, z, 236);
                                            break;
                                        case 252:
                                            chunk.setBlockId(x, y, z, 237);
                                            break;
                                        case 218:
                                            chunk.setBlockId(x, y, z, 251);
                                            break;
                                        case 207:
                                            chunk.setBlockId(x, y, z, 244);
                                            break;
                                        case Item.STONE_BUTTON:
                                        case Item.WOODEN_BUTTON:
                                            int data = chunk.getBlockData(x, y, z);
                                            int face = data & 0b111;

                                            int meta = 0;
                                            switch(face) {
                                                case 0: //down
                                                    meta = BlockFace.DOWN.getIndex();
                                                    break;
                                                case 1: //east
                                                    meta = BlockFace.EAST.getIndex();
                                                    break;
                                                case 2: //west
                                                    meta = BlockFace.WEST.getIndex();
                                                    break;
                                                case 3: //south
                                                    meta = BlockFace.SOUTH.getIndex();
                                                    break;
                                                case 4: //north
                                                    meta = BlockFace.NORTH.getIndex();
                                                    break;
                                                case 5: //up
                                                    meta = BlockFace.UP.getIndex();
                                                    break;
                                            }

                                            if((data & 0x08) == 0x08) {
                                                meta |= 0x08;
                                            }

                                            chunk.setBlockData(x, y, z, meta);
                                            break;
                                        default:
                                            changed = false;
                                            break;
                                    }

                                    if(changed) {
                                        chunkChanged = true;
                                        blocks++;
                                    }

                                    String blockEntityId = BLOCKS.get(id);

                                    Vector3 vector3 = new Vector3(x, y, z);
                                    if(blockEntityId != null && chunk.getTile(vector3.getFloorX(), vector3.getFloorY(), vector3.getFloorZ()) == null) {
                                        Vector3 real = vector3.add(chunk.getX() * 16, 0, chunk.getZ() * 16);

                                        BlockEntity.createBlockEntity(blockEntityId, chunk, BlockEntity.getDefaultCompound(real, blockEntityId));

                                        chunkChanged = true;
                                        blockEntities++;
                                    }
                                }
                            }
                        }

                        if(chunkChanged)
                            loader.writeChunk(chunk);
                    }

                    processed++;
                    loader.close();
                } catch(Exception e) {
                    MainLogger.getLogger().logException(e);
                }

                server.getLogger().info("Converting... " + NukkitMath.round((processed / regions.length) * 100, 2) + "% done");

                if(!fast) {
                    long sleep = NukkitMath.floorDouble((System.currentTimeMillis() - start) * 0.25);

                    try {
                        Thread.sleep(sleep);
                    } catch(InterruptedException e) {
                        server.getLogger().info("Main thread was interrupted and world converting process could not be completed.");
                        return;
                    }
                }
            }

            server.unloadLevel(level);
            server.loadLevel(levelName);
            server.getLogger().info("World " + levelName + " successfully converted in " + (System.currentTimeMillis() - time) / 1000 + "s. (Converted " + blocks + " blocks and " + blockEntities
                    + " blockentities)");
        }
    }
}

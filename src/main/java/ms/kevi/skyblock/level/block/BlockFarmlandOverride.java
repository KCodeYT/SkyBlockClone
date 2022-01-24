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

package ms.kevi.skyblock.level.block;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockCrops;
import cn.nukkit.block.BlockFarmland;
import cn.nukkit.block.BlockWater;
import cn.nukkit.event.block.BlockFromToEvent;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;

public class BlockFarmlandOverride extends BlockFarmland {

    @Override
    public int onUpdate(int type) {
        if(type == Level.BLOCK_UPDATE_RANDOM) {
            Vector3 v = new Vector3();

            if(this.level.getBlock(v.setComponents(x, this.y + 1, z)) instanceof BlockCrops) {
                return 0;
            }

            if(this.level.getBlock(v.setComponents(x, this.y + 1, z)).isSolid()) {
                BlockFromToEvent blockFromToEvent = new BlockFromToEvent(this, Block.get(Block.DIRT));
                Server.getInstance().getPluginManager().callEvent(blockFromToEvent);

                if(!blockFromToEvent.isCancelled()) {
                    this.level.setBlock(this, blockFromToEvent.getTo(), false, true);

                    return Level.BLOCK_UPDATE_RANDOM;
                }

                return 0;
            }

            boolean found = false;

            if(this.level.isRaining()) {
                found = true;
            } else {
                for(int x = (int) this.x - 4; x <= this.x + 4; x++) {
                    for(int z = (int) this.z - 4; z <= this.z + 4; z++) {
                        for(int y = (int) this.y; y <= this.y + 1; y++) {
                            if(z == this.z && x == this.x && y == this.y) {
                                continue;
                            }

                            v.setComponents(x, y, z);
                            final int block = this.level.getBlockIdAt(v.getFloorX(), v.getFloorY(), v.getFloorZ());
                            if(block == WATER || block == STILL_WATER) {
                                found = true;
                                break;
                            }
                        }
                    }
                }
            }

            Block block = this.level.getBlock(v.setComponents(x, y - 1, z));
            if(found || block instanceof BlockWater) {
                if(this.getDamage() < 7) {
                    this.setDamage(7);
                    this.level.setBlock(this, this, false, false);
                }
                return Level.BLOCK_UPDATE_RANDOM;
            }

            if(this.getDamage() > 0) {
                this.setDamage(this.getDamage() - 1);
                this.level.setBlock(this, this, false, false);
            } else {
                BlockFromToEvent blockFromToEvent = new BlockFromToEvent(this, Block.get(Block.DIRT));
                Server.getInstance().getPluginManager().callEvent(blockFromToEvent);

                if(!blockFromToEvent.isCancelled()) {
                    this.level.setBlock(this, blockFromToEvent.getTo(), false, true);
                    return Level.BLOCK_UPDATE_RANDOM;
                }

                return 0;
            }

            return Level.BLOCK_UPDATE_RANDOM;
        }

        return 0;
    }

}

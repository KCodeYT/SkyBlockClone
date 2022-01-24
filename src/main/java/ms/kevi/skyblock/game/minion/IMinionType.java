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

package ms.kevi.skyblock.game.minion;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import ms.kevi.skyblock.entity.minion.EntityMinion;
import ms.kevi.skyblock.registry.Registrable;
import ms.kevi.skyblock.registry.Registries;

import java.util.ArrayList;
import java.util.List;

public interface IMinionType extends Registrable {

    default int checkArea(List<Block> blocks) {
        int blockCount = 0;
        for(Block block : blocks)
            if(this.checkBreakability(block) || this.checkPlaceability(block))
                blockCount++;
        return blockCount;
    }

    default List<Block> createArea(EntityMinion entityMinion) {
        final Vector3 center = new Vector3(entityMinion.getFloorX(), entityMinion.getFloorY() - 1, entityMinion.getFloorZ());
        final List<Block> area = new ArrayList<>();
        for(int x = -2; x <= 2; x++) {
            for(int z = -2; z <= 2; z++) {
                if(x == 0 && z == 0)
                    continue;
                area.add(entityMinion.getLevel().getBlock(center.getFloorX() + x, center.getFloorY(), center.getFloorZ() + z));
            }
        }
        return area;
    }

    String getDisplayName();

    default int getBreakTicks() {
        return 5;
    }

    boolean checkBreakability(Block block);

    boolean checkPlaceability(Block block);

    void breakBlock(Block block);

    List<Block> getAnimationBlocks(Block block);

    void placeBlock(Block block);

    default Item getItemInHand() {
        return this.getItemInHand(null);
    }

    Item getItemInHand(Block block);

    Item getChestplateItem();

    Item getLeggingsItem();

    Item getBootsItem();

    int getMaxTierLevel();

    float getTimeBetweenActions(int tierLevel);

    int getMaxStorage(int tierLevel);

    Item getBlockFormat();

    Item getItemFormat();

    @Override
    default String name() {
        return Registries.MINIONS.nameOf(this);
    }

}
